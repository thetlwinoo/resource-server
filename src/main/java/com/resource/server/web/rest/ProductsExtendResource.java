package com.resource.server.web.rest;

import com.resource.server.domain.Products;
import com.resource.server.service.ProductPhotoService;
import com.resource.server.service.ProductsExtendService;
import com.resource.server.service.ProductsQueryService;
import com.resource.server.service.ProductsService;
import com.resource.server.service.dto.ProductCategoryDTO;
import com.resource.server.service.dto.ProductsCriteria;
import com.resource.server.service.dto.ProductsDTO;
import com.resource.server.web.rest.errors.BadRequestAlertException;
import com.resource.server.web.rest.util.HeaderUtil;
import com.resource.server.web.rest.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * ProductsExtendResource controller
 */
@RestController
@RequestMapping("/api/products-extend")
public class ProductsExtendResource {
    private static final String ENTITY_NAME = "products";
    private final Logger log = LoggerFactory.getLogger(ProductsExtendResource.class);
    private final ProductsExtendService productExtendService;
    private final ProductsService productsService;
    private final ProductPhotoService productPhotoService;
    private final ProductsQueryService productsQueryService;

    public ProductsExtendResource(ProductsExtendService productExtendService, ProductsService productsService, ProductPhotoService productPhotoService, ProductsQueryService productsQueryService) {
        this.productExtendService = productExtendService;
        this.productsService = productsService;
        this.productPhotoService = productPhotoService;
        this.productsQueryService = productsQueryService;
    }

    @PostMapping("/products")
    public ResponseEntity createProducts(@Valid @RequestBody Products products) throws URISyntaxException {
        log.debug("REST request to save Products : {}", products);
        if (products.getId() != null) {
            throw new BadRequestAlertException("A new products cannot already have an ID", ENTITY_NAME, "idexists");
        }

        Products result = productExtendService.save(products);
//        return ResponseEntity.ok().body(products);
        return ResponseEntity.created(new URI("/api/products-extend/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    @RequestMapping(value = "/product", method = RequestMethod.GET, params = "id")
    public ResponseEntity getFullById(@RequestParam("id") Long id) {
        Optional<ProductsDTO> productsDTO = productsService.findOne(id);

        if (productsDTO == null) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
        return ResponseUtil.wrapOrNotFound(productsDTO);
    }

    @RequestMapping(value = "/related", method = RequestMethod.GET, params = "id")
    public ResponseEntity getByRelated(@RequestParam("id") Long id) {
        ProductsDTO productsDTO = productsService.findOne(id).get();
        if (productsDTO == null) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<List>(productExtendService.getRelatedProducts(productsDTO.getProductCategoryId(), id), HttpStatus.OK);
    }

    @RequestMapping(value = "/related/categories", method = RequestMethod.GET)
    public ResponseEntity<List<ProductCategoryDTO>> getRelatedCategories(
        @RequestParam(value = "keyword", required = false) String keyword,
        @RequestParam(value = "category", required = false) Long category
    ) {
        List<ProductCategoryDTO> entityList = this.productExtendService.getRelatedCategories(keyword, category);
        return ResponseEntity.ok().body(entityList);
    }

    @RequestMapping(value = "/related/colors", method = RequestMethod.GET)
    public ResponseEntity<List<String>> getRelatedColors(
        @RequestParam(value = "keyword", required = false) String keyword,
        @RequestParam(value = "category", required = false) Long category
    ) {
        List<String> entityList = this.productExtendService.getRelatedColors(keyword, category);
        return ResponseEntity.ok().body(entityList);
    }

    @RequestMapping(value = "/related/brands", method = RequestMethod.GET)
    public ResponseEntity<List<String>> getRelatedBrands(
        @RequestParam(value = "keyword", required = false) String keyword,
        @RequestParam(value = "category", required = false) Long category
    ) {
        List<String> entityList = this.productExtendService.getRelatedBrands(keyword, category);
        return ResponseEntity.ok().body(entityList);
    }

    @RequestMapping(value = "/related/pricerange", method = RequestMethod.GET)
    public ResponseEntity<Object> getRelatedPriceRange(
        @RequestParam(value = "keyword", required = false) String keyword,
        @RequestParam(value = "category", required = false) Long category
    ) {
        Object entityList = this.productExtendService.getRelatedPriceRange(keyword, category);
        return ResponseEntity.ok().body(entityList);
    }

    @RequestMapping(value = "/recent", method = RequestMethod.GET)
    public ResponseEntity getByNewlyAdded() {
        List returnList = productExtendService.findTop12ByOrderByCreatedDateDesc();
        return new ResponseEntity<List>(returnList, HttpStatus.OK);
    }

    @RequestMapping(value = "/dailydiscover", method = RequestMethod.GET)
    public ResponseEntity getByDailyDiscover() {
        List returnList = productExtendService.findTop12ByOrderByCreatedDateDesc();
        return new ResponseEntity<List>(returnList, HttpStatus.OK);
    }

    @RequestMapping(value = "/mostselling", method = RequestMethod.GET)
    public ResponseEntity getByMostSelling() {
        List returnList = productExtendService.findTop12ByOrderBySellCountDesc();
        return new ResponseEntity<List>(returnList, HttpStatus.OK);
    }

    @RequestMapping(value = "/interested", method = RequestMethod.GET)
    public ResponseEntity getByInterested() {
        List returnList = productExtendService.findTop12ByOrderBySellCountDesc();
        return new ResponseEntity<List>(returnList, HttpStatus.OK);
    }

    @RequestMapping(value = "/searchall", method = RequestMethod.GET, params = {"keyword"})
    public ResponseEntity searchAll(@RequestParam(value = "keyword", required = false) String keyword) {

        List returnList;

        returnList = productExtendService.searchProductsAll(keyword);

        return new ResponseEntity<List>(returnList, HttpStatus.OK);
    }

    @RequestMapping(value = "/search", method = RequestMethod.GET)
    public ResponseEntity<List<ProductsDTO>> search(ProductsCriteria criteria, Pageable pageable) {
        log.debug("REST request to get Products by criteria: {}", criteria);

//        if (category != null) {
//            List<Long> subCategoryList = productExtendService.getSubCategoryList(Long.parseLong(category));
//            LongFilter idList = new LongFilter();
//            idList.setIn(subCategoryList);
//            criteria.setProductSubCategoryId(idList);
//        }

        Page<ProductsDTO> page = productsQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/products-extend");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    private boolean isBlank(String param) {
        return param.isEmpty() || param.trim().equals("");
    }

    private Sort getSort(String sort) {
        switch (sort) {
            case "lowest":
                return new Sort(Sort.Direction.ASC, "price");
            case "highest":
                return new Sort(Sort.Direction.DESC, "price");
            case "name":
                return new Sort(Sort.Direction.ASC, "name");
            default:
                return null;
        }
    }

}
