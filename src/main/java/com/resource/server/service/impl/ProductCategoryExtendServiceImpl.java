package com.resource.server.service.impl;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.resource.server.domain.ProductCategory;
import com.resource.server.repository.ProductCategoryExtendRepository;
import com.resource.server.service.ProductCategoryExtendService;
import com.resource.server.service.dto.ProductCategoryDTO;
import com.resource.server.service.mapper.ProductCategoryMapper;
import com.stripe.model.Product;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import com.resource.server.service.util.NodeUtil;

@Service
@Transactional
public class ProductCategoryExtendServiceImpl implements ProductCategoryExtendService {

    private final Logger log = LoggerFactory.getLogger(ProductCategoryExtendServiceImpl.class);
    private final ProductCategoryExtendRepository productCategoryExtendRepository;
    private final ProductCategoryMapper productCategoryMapper;

    public ProductCategoryExtendServiceImpl(ProductCategoryExtendRepository productCategoryExtendRepository, ProductCategoryMapper productCategoryMapper) {
        this.productCategoryExtendRepository = productCategoryExtendRepository;
        this.productCategoryMapper = productCategoryMapper;
    }


    @Override
    public JsonNode getAllProductCategories() {

        try {
            List<ProductCategoryDTO> rootCategoriesDTO = productCategoryExtendRepository.findAllByParentIdIsNull()
                .stream()
                .map(productCategoryMapper::toDto)
                .collect(Collectors.toCollection(LinkedList::new));

            ObjectMapper mapper = new ObjectMapper();
            JsonNode rootNode = mapper.createArrayNode();

            for (ProductCategoryDTO parentCategoryDTO : rootCategoriesDTO) {

                List<ProductCategoryDTO> childCategoriesDTO = productCategoryExtendRepository.findAllByParentId(parentCategoryDTO.getId())
                    .stream()
                    .map(productCategoryMapper::toDto)
                    .collect(Collectors.toCollection(LinkedList::new));

                JsonNode categoryNode = mapper.convertValue(parentCategoryDTO, JsonNode.class);

                JsonNode subCategoryNodes = mapper.valueToTree(childCategoriesDTO);
                ((ObjectNode) categoryNode).put("children", subCategoryNodes);
                ((ArrayNode) rootNode).add(categoryNode);
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

