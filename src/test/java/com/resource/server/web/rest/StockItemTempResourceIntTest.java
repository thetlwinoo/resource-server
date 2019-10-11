package com.resource.server.web.rest;

import com.resource.server.ResourceApp;

import com.resource.server.domain.StockItemTemp;
import com.resource.server.repository.StockItemTempRepository;
import com.resource.server.service.StockItemTempService;
import com.resource.server.service.dto.StockItemTempDTO;
import com.resource.server.service.mapper.StockItemTempMapper;
import com.resource.server.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Base64Utils;
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;


import static com.resource.server.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the StockItemTempResource REST controller.
 *
 * @see StockItemTempResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ResourceApp.class)
public class StockItemTempResourceIntTest {

    private static final String DEFAULT_STOCK_ITEM_NAME = "AAAAAAAAAA";
    private static final String UPDATED_STOCK_ITEM_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_VENDOR_CODE = "AAAAAAAAAA";
    private static final String UPDATED_VENDOR_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_VENDOR_SKU = "AAAAAAAAAA";
    private static final String UPDATED_VENDOR_SKU = "BBBBBBBBBB";

    private static final String DEFAULT_BARCODE = "AAAAAAAAAA";
    private static final String UPDATED_BARCODE = "BBBBBBBBBB";

    private static final Long DEFAULT_BARCODE_TYPE_ID = 1L;
    private static final Long UPDATED_BARCODE_TYPE_ID = 2L;

    private static final String DEFAULT_BARCODE_TYPE_NAME = "AAAAAAAAAA";
    private static final String UPDATED_BARCODE_TYPE_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_PRODUCT_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_PRODUCT_TYPE = "BBBBBBBBBB";

    private static final Long DEFAULT_PRODUCT_CATEGORY_ID = 1L;
    private static final Long UPDATED_PRODUCT_CATEGORY_ID = 2L;

    private static final String DEFAULT_PRODUCT_CATEGORY_NAME = "AAAAAAAAAA";
    private static final String UPDATED_PRODUCT_CATEGORY_NAME = "BBBBBBBBBB";

    private static final Long DEFAULT_PRODUCT_ATTRIBUTE_SET_ID = 1L;
    private static final Long UPDATED_PRODUCT_ATTRIBUTE_SET_ID = 2L;

    private static final Long DEFAULT_PRODUCT_ATTRIBUTE_ID = 1L;
    private static final Long UPDATED_PRODUCT_ATTRIBUTE_ID = 2L;

    private static final String DEFAULT_PRODUCT_ATTRIBUTE_VALUE = "AAAAAAAAAA";
    private static final String UPDATED_PRODUCT_ATTRIBUTE_VALUE = "BBBBBBBBBB";

    private static final Long DEFAULT_PRODUCT_OPTION_SET_ID = 1L;
    private static final Long UPDATED_PRODUCT_OPTION_SET_ID = 2L;

    private static final Long DEFAULT_PRODUCT_OPTION_ID = 1L;
    private static final Long UPDATED_PRODUCT_OPTION_ID = 2L;

    private static final String DEFAULT_PRODUCT_OPTION_VALUE = "AAAAAAAAAA";
    private static final String UPDATED_PRODUCT_OPTION_VALUE = "BBBBBBBBBB";

    private static final String DEFAULT_MODEL_NAME = "AAAAAAAAAA";
    private static final String UPDATED_MODEL_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_MODEL_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_MODEL_NUMBER = "BBBBBBBBBB";

    private static final Long DEFAULT_MATERIAL_ID = 1L;
    private static final Long UPDATED_MATERIAL_ID = 2L;

    private static final String DEFAULT_MATERIAL_NAME = "AAAAAAAAAA";
    private static final String UPDATED_MATERIAL_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_SHORT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_SHORT_DESCRIPTION = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final Long DEFAULT_PRODUCT_BRAND_ID = 1L;
    private static final Long UPDATED_PRODUCT_BRAND_ID = 2L;

    private static final String DEFAULT_PRODUCT_BRAND_NAME = "AAAAAAAAAA";
    private static final String UPDATED_PRODUCT_BRAND_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_HIGHLIGHTS = "AAAAAAAAAA";
    private static final String UPDATED_HIGHLIGHTS = "BBBBBBBBBB";

    private static final String DEFAULT_SEARCH_DETAILS = "AAAAAAAAAA";
    private static final String UPDATED_SEARCH_DETAILS = "BBBBBBBBBB";

    private static final String DEFAULT_CARE_INSTRUCTIONS = "AAAAAAAAAA";
    private static final String UPDATED_CARE_INSTRUCTIONS = "BBBBBBBBBB";

    private static final String DEFAULT_DANGEROUS_GOODS = "AAAAAAAAAA";
    private static final String UPDATED_DANGEROUS_GOODS = "BBBBBBBBBB";

    private static final String DEFAULT_VIDEO_URL = "AAAAAAAAAA";
    private static final String UPDATED_VIDEO_URL = "BBBBBBBBBB";

    private static final Float DEFAULT_UNIT_PRICE = 1F;
    private static final Float UPDATED_UNIT_PRICE = 2F;

    private static final Float DEFAULT_REMOMMENDED_RETAIL_PRICE = 1F;
    private static final Float UPDATED_REMOMMENDED_RETAIL_PRICE = 2F;

    private static final String DEFAULT_CURRENCY_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CURRENCY_CODE = "BBBBBBBBBB";

    private static final Integer DEFAULT_QUANTITY_ON_HAND = 1;
    private static final Integer UPDATED_QUANTITY_ON_HAND = 2;

    private static final String DEFAULT_WARRANTY_PERIOD = "AAAAAAAAAA";
    private static final String UPDATED_WARRANTY_PERIOD = "BBBBBBBBBB";

    private static final String DEFAULT_WARRANTY_POLICY = "AAAAAAAAAA";
    private static final String UPDATED_WARRANTY_POLICY = "BBBBBBBBBB";

    private static final Long DEFAULT_WARRANTY_TYPE_ID = 1L;
    private static final Long UPDATED_WARRANTY_TYPE_ID = 2L;

    private static final String DEFAULT_WARRANTY_TYPE_NAME = "AAAAAAAAAA";
    private static final String UPDATED_WARRANTY_TYPE_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_WHAT_IN_THE_BOX = "AAAAAAAAAA";
    private static final String UPDATED_WHAT_IN_THE_BOX = "BBBBBBBBBB";

    private static final Integer DEFAULT_ITEM_LENGTH = 1;
    private static final Integer UPDATED_ITEM_LENGTH = 2;

    private static final Integer DEFAULT_ITEM_WIDTH = 1;
    private static final Integer UPDATED_ITEM_WIDTH = 2;

    private static final Integer DEFAULT_ITEM_HEIGHT = 1;
    private static final Integer UPDATED_ITEM_HEIGHT = 2;

    private static final Float DEFAULT_ITEM_WEIGHT = 1F;
    private static final Float UPDATED_ITEM_WEIGHT = 2F;

    private static final Integer DEFAULT_ITEM_PACKAGE_LENGTH = 1;
    private static final Integer UPDATED_ITEM_PACKAGE_LENGTH = 2;

    private static final Integer DEFAULT_ITEM_PACKAGE_WIDTH = 1;
    private static final Integer UPDATED_ITEM_PACKAGE_WIDTH = 2;

    private static final Integer DEFAULT_ITEM_PACKAGE_HEIGHT = 1;
    private static final Integer UPDATED_ITEM_PACKAGE_HEIGHT = 2;

    private static final Float DEFAULT_ITEM_PACKAGE_WEIGHT = 1F;
    private static final Float UPDATED_ITEM_PACKAGE_WEIGHT = 2F;

    private static final Long DEFAULT_ITEM_LENGTH_UNIT_MEASURE_ID = 1L;
    private static final Long UPDATED_ITEM_LENGTH_UNIT_MEASURE_ID = 2L;

    private static final String DEFAULT_ITEM_LENGTH_UNIT_MEASURE_CODE = "AAAAAAAAAA";
    private static final String UPDATED_ITEM_LENGTH_UNIT_MEASURE_CODE = "BBBBBBBBBB";

    private static final Long DEFAULT_ITEM_WIDTH_UNIT_MEASURE_ID = 1L;
    private static final Long UPDATED_ITEM_WIDTH_UNIT_MEASURE_ID = 2L;

    private static final String DEFAULT_ITEM_WIDTH_UNIT_MEASURE_CODE = "AAAAAAAAAA";
    private static final String UPDATED_ITEM_WIDTH_UNIT_MEASURE_CODE = "BBBBBBBBBB";

    private static final Long DEFAULT_ITEM_HEIGHT_UNIT_MEASURE_ID = 1L;
    private static final Long UPDATED_ITEM_HEIGHT_UNIT_MEASURE_ID = 2L;

    private static final String DEFAULT_ITEM_HEIGHT_UNIT_MEASURE_CODE = "AAAAAAAAAA";
    private static final String UPDATED_ITEM_HEIGHT_UNIT_MEASURE_CODE = "BBBBBBBBBB";

    private static final Long DEFAULT_ITEM_WEIGHT_UNIT_MEASURE_ID = 1L;
    private static final Long UPDATED_ITEM_WEIGHT_UNIT_MEASURE_ID = 2L;

    private static final String DEFAULT_ITEM_WEIGHT_UNIT_MEASURE_CODE = "AAAAAAAAAA";
    private static final String UPDATED_ITEM_WEIGHT_UNIT_MEASURE_CODE = "BBBBBBBBBB";

    private static final Long DEFAULT_ITEM_PACKAGE_LENGTH_UNIT_MEASURE_ID = 1L;
    private static final Long UPDATED_ITEM_PACKAGE_LENGTH_UNIT_MEASURE_ID = 2L;

    private static final String DEFAULT_ITEM_PACKAGE_LENGTH_UNIT_MEASURE_CODE = "AAAAAAAAAA";
    private static final String UPDATED_ITEM_PACKAGE_LENGTH_UNIT_MEASURE_CODE = "BBBBBBBBBB";

    private static final Long DEFAULT_ITEM_PACKAGE_WIDTH_UNIT_MEASURE_ID = 1L;
    private static final Long UPDATED_ITEM_PACKAGE_WIDTH_UNIT_MEASURE_ID = 2L;

    private static final String DEFAULT_ITEM_PACKAGE_WIDTH_UNIT_MEASURE_CODE = "AAAAAAAAAA";
    private static final String UPDATED_ITEM_PACKAGE_WIDTH_UNIT_MEASURE_CODE = "BBBBBBBBBB";

    private static final Long DEFAULT_ITEM_PACKAGE_HEIGHT_UNIT_MEASURE_ID = 1L;
    private static final Long UPDATED_ITEM_PACKAGE_HEIGHT_UNIT_MEASURE_ID = 2L;

    private static final String DEFAULT_ITEM_PACKAGE_HEIGHT_UNIT_MEASURE_CODE = "AAAAAAAAAA";
    private static final String UPDATED_ITEM_PACKAGE_HEIGHT_UNIT_MEASURE_CODE = "BBBBBBBBBB";

    private static final Long DEFAULT_ITEM_PACKAGE_WEIGHT_UNIT_MEASURE_ID = 1L;
    private static final Long UPDATED_ITEM_PACKAGE_WEIGHT_UNIT_MEASURE_ID = 2L;

    private static final String DEFAULT_ITEM_PACKAGE_WEIGHT_UNIT_MEASURE_CODE = "AAAAAAAAAA";
    private static final String UPDATED_ITEM_PACKAGE_WEIGHT_UNIT_MEASURE_CODE = "BBBBBBBBBB";

    private static final Integer DEFAULT_NO_OF_PIECES = 1;
    private static final Integer UPDATED_NO_OF_PIECES = 2;

    private static final Integer DEFAULT_NO_OF_ITEMS = 1;
    private static final Integer UPDATED_NO_OF_ITEMS = 2;

    private static final String DEFAULT_MANUFACTURE = "AAAAAAAAAA";
    private static final String UPDATED_MANUFACTURE = "BBBBBBBBBB";

    private static final String DEFAULT_SPECIAL_FEACTURES = "AAAAAAAAAA";
    private static final String UPDATED_SPECIAL_FEACTURES = "BBBBBBBBBB";

    private static final String DEFAULT_PRODUCT_COMPLIANCE_CERTIFICATE = "AAAAAAAAAA";
    private static final String UPDATED_PRODUCT_COMPLIANCE_CERTIFICATE = "BBBBBBBBBB";

    private static final Boolean DEFAULT_GENUINE_AND_LEGAL = false;
    private static final Boolean UPDATED_GENUINE_AND_LEGAL = true;

    private static final String DEFAULT_COUNTRY_OF_ORIGIN = "AAAAAAAAAA";
    private static final String UPDATED_COUNTRY_OF_ORIGIN = "BBBBBBBBBB";

    private static final String DEFAULT_USAGE_AND_SIDE_EFFECTS = "AAAAAAAAAA";
    private static final String UPDATED_USAGE_AND_SIDE_EFFECTS = "BBBBBBBBBB";

    private static final String DEFAULT_SAFETY_WARNNING = "AAAAAAAAAA";
    private static final String UPDATED_SAFETY_WARNNING = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_SELL_START_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_SELL_START_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_SELL_END_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_SELL_END_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final Integer DEFAULT_STATUS = 1;
    private static final Integer UPDATED_STATUS = 2;

    @Autowired
    private StockItemTempRepository stockItemTempRepository;

    @Autowired
    private StockItemTempMapper stockItemTempMapper;

    @Autowired
    private StockItemTempService stockItemTempService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    @Autowired
    private Validator validator;

    private MockMvc restStockItemTempMockMvc;

    private StockItemTemp stockItemTemp;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final StockItemTempResource stockItemTempResource = new StockItemTempResource(stockItemTempService);
        this.restStockItemTempMockMvc = MockMvcBuilders.standaloneSetup(stockItemTempResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter)
            .setValidator(validator).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static StockItemTemp createEntity(EntityManager em) {
        StockItemTemp stockItemTemp = new StockItemTemp()
            .stockItemName(DEFAULT_STOCK_ITEM_NAME)
            .vendorCode(DEFAULT_VENDOR_CODE)
            .vendorSKU(DEFAULT_VENDOR_SKU)
            .barcode(DEFAULT_BARCODE)
            .barcodeTypeId(DEFAULT_BARCODE_TYPE_ID)
            .barcodeTypeName(DEFAULT_BARCODE_TYPE_NAME)
            .productType(DEFAULT_PRODUCT_TYPE)
            .productCategoryId(DEFAULT_PRODUCT_CATEGORY_ID)
            .productCategoryName(DEFAULT_PRODUCT_CATEGORY_NAME)
            .productAttributeSetId(DEFAULT_PRODUCT_ATTRIBUTE_SET_ID)
            .productAttributeId(DEFAULT_PRODUCT_ATTRIBUTE_ID)
            .productAttributeValue(DEFAULT_PRODUCT_ATTRIBUTE_VALUE)
            .productOptionSetId(DEFAULT_PRODUCT_OPTION_SET_ID)
            .productOptionId(DEFAULT_PRODUCT_OPTION_ID)
            .productOptionValue(DEFAULT_PRODUCT_OPTION_VALUE)
            .modelName(DEFAULT_MODEL_NAME)
            .modelNumber(DEFAULT_MODEL_NUMBER)
            .materialId(DEFAULT_MATERIAL_ID)
            .materialName(DEFAULT_MATERIAL_NAME)
            .shortDescription(DEFAULT_SHORT_DESCRIPTION)
            .description(DEFAULT_DESCRIPTION)
            .productBrandId(DEFAULT_PRODUCT_BRAND_ID)
            .productBrandName(DEFAULT_PRODUCT_BRAND_NAME)
            .highlights(DEFAULT_HIGHLIGHTS)
            .searchDetails(DEFAULT_SEARCH_DETAILS)
            .careInstructions(DEFAULT_CARE_INSTRUCTIONS)
            .dangerousGoods(DEFAULT_DANGEROUS_GOODS)
            .videoUrl(DEFAULT_VIDEO_URL)
            .unitPrice(DEFAULT_UNIT_PRICE)
            .remommendedRetailPrice(DEFAULT_REMOMMENDED_RETAIL_PRICE)
            .currencyCode(DEFAULT_CURRENCY_CODE)
            .quantityOnHand(DEFAULT_QUANTITY_ON_HAND)
            .warrantyPeriod(DEFAULT_WARRANTY_PERIOD)
            .warrantyPolicy(DEFAULT_WARRANTY_POLICY)
            .warrantyTypeId(DEFAULT_WARRANTY_TYPE_ID)
            .warrantyTypeName(DEFAULT_WARRANTY_TYPE_NAME)
            .whatInTheBox(DEFAULT_WHAT_IN_THE_BOX)
            .itemLength(DEFAULT_ITEM_LENGTH)
            .itemWidth(DEFAULT_ITEM_WIDTH)
            .itemHeight(DEFAULT_ITEM_HEIGHT)
            .itemWeight(DEFAULT_ITEM_WEIGHT)
            .itemPackageLength(DEFAULT_ITEM_PACKAGE_LENGTH)
            .itemPackageWidth(DEFAULT_ITEM_PACKAGE_WIDTH)
            .itemPackageHeight(DEFAULT_ITEM_PACKAGE_HEIGHT)
            .itemPackageWeight(DEFAULT_ITEM_PACKAGE_WEIGHT)
            .itemLengthUnitMeasureId(DEFAULT_ITEM_LENGTH_UNIT_MEASURE_ID)
            .itemLengthUnitMeasureCode(DEFAULT_ITEM_LENGTH_UNIT_MEASURE_CODE)
            .itemWidthUnitMeasureId(DEFAULT_ITEM_WIDTH_UNIT_MEASURE_ID)
            .itemWidthUnitMeasureCode(DEFAULT_ITEM_WIDTH_UNIT_MEASURE_CODE)
            .itemHeightUnitMeasureId(DEFAULT_ITEM_HEIGHT_UNIT_MEASURE_ID)
            .itemHeightUnitMeasureCode(DEFAULT_ITEM_HEIGHT_UNIT_MEASURE_CODE)
            .itemWeightUnitMeasureId(DEFAULT_ITEM_WEIGHT_UNIT_MEASURE_ID)
            .itemWeightUnitMeasureCode(DEFAULT_ITEM_WEIGHT_UNIT_MEASURE_CODE)
            .itemPackageLengthUnitMeasureId(DEFAULT_ITEM_PACKAGE_LENGTH_UNIT_MEASURE_ID)
            .itemPackageLengthUnitMeasureCode(DEFAULT_ITEM_PACKAGE_LENGTH_UNIT_MEASURE_CODE)
            .itemPackageWidthUnitMeasureId(DEFAULT_ITEM_PACKAGE_WIDTH_UNIT_MEASURE_ID)
            .itemPackageWidthUnitMeasureCode(DEFAULT_ITEM_PACKAGE_WIDTH_UNIT_MEASURE_CODE)
            .itemPackageHeightUnitMeasureId(DEFAULT_ITEM_PACKAGE_HEIGHT_UNIT_MEASURE_ID)
            .itemPackageHeightUnitMeasureCode(DEFAULT_ITEM_PACKAGE_HEIGHT_UNIT_MEASURE_CODE)
            .itemPackageWeightUnitMeasureId(DEFAULT_ITEM_PACKAGE_WEIGHT_UNIT_MEASURE_ID)
            .itemPackageWeightUnitMeasureCode(DEFAULT_ITEM_PACKAGE_WEIGHT_UNIT_MEASURE_CODE)
            .noOfPieces(DEFAULT_NO_OF_PIECES)
            .noOfItems(DEFAULT_NO_OF_ITEMS)
            .manufacture(DEFAULT_MANUFACTURE)
            .specialFeactures(DEFAULT_SPECIAL_FEACTURES)
            .productComplianceCertificate(DEFAULT_PRODUCT_COMPLIANCE_CERTIFICATE)
            .genuineAndLegal(DEFAULT_GENUINE_AND_LEGAL)
            .countryOfOrigin(DEFAULT_COUNTRY_OF_ORIGIN)
            .usageAndSideEffects(DEFAULT_USAGE_AND_SIDE_EFFECTS)
            .safetyWarnning(DEFAULT_SAFETY_WARNNING)
            .sellStartDate(DEFAULT_SELL_START_DATE)
            .sellEndDate(DEFAULT_SELL_END_DATE)
            .status(DEFAULT_STATUS);
        return stockItemTemp;
    }

    @Before
    public void initTest() {
        stockItemTemp = createEntity(em);
    }

    @Test
    @Transactional
    public void createStockItemTemp() throws Exception {
        int databaseSizeBeforeCreate = stockItemTempRepository.findAll().size();

        // Create the StockItemTemp
        StockItemTempDTO stockItemTempDTO = stockItemTempMapper.toDto(stockItemTemp);
        restStockItemTempMockMvc.perform(post("/api/stock-item-temps")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(stockItemTempDTO)))
            .andExpect(status().isCreated());

        // Validate the StockItemTemp in the database
        List<StockItemTemp> stockItemTempList = stockItemTempRepository.findAll();
        assertThat(stockItemTempList).hasSize(databaseSizeBeforeCreate + 1);
        StockItemTemp testStockItemTemp = stockItemTempList.get(stockItemTempList.size() - 1);
        assertThat(testStockItemTemp.getStockItemName()).isEqualTo(DEFAULT_STOCK_ITEM_NAME);
        assertThat(testStockItemTemp.getVendorCode()).isEqualTo(DEFAULT_VENDOR_CODE);
        assertThat(testStockItemTemp.getVendorSKU()).isEqualTo(DEFAULT_VENDOR_SKU);
        assertThat(testStockItemTemp.getBarcode()).isEqualTo(DEFAULT_BARCODE);
        assertThat(testStockItemTemp.getBarcodeTypeId()).isEqualTo(DEFAULT_BARCODE_TYPE_ID);
        assertThat(testStockItemTemp.getBarcodeTypeName()).isEqualTo(DEFAULT_BARCODE_TYPE_NAME);
        assertThat(testStockItemTemp.getProductType()).isEqualTo(DEFAULT_PRODUCT_TYPE);
        assertThat(testStockItemTemp.getProductCategoryId()).isEqualTo(DEFAULT_PRODUCT_CATEGORY_ID);
        assertThat(testStockItemTemp.getProductCategoryName()).isEqualTo(DEFAULT_PRODUCT_CATEGORY_NAME);
        assertThat(testStockItemTemp.getProductAttributeSetId()).isEqualTo(DEFAULT_PRODUCT_ATTRIBUTE_SET_ID);
        assertThat(testStockItemTemp.getProductAttributeId()).isEqualTo(DEFAULT_PRODUCT_ATTRIBUTE_ID);
        assertThat(testStockItemTemp.getProductAttributeValue()).isEqualTo(DEFAULT_PRODUCT_ATTRIBUTE_VALUE);
        assertThat(testStockItemTemp.getProductOptionSetId()).isEqualTo(DEFAULT_PRODUCT_OPTION_SET_ID);
        assertThat(testStockItemTemp.getProductOptionId()).isEqualTo(DEFAULT_PRODUCT_OPTION_ID);
        assertThat(testStockItemTemp.getProductOptionValue()).isEqualTo(DEFAULT_PRODUCT_OPTION_VALUE);
        assertThat(testStockItemTemp.getModelName()).isEqualTo(DEFAULT_MODEL_NAME);
        assertThat(testStockItemTemp.getModelNumber()).isEqualTo(DEFAULT_MODEL_NUMBER);
        assertThat(testStockItemTemp.getMaterialId()).isEqualTo(DEFAULT_MATERIAL_ID);
        assertThat(testStockItemTemp.getMaterialName()).isEqualTo(DEFAULT_MATERIAL_NAME);
        assertThat(testStockItemTemp.getShortDescription()).isEqualTo(DEFAULT_SHORT_DESCRIPTION);
        assertThat(testStockItemTemp.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testStockItemTemp.getProductBrandId()).isEqualTo(DEFAULT_PRODUCT_BRAND_ID);
        assertThat(testStockItemTemp.getProductBrandName()).isEqualTo(DEFAULT_PRODUCT_BRAND_NAME);
        assertThat(testStockItemTemp.getHighlights()).isEqualTo(DEFAULT_HIGHLIGHTS);
        assertThat(testStockItemTemp.getSearchDetails()).isEqualTo(DEFAULT_SEARCH_DETAILS);
        assertThat(testStockItemTemp.getCareInstructions()).isEqualTo(DEFAULT_CARE_INSTRUCTIONS);
        assertThat(testStockItemTemp.getDangerousGoods()).isEqualTo(DEFAULT_DANGEROUS_GOODS);
        assertThat(testStockItemTemp.getVideoUrl()).isEqualTo(DEFAULT_VIDEO_URL);
        assertThat(testStockItemTemp.getUnitPrice()).isEqualTo(DEFAULT_UNIT_PRICE);
        assertThat(testStockItemTemp.getRemommendedRetailPrice()).isEqualTo(DEFAULT_REMOMMENDED_RETAIL_PRICE);
        assertThat(testStockItemTemp.getCurrencyCode()).isEqualTo(DEFAULT_CURRENCY_CODE);
        assertThat(testStockItemTemp.getQuantityOnHand()).isEqualTo(DEFAULT_QUANTITY_ON_HAND);
        assertThat(testStockItemTemp.getWarrantyPeriod()).isEqualTo(DEFAULT_WARRANTY_PERIOD);
        assertThat(testStockItemTemp.getWarrantyPolicy()).isEqualTo(DEFAULT_WARRANTY_POLICY);
        assertThat(testStockItemTemp.getWarrantyTypeId()).isEqualTo(DEFAULT_WARRANTY_TYPE_ID);
        assertThat(testStockItemTemp.getWarrantyTypeName()).isEqualTo(DEFAULT_WARRANTY_TYPE_NAME);
        assertThat(testStockItemTemp.getWhatInTheBox()).isEqualTo(DEFAULT_WHAT_IN_THE_BOX);
        assertThat(testStockItemTemp.getItemLength()).isEqualTo(DEFAULT_ITEM_LENGTH);
        assertThat(testStockItemTemp.getItemWidth()).isEqualTo(DEFAULT_ITEM_WIDTH);
        assertThat(testStockItemTemp.getItemHeight()).isEqualTo(DEFAULT_ITEM_HEIGHT);
        assertThat(testStockItemTemp.getItemWeight()).isEqualTo(DEFAULT_ITEM_WEIGHT);
        assertThat(testStockItemTemp.getItemPackageLength()).isEqualTo(DEFAULT_ITEM_PACKAGE_LENGTH);
        assertThat(testStockItemTemp.getItemPackageWidth()).isEqualTo(DEFAULT_ITEM_PACKAGE_WIDTH);
        assertThat(testStockItemTemp.getItemPackageHeight()).isEqualTo(DEFAULT_ITEM_PACKAGE_HEIGHT);
        assertThat(testStockItemTemp.getItemPackageWeight()).isEqualTo(DEFAULT_ITEM_PACKAGE_WEIGHT);
        assertThat(testStockItemTemp.getItemLengthUnitMeasureId()).isEqualTo(DEFAULT_ITEM_LENGTH_UNIT_MEASURE_ID);
        assertThat(testStockItemTemp.getItemLengthUnitMeasureCode()).isEqualTo(DEFAULT_ITEM_LENGTH_UNIT_MEASURE_CODE);
        assertThat(testStockItemTemp.getItemWidthUnitMeasureId()).isEqualTo(DEFAULT_ITEM_WIDTH_UNIT_MEASURE_ID);
        assertThat(testStockItemTemp.getItemWidthUnitMeasureCode()).isEqualTo(DEFAULT_ITEM_WIDTH_UNIT_MEASURE_CODE);
        assertThat(testStockItemTemp.getItemHeightUnitMeasureId()).isEqualTo(DEFAULT_ITEM_HEIGHT_UNIT_MEASURE_ID);
        assertThat(testStockItemTemp.getItemHeightUnitMeasureCode()).isEqualTo(DEFAULT_ITEM_HEIGHT_UNIT_MEASURE_CODE);
        assertThat(testStockItemTemp.getItemWeightUnitMeasureId()).isEqualTo(DEFAULT_ITEM_WEIGHT_UNIT_MEASURE_ID);
        assertThat(testStockItemTemp.getItemWeightUnitMeasureCode()).isEqualTo(DEFAULT_ITEM_WEIGHT_UNIT_MEASURE_CODE);
        assertThat(testStockItemTemp.getItemPackageLengthUnitMeasureId()).isEqualTo(DEFAULT_ITEM_PACKAGE_LENGTH_UNIT_MEASURE_ID);
        assertThat(testStockItemTemp.getItemPackageLengthUnitMeasureCode()).isEqualTo(DEFAULT_ITEM_PACKAGE_LENGTH_UNIT_MEASURE_CODE);
        assertThat(testStockItemTemp.getItemPackageWidthUnitMeasureId()).isEqualTo(DEFAULT_ITEM_PACKAGE_WIDTH_UNIT_MEASURE_ID);
        assertThat(testStockItemTemp.getItemPackageWidthUnitMeasureCode()).isEqualTo(DEFAULT_ITEM_PACKAGE_WIDTH_UNIT_MEASURE_CODE);
        assertThat(testStockItemTemp.getItemPackageHeightUnitMeasureId()).isEqualTo(DEFAULT_ITEM_PACKAGE_HEIGHT_UNIT_MEASURE_ID);
        assertThat(testStockItemTemp.getItemPackageHeightUnitMeasureCode()).isEqualTo(DEFAULT_ITEM_PACKAGE_HEIGHT_UNIT_MEASURE_CODE);
        assertThat(testStockItemTemp.getItemPackageWeightUnitMeasureId()).isEqualTo(DEFAULT_ITEM_PACKAGE_WEIGHT_UNIT_MEASURE_ID);
        assertThat(testStockItemTemp.getItemPackageWeightUnitMeasureCode()).isEqualTo(DEFAULT_ITEM_PACKAGE_WEIGHT_UNIT_MEASURE_CODE);
        assertThat(testStockItemTemp.getNoOfPieces()).isEqualTo(DEFAULT_NO_OF_PIECES);
        assertThat(testStockItemTemp.getNoOfItems()).isEqualTo(DEFAULT_NO_OF_ITEMS);
        assertThat(testStockItemTemp.getManufacture()).isEqualTo(DEFAULT_MANUFACTURE);
        assertThat(testStockItemTemp.getSpecialFeactures()).isEqualTo(DEFAULT_SPECIAL_FEACTURES);
        assertThat(testStockItemTemp.getProductComplianceCertificate()).isEqualTo(DEFAULT_PRODUCT_COMPLIANCE_CERTIFICATE);
        assertThat(testStockItemTemp.isGenuineAndLegal()).isEqualTo(DEFAULT_GENUINE_AND_LEGAL);
        assertThat(testStockItemTemp.getCountryOfOrigin()).isEqualTo(DEFAULT_COUNTRY_OF_ORIGIN);
        assertThat(testStockItemTemp.getUsageAndSideEffects()).isEqualTo(DEFAULT_USAGE_AND_SIDE_EFFECTS);
        assertThat(testStockItemTemp.getSafetyWarnning()).isEqualTo(DEFAULT_SAFETY_WARNNING);
        assertThat(testStockItemTemp.getSellStartDate()).isEqualTo(DEFAULT_SELL_START_DATE);
        assertThat(testStockItemTemp.getSellEndDate()).isEqualTo(DEFAULT_SELL_END_DATE);
        assertThat(testStockItemTemp.getStatus()).isEqualTo(DEFAULT_STATUS);
    }

    @Test
    @Transactional
    public void createStockItemTempWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = stockItemTempRepository.findAll().size();

        // Create the StockItemTemp with an existing ID
        stockItemTemp.setId(1L);
        StockItemTempDTO stockItemTempDTO = stockItemTempMapper.toDto(stockItemTemp);

        // An entity with an existing ID cannot be created, so this API call must fail
        restStockItemTempMockMvc.perform(post("/api/stock-item-temps")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(stockItemTempDTO)))
            .andExpect(status().isBadRequest());

        // Validate the StockItemTemp in the database
        List<StockItemTemp> stockItemTempList = stockItemTempRepository.findAll();
        assertThat(stockItemTempList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkStockItemNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = stockItemTempRepository.findAll().size();
        // set the field null
        stockItemTemp.setStockItemName(null);

        // Create the StockItemTemp, which fails.
        StockItemTempDTO stockItemTempDTO = stockItemTempMapper.toDto(stockItemTemp);

        restStockItemTempMockMvc.perform(post("/api/stock-item-temps")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(stockItemTempDTO)))
            .andExpect(status().isBadRequest());

        List<StockItemTemp> stockItemTempList = stockItemTempRepository.findAll();
        assertThat(stockItemTempList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkVendorCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = stockItemTempRepository.findAll().size();
        // set the field null
        stockItemTemp.setVendorCode(null);

        // Create the StockItemTemp, which fails.
        StockItemTempDTO stockItemTempDTO = stockItemTempMapper.toDto(stockItemTemp);

        restStockItemTempMockMvc.perform(post("/api/stock-item-temps")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(stockItemTempDTO)))
            .andExpect(status().isBadRequest());

        List<StockItemTemp> stockItemTempList = stockItemTempRepository.findAll();
        assertThat(stockItemTempList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkVendorSKUIsRequired() throws Exception {
        int databaseSizeBeforeTest = stockItemTempRepository.findAll().size();
        // set the field null
        stockItemTemp.setVendorSKU(null);

        // Create the StockItemTemp, which fails.
        StockItemTempDTO stockItemTempDTO = stockItemTempMapper.toDto(stockItemTemp);

        restStockItemTempMockMvc.perform(post("/api/stock-item-temps")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(stockItemTempDTO)))
            .andExpect(status().isBadRequest());

        List<StockItemTemp> stockItemTempList = stockItemTempRepository.findAll();
        assertThat(stockItemTempList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkProductCategoryIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = stockItemTempRepository.findAll().size();
        // set the field null
        stockItemTemp.setProductCategoryId(null);

        // Create the StockItemTemp, which fails.
        StockItemTempDTO stockItemTempDTO = stockItemTempMapper.toDto(stockItemTemp);

        restStockItemTempMockMvc.perform(post("/api/stock-item-temps")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(stockItemTempDTO)))
            .andExpect(status().isBadRequest());

        List<StockItemTemp> stockItemTempList = stockItemTempRepository.findAll();
        assertThat(stockItemTempList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllStockItemTemps() throws Exception {
        // Initialize the database
        stockItemTempRepository.saveAndFlush(stockItemTemp);

        // Get all the stockItemTempList
        restStockItemTempMockMvc.perform(get("/api/stock-item-temps?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(stockItemTemp.getId().intValue())))
            .andExpect(jsonPath("$.[*].stockItemName").value(hasItem(DEFAULT_STOCK_ITEM_NAME.toString())))
            .andExpect(jsonPath("$.[*].vendorCode").value(hasItem(DEFAULT_VENDOR_CODE.toString())))
            .andExpect(jsonPath("$.[*].vendorSKU").value(hasItem(DEFAULT_VENDOR_SKU.toString())))
            .andExpect(jsonPath("$.[*].barcode").value(hasItem(DEFAULT_BARCODE.toString())))
            .andExpect(jsonPath("$.[*].barcodeTypeId").value(hasItem(DEFAULT_BARCODE_TYPE_ID.intValue())))
            .andExpect(jsonPath("$.[*].barcodeTypeName").value(hasItem(DEFAULT_BARCODE_TYPE_NAME.toString())))
            .andExpect(jsonPath("$.[*].productType").value(hasItem(DEFAULT_PRODUCT_TYPE.toString())))
            .andExpect(jsonPath("$.[*].productCategoryId").value(hasItem(DEFAULT_PRODUCT_CATEGORY_ID.intValue())))
            .andExpect(jsonPath("$.[*].productCategoryName").value(hasItem(DEFAULT_PRODUCT_CATEGORY_NAME.toString())))
            .andExpect(jsonPath("$.[*].productAttributeSetId").value(hasItem(DEFAULT_PRODUCT_ATTRIBUTE_SET_ID.intValue())))
            .andExpect(jsonPath("$.[*].productAttributeId").value(hasItem(DEFAULT_PRODUCT_ATTRIBUTE_ID.intValue())))
            .andExpect(jsonPath("$.[*].productAttributeValue").value(hasItem(DEFAULT_PRODUCT_ATTRIBUTE_VALUE.toString())))
            .andExpect(jsonPath("$.[*].productOptionSetId").value(hasItem(DEFAULT_PRODUCT_OPTION_SET_ID.intValue())))
            .andExpect(jsonPath("$.[*].productOptionId").value(hasItem(DEFAULT_PRODUCT_OPTION_ID.intValue())))
            .andExpect(jsonPath("$.[*].productOptionValue").value(hasItem(DEFAULT_PRODUCT_OPTION_VALUE.toString())))
            .andExpect(jsonPath("$.[*].modelName").value(hasItem(DEFAULT_MODEL_NAME.toString())))
            .andExpect(jsonPath("$.[*].modelNumber").value(hasItem(DEFAULT_MODEL_NUMBER.toString())))
            .andExpect(jsonPath("$.[*].materialId").value(hasItem(DEFAULT_MATERIAL_ID.intValue())))
            .andExpect(jsonPath("$.[*].materialName").value(hasItem(DEFAULT_MATERIAL_NAME.toString())))
            .andExpect(jsonPath("$.[*].shortDescription").value(hasItem(DEFAULT_SHORT_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].productBrandId").value(hasItem(DEFAULT_PRODUCT_BRAND_ID.intValue())))
            .andExpect(jsonPath("$.[*].productBrandName").value(hasItem(DEFAULT_PRODUCT_BRAND_NAME.toString())))
            .andExpect(jsonPath("$.[*].highlights").value(hasItem(DEFAULT_HIGHLIGHTS.toString())))
            .andExpect(jsonPath("$.[*].searchDetails").value(hasItem(DEFAULT_SEARCH_DETAILS.toString())))
            .andExpect(jsonPath("$.[*].careInstructions").value(hasItem(DEFAULT_CARE_INSTRUCTIONS.toString())))
            .andExpect(jsonPath("$.[*].dangerousGoods").value(hasItem(DEFAULT_DANGEROUS_GOODS.toString())))
            .andExpect(jsonPath("$.[*].videoUrl").value(hasItem(DEFAULT_VIDEO_URL.toString())))
            .andExpect(jsonPath("$.[*].unitPrice").value(hasItem(DEFAULT_UNIT_PRICE.doubleValue())))
            .andExpect(jsonPath("$.[*].remommendedRetailPrice").value(hasItem(DEFAULT_REMOMMENDED_RETAIL_PRICE.doubleValue())))
            .andExpect(jsonPath("$.[*].currencyCode").value(hasItem(DEFAULT_CURRENCY_CODE.toString())))
            .andExpect(jsonPath("$.[*].quantityOnHand").value(hasItem(DEFAULT_QUANTITY_ON_HAND)))
            .andExpect(jsonPath("$.[*].warrantyPeriod").value(hasItem(DEFAULT_WARRANTY_PERIOD.toString())))
            .andExpect(jsonPath("$.[*].warrantyPolicy").value(hasItem(DEFAULT_WARRANTY_POLICY.toString())))
            .andExpect(jsonPath("$.[*].warrantyTypeId").value(hasItem(DEFAULT_WARRANTY_TYPE_ID.intValue())))
            .andExpect(jsonPath("$.[*].warrantyTypeName").value(hasItem(DEFAULT_WARRANTY_TYPE_NAME.toString())))
            .andExpect(jsonPath("$.[*].whatInTheBox").value(hasItem(DEFAULT_WHAT_IN_THE_BOX.toString())))
            .andExpect(jsonPath("$.[*].itemLength").value(hasItem(DEFAULT_ITEM_LENGTH)))
            .andExpect(jsonPath("$.[*].itemWidth").value(hasItem(DEFAULT_ITEM_WIDTH)))
            .andExpect(jsonPath("$.[*].itemHeight").value(hasItem(DEFAULT_ITEM_HEIGHT)))
            .andExpect(jsonPath("$.[*].itemWeight").value(hasItem(DEFAULT_ITEM_WEIGHT.doubleValue())))
            .andExpect(jsonPath("$.[*].itemPackageLength").value(hasItem(DEFAULT_ITEM_PACKAGE_LENGTH)))
            .andExpect(jsonPath("$.[*].itemPackageWidth").value(hasItem(DEFAULT_ITEM_PACKAGE_WIDTH)))
            .andExpect(jsonPath("$.[*].itemPackageHeight").value(hasItem(DEFAULT_ITEM_PACKAGE_HEIGHT)))
            .andExpect(jsonPath("$.[*].itemPackageWeight").value(hasItem(DEFAULT_ITEM_PACKAGE_WEIGHT.doubleValue())))
            .andExpect(jsonPath("$.[*].itemLengthUnitMeasureId").value(hasItem(DEFAULT_ITEM_LENGTH_UNIT_MEASURE_ID.intValue())))
            .andExpect(jsonPath("$.[*].itemLengthUnitMeasureCode").value(hasItem(DEFAULT_ITEM_LENGTH_UNIT_MEASURE_CODE.toString())))
            .andExpect(jsonPath("$.[*].itemWidthUnitMeasureId").value(hasItem(DEFAULT_ITEM_WIDTH_UNIT_MEASURE_ID.intValue())))
            .andExpect(jsonPath("$.[*].itemWidthUnitMeasureCode").value(hasItem(DEFAULT_ITEM_WIDTH_UNIT_MEASURE_CODE.toString())))
            .andExpect(jsonPath("$.[*].itemHeightUnitMeasureId").value(hasItem(DEFAULT_ITEM_HEIGHT_UNIT_MEASURE_ID.intValue())))
            .andExpect(jsonPath("$.[*].itemHeightUnitMeasureCode").value(hasItem(DEFAULT_ITEM_HEIGHT_UNIT_MEASURE_CODE.toString())))
            .andExpect(jsonPath("$.[*].itemWeightUnitMeasureId").value(hasItem(DEFAULT_ITEM_WEIGHT_UNIT_MEASURE_ID.intValue())))
            .andExpect(jsonPath("$.[*].itemWeightUnitMeasureCode").value(hasItem(DEFAULT_ITEM_WEIGHT_UNIT_MEASURE_CODE.toString())))
            .andExpect(jsonPath("$.[*].itemPackageLengthUnitMeasureId").value(hasItem(DEFAULT_ITEM_PACKAGE_LENGTH_UNIT_MEASURE_ID.intValue())))
            .andExpect(jsonPath("$.[*].itemPackageLengthUnitMeasureCode").value(hasItem(DEFAULT_ITEM_PACKAGE_LENGTH_UNIT_MEASURE_CODE.toString())))
            .andExpect(jsonPath("$.[*].itemPackageWidthUnitMeasureId").value(hasItem(DEFAULT_ITEM_PACKAGE_WIDTH_UNIT_MEASURE_ID.intValue())))
            .andExpect(jsonPath("$.[*].itemPackageWidthUnitMeasureCode").value(hasItem(DEFAULT_ITEM_PACKAGE_WIDTH_UNIT_MEASURE_CODE.toString())))
            .andExpect(jsonPath("$.[*].itemPackageHeightUnitMeasureId").value(hasItem(DEFAULT_ITEM_PACKAGE_HEIGHT_UNIT_MEASURE_ID.intValue())))
            .andExpect(jsonPath("$.[*].itemPackageHeightUnitMeasureCode").value(hasItem(DEFAULT_ITEM_PACKAGE_HEIGHT_UNIT_MEASURE_CODE.toString())))
            .andExpect(jsonPath("$.[*].itemPackageWeightUnitMeasureId").value(hasItem(DEFAULT_ITEM_PACKAGE_WEIGHT_UNIT_MEASURE_ID.intValue())))
            .andExpect(jsonPath("$.[*].itemPackageWeightUnitMeasureCode").value(hasItem(DEFAULT_ITEM_PACKAGE_WEIGHT_UNIT_MEASURE_CODE.toString())))
            .andExpect(jsonPath("$.[*].noOfPieces").value(hasItem(DEFAULT_NO_OF_PIECES)))
            .andExpect(jsonPath("$.[*].noOfItems").value(hasItem(DEFAULT_NO_OF_ITEMS)))
            .andExpect(jsonPath("$.[*].manufacture").value(hasItem(DEFAULT_MANUFACTURE.toString())))
            .andExpect(jsonPath("$.[*].specialFeactures").value(hasItem(DEFAULT_SPECIAL_FEACTURES.toString())))
            .andExpect(jsonPath("$.[*].productComplianceCertificate").value(hasItem(DEFAULT_PRODUCT_COMPLIANCE_CERTIFICATE.toString())))
            .andExpect(jsonPath("$.[*].genuineAndLegal").value(hasItem(DEFAULT_GENUINE_AND_LEGAL.booleanValue())))
            .andExpect(jsonPath("$.[*].countryOfOrigin").value(hasItem(DEFAULT_COUNTRY_OF_ORIGIN.toString())))
            .andExpect(jsonPath("$.[*].usageAndSideEffects").value(hasItem(DEFAULT_USAGE_AND_SIDE_EFFECTS.toString())))
            .andExpect(jsonPath("$.[*].safetyWarnning").value(hasItem(DEFAULT_SAFETY_WARNNING.toString())))
            .andExpect(jsonPath("$.[*].sellStartDate").value(hasItem(DEFAULT_SELL_START_DATE.toString())))
            .andExpect(jsonPath("$.[*].sellEndDate").value(hasItem(DEFAULT_SELL_END_DATE.toString())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS)));
    }
    
    @Test
    @Transactional
    public void getStockItemTemp() throws Exception {
        // Initialize the database
        stockItemTempRepository.saveAndFlush(stockItemTemp);

        // Get the stockItemTemp
        restStockItemTempMockMvc.perform(get("/api/stock-item-temps/{id}", stockItemTemp.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(stockItemTemp.getId().intValue()))
            .andExpect(jsonPath("$.stockItemName").value(DEFAULT_STOCK_ITEM_NAME.toString()))
            .andExpect(jsonPath("$.vendorCode").value(DEFAULT_VENDOR_CODE.toString()))
            .andExpect(jsonPath("$.vendorSKU").value(DEFAULT_VENDOR_SKU.toString()))
            .andExpect(jsonPath("$.barcode").value(DEFAULT_BARCODE.toString()))
            .andExpect(jsonPath("$.barcodeTypeId").value(DEFAULT_BARCODE_TYPE_ID.intValue()))
            .andExpect(jsonPath("$.barcodeTypeName").value(DEFAULT_BARCODE_TYPE_NAME.toString()))
            .andExpect(jsonPath("$.productType").value(DEFAULT_PRODUCT_TYPE.toString()))
            .andExpect(jsonPath("$.productCategoryId").value(DEFAULT_PRODUCT_CATEGORY_ID.intValue()))
            .andExpect(jsonPath("$.productCategoryName").value(DEFAULT_PRODUCT_CATEGORY_NAME.toString()))
            .andExpect(jsonPath("$.productAttributeSetId").value(DEFAULT_PRODUCT_ATTRIBUTE_SET_ID.intValue()))
            .andExpect(jsonPath("$.productAttributeId").value(DEFAULT_PRODUCT_ATTRIBUTE_ID.intValue()))
            .andExpect(jsonPath("$.productAttributeValue").value(DEFAULT_PRODUCT_ATTRIBUTE_VALUE.toString()))
            .andExpect(jsonPath("$.productOptionSetId").value(DEFAULT_PRODUCT_OPTION_SET_ID.intValue()))
            .andExpect(jsonPath("$.productOptionId").value(DEFAULT_PRODUCT_OPTION_ID.intValue()))
            .andExpect(jsonPath("$.productOptionValue").value(DEFAULT_PRODUCT_OPTION_VALUE.toString()))
            .andExpect(jsonPath("$.modelName").value(DEFAULT_MODEL_NAME.toString()))
            .andExpect(jsonPath("$.modelNumber").value(DEFAULT_MODEL_NUMBER.toString()))
            .andExpect(jsonPath("$.materialId").value(DEFAULT_MATERIAL_ID.intValue()))
            .andExpect(jsonPath("$.materialName").value(DEFAULT_MATERIAL_NAME.toString()))
            .andExpect(jsonPath("$.shortDescription").value(DEFAULT_SHORT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.productBrandId").value(DEFAULT_PRODUCT_BRAND_ID.intValue()))
            .andExpect(jsonPath("$.productBrandName").value(DEFAULT_PRODUCT_BRAND_NAME.toString()))
            .andExpect(jsonPath("$.highlights").value(DEFAULT_HIGHLIGHTS.toString()))
            .andExpect(jsonPath("$.searchDetails").value(DEFAULT_SEARCH_DETAILS.toString()))
            .andExpect(jsonPath("$.careInstructions").value(DEFAULT_CARE_INSTRUCTIONS.toString()))
            .andExpect(jsonPath("$.dangerousGoods").value(DEFAULT_DANGEROUS_GOODS.toString()))
            .andExpect(jsonPath("$.videoUrl").value(DEFAULT_VIDEO_URL.toString()))
            .andExpect(jsonPath("$.unitPrice").value(DEFAULT_UNIT_PRICE.doubleValue()))
            .andExpect(jsonPath("$.remommendedRetailPrice").value(DEFAULT_REMOMMENDED_RETAIL_PRICE.doubleValue()))
            .andExpect(jsonPath("$.currencyCode").value(DEFAULT_CURRENCY_CODE.toString()))
            .andExpect(jsonPath("$.quantityOnHand").value(DEFAULT_QUANTITY_ON_HAND))
            .andExpect(jsonPath("$.warrantyPeriod").value(DEFAULT_WARRANTY_PERIOD.toString()))
            .andExpect(jsonPath("$.warrantyPolicy").value(DEFAULT_WARRANTY_POLICY.toString()))
            .andExpect(jsonPath("$.warrantyTypeId").value(DEFAULT_WARRANTY_TYPE_ID.intValue()))
            .andExpect(jsonPath("$.warrantyTypeName").value(DEFAULT_WARRANTY_TYPE_NAME.toString()))
            .andExpect(jsonPath("$.whatInTheBox").value(DEFAULT_WHAT_IN_THE_BOX.toString()))
            .andExpect(jsonPath("$.itemLength").value(DEFAULT_ITEM_LENGTH))
            .andExpect(jsonPath("$.itemWidth").value(DEFAULT_ITEM_WIDTH))
            .andExpect(jsonPath("$.itemHeight").value(DEFAULT_ITEM_HEIGHT))
            .andExpect(jsonPath("$.itemWeight").value(DEFAULT_ITEM_WEIGHT.doubleValue()))
            .andExpect(jsonPath("$.itemPackageLength").value(DEFAULT_ITEM_PACKAGE_LENGTH))
            .andExpect(jsonPath("$.itemPackageWidth").value(DEFAULT_ITEM_PACKAGE_WIDTH))
            .andExpect(jsonPath("$.itemPackageHeight").value(DEFAULT_ITEM_PACKAGE_HEIGHT))
            .andExpect(jsonPath("$.itemPackageWeight").value(DEFAULT_ITEM_PACKAGE_WEIGHT.doubleValue()))
            .andExpect(jsonPath("$.itemLengthUnitMeasureId").value(DEFAULT_ITEM_LENGTH_UNIT_MEASURE_ID.intValue()))
            .andExpect(jsonPath("$.itemLengthUnitMeasureCode").value(DEFAULT_ITEM_LENGTH_UNIT_MEASURE_CODE.toString()))
            .andExpect(jsonPath("$.itemWidthUnitMeasureId").value(DEFAULT_ITEM_WIDTH_UNIT_MEASURE_ID.intValue()))
            .andExpect(jsonPath("$.itemWidthUnitMeasureCode").value(DEFAULT_ITEM_WIDTH_UNIT_MEASURE_CODE.toString()))
            .andExpect(jsonPath("$.itemHeightUnitMeasureId").value(DEFAULT_ITEM_HEIGHT_UNIT_MEASURE_ID.intValue()))
            .andExpect(jsonPath("$.itemHeightUnitMeasureCode").value(DEFAULT_ITEM_HEIGHT_UNIT_MEASURE_CODE.toString()))
            .andExpect(jsonPath("$.itemWeightUnitMeasureId").value(DEFAULT_ITEM_WEIGHT_UNIT_MEASURE_ID.intValue()))
            .andExpect(jsonPath("$.itemWeightUnitMeasureCode").value(DEFAULT_ITEM_WEIGHT_UNIT_MEASURE_CODE.toString()))
            .andExpect(jsonPath("$.itemPackageLengthUnitMeasureId").value(DEFAULT_ITEM_PACKAGE_LENGTH_UNIT_MEASURE_ID.intValue()))
            .andExpect(jsonPath("$.itemPackageLengthUnitMeasureCode").value(DEFAULT_ITEM_PACKAGE_LENGTH_UNIT_MEASURE_CODE.toString()))
            .andExpect(jsonPath("$.itemPackageWidthUnitMeasureId").value(DEFAULT_ITEM_PACKAGE_WIDTH_UNIT_MEASURE_ID.intValue()))
            .andExpect(jsonPath("$.itemPackageWidthUnitMeasureCode").value(DEFAULT_ITEM_PACKAGE_WIDTH_UNIT_MEASURE_CODE.toString()))
            .andExpect(jsonPath("$.itemPackageHeightUnitMeasureId").value(DEFAULT_ITEM_PACKAGE_HEIGHT_UNIT_MEASURE_ID.intValue()))
            .andExpect(jsonPath("$.itemPackageHeightUnitMeasureCode").value(DEFAULT_ITEM_PACKAGE_HEIGHT_UNIT_MEASURE_CODE.toString()))
            .andExpect(jsonPath("$.itemPackageWeightUnitMeasureId").value(DEFAULT_ITEM_PACKAGE_WEIGHT_UNIT_MEASURE_ID.intValue()))
            .andExpect(jsonPath("$.itemPackageWeightUnitMeasureCode").value(DEFAULT_ITEM_PACKAGE_WEIGHT_UNIT_MEASURE_CODE.toString()))
            .andExpect(jsonPath("$.noOfPieces").value(DEFAULT_NO_OF_PIECES))
            .andExpect(jsonPath("$.noOfItems").value(DEFAULT_NO_OF_ITEMS))
            .andExpect(jsonPath("$.manufacture").value(DEFAULT_MANUFACTURE.toString()))
            .andExpect(jsonPath("$.specialFeactures").value(DEFAULT_SPECIAL_FEACTURES.toString()))
            .andExpect(jsonPath("$.productComplianceCertificate").value(DEFAULT_PRODUCT_COMPLIANCE_CERTIFICATE.toString()))
            .andExpect(jsonPath("$.genuineAndLegal").value(DEFAULT_GENUINE_AND_LEGAL.booleanValue()))
            .andExpect(jsonPath("$.countryOfOrigin").value(DEFAULT_COUNTRY_OF_ORIGIN.toString()))
            .andExpect(jsonPath("$.usageAndSideEffects").value(DEFAULT_USAGE_AND_SIDE_EFFECTS.toString()))
            .andExpect(jsonPath("$.safetyWarnning").value(DEFAULT_SAFETY_WARNNING.toString()))
            .andExpect(jsonPath("$.sellStartDate").value(DEFAULT_SELL_START_DATE.toString()))
            .andExpect(jsonPath("$.sellEndDate").value(DEFAULT_SELL_END_DATE.toString()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS));
    }

    @Test
    @Transactional
    public void getNonExistingStockItemTemp() throws Exception {
        // Get the stockItemTemp
        restStockItemTempMockMvc.perform(get("/api/stock-item-temps/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateStockItemTemp() throws Exception {
        // Initialize the database
        stockItemTempRepository.saveAndFlush(stockItemTemp);

        int databaseSizeBeforeUpdate = stockItemTempRepository.findAll().size();

        // Update the stockItemTemp
        StockItemTemp updatedStockItemTemp = stockItemTempRepository.findById(stockItemTemp.getId()).get();
        // Disconnect from session so that the updates on updatedStockItemTemp are not directly saved in db
        em.detach(updatedStockItemTemp);
        updatedStockItemTemp
            .stockItemName(UPDATED_STOCK_ITEM_NAME)
            .vendorCode(UPDATED_VENDOR_CODE)
            .vendorSKU(UPDATED_VENDOR_SKU)
            .barcode(UPDATED_BARCODE)
            .barcodeTypeId(UPDATED_BARCODE_TYPE_ID)
            .barcodeTypeName(UPDATED_BARCODE_TYPE_NAME)
            .productType(UPDATED_PRODUCT_TYPE)
            .productCategoryId(UPDATED_PRODUCT_CATEGORY_ID)
            .productCategoryName(UPDATED_PRODUCT_CATEGORY_NAME)
            .productAttributeSetId(UPDATED_PRODUCT_ATTRIBUTE_SET_ID)
            .productAttributeId(UPDATED_PRODUCT_ATTRIBUTE_ID)
            .productAttributeValue(UPDATED_PRODUCT_ATTRIBUTE_VALUE)
            .productOptionSetId(UPDATED_PRODUCT_OPTION_SET_ID)
            .productOptionId(UPDATED_PRODUCT_OPTION_ID)
            .productOptionValue(UPDATED_PRODUCT_OPTION_VALUE)
            .modelName(UPDATED_MODEL_NAME)
            .modelNumber(UPDATED_MODEL_NUMBER)
            .materialId(UPDATED_MATERIAL_ID)
            .materialName(UPDATED_MATERIAL_NAME)
            .shortDescription(UPDATED_SHORT_DESCRIPTION)
            .description(UPDATED_DESCRIPTION)
            .productBrandId(UPDATED_PRODUCT_BRAND_ID)
            .productBrandName(UPDATED_PRODUCT_BRAND_NAME)
            .highlights(UPDATED_HIGHLIGHTS)
            .searchDetails(UPDATED_SEARCH_DETAILS)
            .careInstructions(UPDATED_CARE_INSTRUCTIONS)
            .dangerousGoods(UPDATED_DANGEROUS_GOODS)
            .videoUrl(UPDATED_VIDEO_URL)
            .unitPrice(UPDATED_UNIT_PRICE)
            .remommendedRetailPrice(UPDATED_REMOMMENDED_RETAIL_PRICE)
            .currencyCode(UPDATED_CURRENCY_CODE)
            .quantityOnHand(UPDATED_QUANTITY_ON_HAND)
            .warrantyPeriod(UPDATED_WARRANTY_PERIOD)
            .warrantyPolicy(UPDATED_WARRANTY_POLICY)
            .warrantyTypeId(UPDATED_WARRANTY_TYPE_ID)
            .warrantyTypeName(UPDATED_WARRANTY_TYPE_NAME)
            .whatInTheBox(UPDATED_WHAT_IN_THE_BOX)
            .itemLength(UPDATED_ITEM_LENGTH)
            .itemWidth(UPDATED_ITEM_WIDTH)
            .itemHeight(UPDATED_ITEM_HEIGHT)
            .itemWeight(UPDATED_ITEM_WEIGHT)
            .itemPackageLength(UPDATED_ITEM_PACKAGE_LENGTH)
            .itemPackageWidth(UPDATED_ITEM_PACKAGE_WIDTH)
            .itemPackageHeight(UPDATED_ITEM_PACKAGE_HEIGHT)
            .itemPackageWeight(UPDATED_ITEM_PACKAGE_WEIGHT)
            .itemLengthUnitMeasureId(UPDATED_ITEM_LENGTH_UNIT_MEASURE_ID)
            .itemLengthUnitMeasureCode(UPDATED_ITEM_LENGTH_UNIT_MEASURE_CODE)
            .itemWidthUnitMeasureId(UPDATED_ITEM_WIDTH_UNIT_MEASURE_ID)
            .itemWidthUnitMeasureCode(UPDATED_ITEM_WIDTH_UNIT_MEASURE_CODE)
            .itemHeightUnitMeasureId(UPDATED_ITEM_HEIGHT_UNIT_MEASURE_ID)
            .itemHeightUnitMeasureCode(UPDATED_ITEM_HEIGHT_UNIT_MEASURE_CODE)
            .itemWeightUnitMeasureId(UPDATED_ITEM_WEIGHT_UNIT_MEASURE_ID)
            .itemWeightUnitMeasureCode(UPDATED_ITEM_WEIGHT_UNIT_MEASURE_CODE)
            .itemPackageLengthUnitMeasureId(UPDATED_ITEM_PACKAGE_LENGTH_UNIT_MEASURE_ID)
            .itemPackageLengthUnitMeasureCode(UPDATED_ITEM_PACKAGE_LENGTH_UNIT_MEASURE_CODE)
            .itemPackageWidthUnitMeasureId(UPDATED_ITEM_PACKAGE_WIDTH_UNIT_MEASURE_ID)
            .itemPackageWidthUnitMeasureCode(UPDATED_ITEM_PACKAGE_WIDTH_UNIT_MEASURE_CODE)
            .itemPackageHeightUnitMeasureId(UPDATED_ITEM_PACKAGE_HEIGHT_UNIT_MEASURE_ID)
            .itemPackageHeightUnitMeasureCode(UPDATED_ITEM_PACKAGE_HEIGHT_UNIT_MEASURE_CODE)
            .itemPackageWeightUnitMeasureId(UPDATED_ITEM_PACKAGE_WEIGHT_UNIT_MEASURE_ID)
            .itemPackageWeightUnitMeasureCode(UPDATED_ITEM_PACKAGE_WEIGHT_UNIT_MEASURE_CODE)
            .noOfPieces(UPDATED_NO_OF_PIECES)
            .noOfItems(UPDATED_NO_OF_ITEMS)
            .manufacture(UPDATED_MANUFACTURE)
            .specialFeactures(UPDATED_SPECIAL_FEACTURES)
            .productComplianceCertificate(UPDATED_PRODUCT_COMPLIANCE_CERTIFICATE)
            .genuineAndLegal(UPDATED_GENUINE_AND_LEGAL)
            .countryOfOrigin(UPDATED_COUNTRY_OF_ORIGIN)
            .usageAndSideEffects(UPDATED_USAGE_AND_SIDE_EFFECTS)
            .safetyWarnning(UPDATED_SAFETY_WARNNING)
            .sellStartDate(UPDATED_SELL_START_DATE)
            .sellEndDate(UPDATED_SELL_END_DATE)
            .status(UPDATED_STATUS);
        StockItemTempDTO stockItemTempDTO = stockItemTempMapper.toDto(updatedStockItemTemp);

        restStockItemTempMockMvc.perform(put("/api/stock-item-temps")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(stockItemTempDTO)))
            .andExpect(status().isOk());

        // Validate the StockItemTemp in the database
        List<StockItemTemp> stockItemTempList = stockItemTempRepository.findAll();
        assertThat(stockItemTempList).hasSize(databaseSizeBeforeUpdate);
        StockItemTemp testStockItemTemp = stockItemTempList.get(stockItemTempList.size() - 1);
        assertThat(testStockItemTemp.getStockItemName()).isEqualTo(UPDATED_STOCK_ITEM_NAME);
        assertThat(testStockItemTemp.getVendorCode()).isEqualTo(UPDATED_VENDOR_CODE);
        assertThat(testStockItemTemp.getVendorSKU()).isEqualTo(UPDATED_VENDOR_SKU);
        assertThat(testStockItemTemp.getBarcode()).isEqualTo(UPDATED_BARCODE);
        assertThat(testStockItemTemp.getBarcodeTypeId()).isEqualTo(UPDATED_BARCODE_TYPE_ID);
        assertThat(testStockItemTemp.getBarcodeTypeName()).isEqualTo(UPDATED_BARCODE_TYPE_NAME);
        assertThat(testStockItemTemp.getProductType()).isEqualTo(UPDATED_PRODUCT_TYPE);
        assertThat(testStockItemTemp.getProductCategoryId()).isEqualTo(UPDATED_PRODUCT_CATEGORY_ID);
        assertThat(testStockItemTemp.getProductCategoryName()).isEqualTo(UPDATED_PRODUCT_CATEGORY_NAME);
        assertThat(testStockItemTemp.getProductAttributeSetId()).isEqualTo(UPDATED_PRODUCT_ATTRIBUTE_SET_ID);
        assertThat(testStockItemTemp.getProductAttributeId()).isEqualTo(UPDATED_PRODUCT_ATTRIBUTE_ID);
        assertThat(testStockItemTemp.getProductAttributeValue()).isEqualTo(UPDATED_PRODUCT_ATTRIBUTE_VALUE);
        assertThat(testStockItemTemp.getProductOptionSetId()).isEqualTo(UPDATED_PRODUCT_OPTION_SET_ID);
        assertThat(testStockItemTemp.getProductOptionId()).isEqualTo(UPDATED_PRODUCT_OPTION_ID);
        assertThat(testStockItemTemp.getProductOptionValue()).isEqualTo(UPDATED_PRODUCT_OPTION_VALUE);
        assertThat(testStockItemTemp.getModelName()).isEqualTo(UPDATED_MODEL_NAME);
        assertThat(testStockItemTemp.getModelNumber()).isEqualTo(UPDATED_MODEL_NUMBER);
        assertThat(testStockItemTemp.getMaterialId()).isEqualTo(UPDATED_MATERIAL_ID);
        assertThat(testStockItemTemp.getMaterialName()).isEqualTo(UPDATED_MATERIAL_NAME);
        assertThat(testStockItemTemp.getShortDescription()).isEqualTo(UPDATED_SHORT_DESCRIPTION);
        assertThat(testStockItemTemp.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testStockItemTemp.getProductBrandId()).isEqualTo(UPDATED_PRODUCT_BRAND_ID);
        assertThat(testStockItemTemp.getProductBrandName()).isEqualTo(UPDATED_PRODUCT_BRAND_NAME);
        assertThat(testStockItemTemp.getHighlights()).isEqualTo(UPDATED_HIGHLIGHTS);
        assertThat(testStockItemTemp.getSearchDetails()).isEqualTo(UPDATED_SEARCH_DETAILS);
        assertThat(testStockItemTemp.getCareInstructions()).isEqualTo(UPDATED_CARE_INSTRUCTIONS);
        assertThat(testStockItemTemp.getDangerousGoods()).isEqualTo(UPDATED_DANGEROUS_GOODS);
        assertThat(testStockItemTemp.getVideoUrl()).isEqualTo(UPDATED_VIDEO_URL);
        assertThat(testStockItemTemp.getUnitPrice()).isEqualTo(UPDATED_UNIT_PRICE);
        assertThat(testStockItemTemp.getRemommendedRetailPrice()).isEqualTo(UPDATED_REMOMMENDED_RETAIL_PRICE);
        assertThat(testStockItemTemp.getCurrencyCode()).isEqualTo(UPDATED_CURRENCY_CODE);
        assertThat(testStockItemTemp.getQuantityOnHand()).isEqualTo(UPDATED_QUANTITY_ON_HAND);
        assertThat(testStockItemTemp.getWarrantyPeriod()).isEqualTo(UPDATED_WARRANTY_PERIOD);
        assertThat(testStockItemTemp.getWarrantyPolicy()).isEqualTo(UPDATED_WARRANTY_POLICY);
        assertThat(testStockItemTemp.getWarrantyTypeId()).isEqualTo(UPDATED_WARRANTY_TYPE_ID);
        assertThat(testStockItemTemp.getWarrantyTypeName()).isEqualTo(UPDATED_WARRANTY_TYPE_NAME);
        assertThat(testStockItemTemp.getWhatInTheBox()).isEqualTo(UPDATED_WHAT_IN_THE_BOX);
        assertThat(testStockItemTemp.getItemLength()).isEqualTo(UPDATED_ITEM_LENGTH);
        assertThat(testStockItemTemp.getItemWidth()).isEqualTo(UPDATED_ITEM_WIDTH);
        assertThat(testStockItemTemp.getItemHeight()).isEqualTo(UPDATED_ITEM_HEIGHT);
        assertThat(testStockItemTemp.getItemWeight()).isEqualTo(UPDATED_ITEM_WEIGHT);
        assertThat(testStockItemTemp.getItemPackageLength()).isEqualTo(UPDATED_ITEM_PACKAGE_LENGTH);
        assertThat(testStockItemTemp.getItemPackageWidth()).isEqualTo(UPDATED_ITEM_PACKAGE_WIDTH);
        assertThat(testStockItemTemp.getItemPackageHeight()).isEqualTo(UPDATED_ITEM_PACKAGE_HEIGHT);
        assertThat(testStockItemTemp.getItemPackageWeight()).isEqualTo(UPDATED_ITEM_PACKAGE_WEIGHT);
        assertThat(testStockItemTemp.getItemLengthUnitMeasureId()).isEqualTo(UPDATED_ITEM_LENGTH_UNIT_MEASURE_ID);
        assertThat(testStockItemTemp.getItemLengthUnitMeasureCode()).isEqualTo(UPDATED_ITEM_LENGTH_UNIT_MEASURE_CODE);
        assertThat(testStockItemTemp.getItemWidthUnitMeasureId()).isEqualTo(UPDATED_ITEM_WIDTH_UNIT_MEASURE_ID);
        assertThat(testStockItemTemp.getItemWidthUnitMeasureCode()).isEqualTo(UPDATED_ITEM_WIDTH_UNIT_MEASURE_CODE);
        assertThat(testStockItemTemp.getItemHeightUnitMeasureId()).isEqualTo(UPDATED_ITEM_HEIGHT_UNIT_MEASURE_ID);
        assertThat(testStockItemTemp.getItemHeightUnitMeasureCode()).isEqualTo(UPDATED_ITEM_HEIGHT_UNIT_MEASURE_CODE);
        assertThat(testStockItemTemp.getItemWeightUnitMeasureId()).isEqualTo(UPDATED_ITEM_WEIGHT_UNIT_MEASURE_ID);
        assertThat(testStockItemTemp.getItemWeightUnitMeasureCode()).isEqualTo(UPDATED_ITEM_WEIGHT_UNIT_MEASURE_CODE);
        assertThat(testStockItemTemp.getItemPackageLengthUnitMeasureId()).isEqualTo(UPDATED_ITEM_PACKAGE_LENGTH_UNIT_MEASURE_ID);
        assertThat(testStockItemTemp.getItemPackageLengthUnitMeasureCode()).isEqualTo(UPDATED_ITEM_PACKAGE_LENGTH_UNIT_MEASURE_CODE);
        assertThat(testStockItemTemp.getItemPackageWidthUnitMeasureId()).isEqualTo(UPDATED_ITEM_PACKAGE_WIDTH_UNIT_MEASURE_ID);
        assertThat(testStockItemTemp.getItemPackageWidthUnitMeasureCode()).isEqualTo(UPDATED_ITEM_PACKAGE_WIDTH_UNIT_MEASURE_CODE);
        assertThat(testStockItemTemp.getItemPackageHeightUnitMeasureId()).isEqualTo(UPDATED_ITEM_PACKAGE_HEIGHT_UNIT_MEASURE_ID);
        assertThat(testStockItemTemp.getItemPackageHeightUnitMeasureCode()).isEqualTo(UPDATED_ITEM_PACKAGE_HEIGHT_UNIT_MEASURE_CODE);
        assertThat(testStockItemTemp.getItemPackageWeightUnitMeasureId()).isEqualTo(UPDATED_ITEM_PACKAGE_WEIGHT_UNIT_MEASURE_ID);
        assertThat(testStockItemTemp.getItemPackageWeightUnitMeasureCode()).isEqualTo(UPDATED_ITEM_PACKAGE_WEIGHT_UNIT_MEASURE_CODE);
        assertThat(testStockItemTemp.getNoOfPieces()).isEqualTo(UPDATED_NO_OF_PIECES);
        assertThat(testStockItemTemp.getNoOfItems()).isEqualTo(UPDATED_NO_OF_ITEMS);
        assertThat(testStockItemTemp.getManufacture()).isEqualTo(UPDATED_MANUFACTURE);
        assertThat(testStockItemTemp.getSpecialFeactures()).isEqualTo(UPDATED_SPECIAL_FEACTURES);
        assertThat(testStockItemTemp.getProductComplianceCertificate()).isEqualTo(UPDATED_PRODUCT_COMPLIANCE_CERTIFICATE);
        assertThat(testStockItemTemp.isGenuineAndLegal()).isEqualTo(UPDATED_GENUINE_AND_LEGAL);
        assertThat(testStockItemTemp.getCountryOfOrigin()).isEqualTo(UPDATED_COUNTRY_OF_ORIGIN);
        assertThat(testStockItemTemp.getUsageAndSideEffects()).isEqualTo(UPDATED_USAGE_AND_SIDE_EFFECTS);
        assertThat(testStockItemTemp.getSafetyWarnning()).isEqualTo(UPDATED_SAFETY_WARNNING);
        assertThat(testStockItemTemp.getSellStartDate()).isEqualTo(UPDATED_SELL_START_DATE);
        assertThat(testStockItemTemp.getSellEndDate()).isEqualTo(UPDATED_SELL_END_DATE);
        assertThat(testStockItemTemp.getStatus()).isEqualTo(UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void updateNonExistingStockItemTemp() throws Exception {
        int databaseSizeBeforeUpdate = stockItemTempRepository.findAll().size();

        // Create the StockItemTemp
        StockItemTempDTO stockItemTempDTO = stockItemTempMapper.toDto(stockItemTemp);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restStockItemTempMockMvc.perform(put("/api/stock-item-temps")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(stockItemTempDTO)))
            .andExpect(status().isBadRequest());

        // Validate the StockItemTemp in the database
        List<StockItemTemp> stockItemTempList = stockItemTempRepository.findAll();
        assertThat(stockItemTempList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteStockItemTemp() throws Exception {
        // Initialize the database
        stockItemTempRepository.saveAndFlush(stockItemTemp);

        int databaseSizeBeforeDelete = stockItemTempRepository.findAll().size();

        // Delete the stockItemTemp
        restStockItemTempMockMvc.perform(delete("/api/stock-item-temps/{id}", stockItemTemp.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<StockItemTemp> stockItemTempList = stockItemTempRepository.findAll();
        assertThat(stockItemTempList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(StockItemTemp.class);
        StockItemTemp stockItemTemp1 = new StockItemTemp();
        stockItemTemp1.setId(1L);
        StockItemTemp stockItemTemp2 = new StockItemTemp();
        stockItemTemp2.setId(stockItemTemp1.getId());
        assertThat(stockItemTemp1).isEqualTo(stockItemTemp2);
        stockItemTemp2.setId(2L);
        assertThat(stockItemTemp1).isNotEqualTo(stockItemTemp2);
        stockItemTemp1.setId(null);
        assertThat(stockItemTemp1).isNotEqualTo(stockItemTemp2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(StockItemTempDTO.class);
        StockItemTempDTO stockItemTempDTO1 = new StockItemTempDTO();
        stockItemTempDTO1.setId(1L);
        StockItemTempDTO stockItemTempDTO2 = new StockItemTempDTO();
        assertThat(stockItemTempDTO1).isNotEqualTo(stockItemTempDTO2);
        stockItemTempDTO2.setId(stockItemTempDTO1.getId());
        assertThat(stockItemTempDTO1).isEqualTo(stockItemTempDTO2);
        stockItemTempDTO2.setId(2L);
        assertThat(stockItemTempDTO1).isNotEqualTo(stockItemTempDTO2);
        stockItemTempDTO1.setId(null);
        assertThat(stockItemTempDTO1).isNotEqualTo(stockItemTempDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(stockItemTempMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(stockItemTempMapper.fromId(null)).isNull();
    }
}
