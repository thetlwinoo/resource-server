package com.resource.server.service.impl;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.resource.server.domain.ProductCategory;
import com.resource.server.repository.ProductCategoryExtendRepository;
import com.resource.server.service.ProductCategoryExtendService;
import com.stripe.model.Product;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import com.resource.server.service.util.NodeUtil;

@Service
@Transactional
public class ProductCategoryExtendServiceImpl implements ProductCategoryExtendService {

    private final Logger log = LoggerFactory.getLogger(ProductCategoryExtendServiceImpl.class);
    private final ProductCategoryExtendRepository productCategoryExtendRepository;

    public ProductCategoryExtendServiceImpl(ProductCategoryExtendRepository productCategoryExtendRepository) {
        this.productCategoryExtendRepository = productCategoryExtendRepository;
    }


    @Override
    public JsonNode getAllProductCategories() {

        try {
            List<ProductCategory> rootCategories = productCategoryExtendRepository.findAllByParentIdIsNull();
            ObjectMapper mapper = new ObjectMapper();
            JsonNode rootNode = mapper.createArrayNode();
            for (ProductCategory parentCategory : rootCategories) {
                List<ProductCategory> childCategories = productCategoryExtendRepository.findAllByParentId(parentCategory.getId());
                JsonNode categoryNode = mapper.convertValue(parentCategory, JsonNode.class);

                JsonNode subCategoryNodes = mapper.valueToTree(childCategories);
                ((ObjectNode)categoryNode).put("children",subCategoryNodes);
                ((ArrayNode)rootNode).add(categoryNode);
            }

            return rootNode;
        } catch (Exception ex) {
            throw new IllegalArgumentException(ex.getMessage());
        }

    }

//    public static <T> T mergeObjects(T first, T second) throws IllegalAccessException, InstantiationException {
//        Class<?> clazz = first.getClass();
//        Field[] fields = clazz.getDeclaredFields();
//        Object returnValue = clazz.newInstance();
//        for (Field field : fields) {
//            field.setAccessible(true);
//            Object value1 = field.get(first);
//            Object value2 = field.get(second);
//            Object value = (value1 != null) ? value1 : value2;
//            field.set(returnValue, value);
//        }
//        return (T) returnValue;
//    }
}

//class TreeNode{
//    private Long id;
//    private  String name;
//    private List<TreeNode> children;
//
//    public Long getId() {
//        return id;
//    }
//    public void setId(Long id) {
//        this.id = id;
//    }
//
//    public String getName() {
//        return name;
//    }
//
//    public List<TreeNode> getChildren() {
//        return children;
//    }
//}

