package com.resource.server.web.rest;

import com.resource.server.ResourceApp;

import com.resource.server.domain.SpecialDeals;
import com.resource.server.repository.SpecialDealsRepository;
import com.resource.server.service.SpecialDealsService;
import com.resource.server.service.dto.SpecialDealsDTO;
import com.resource.server.service.mapper.SpecialDealsMapper;
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
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;


import static com.resource.server.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the SpecialDealsResource REST controller.
 *
 * @see SpecialDealsResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ResourceApp.class)
public class SpecialDealsResourceIntTest {

    private static final String DEFAULT_DEAL_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DEAL_DESCRIPTION = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_START_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_START_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_END_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_END_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final BigDecimal DEFAULT_DISCOUNT_AMOUNT = new BigDecimal(1);
    private static final BigDecimal UPDATED_DISCOUNT_AMOUNT = new BigDecimal(2);

    private static final Float DEFAULT_DISCOUNT_PERCENTAGE = 1F;
    private static final Float UPDATED_DISCOUNT_PERCENTAGE = 2F;

    private static final String DEFAULT_DISCOUNT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_DISCOUNT_CODE = "BBBBBBBBBB";

    private static final BigDecimal DEFAULT_UNIT_PRICE = new BigDecimal(1);
    private static final BigDecimal UPDATED_UNIT_PRICE = new BigDecimal(2);

    @Autowired
    private SpecialDealsRepository specialDealsRepository;

    @Autowired
    private SpecialDealsMapper specialDealsMapper;

    @Autowired
    private SpecialDealsService specialDealsService;

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

    private MockMvc restSpecialDealsMockMvc;

    private SpecialDeals specialDeals;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final SpecialDealsResource specialDealsResource = new SpecialDealsResource(specialDealsService);
        this.restSpecialDealsMockMvc = MockMvcBuilders.standaloneSetup(specialDealsResource)
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
    public static SpecialDeals createEntity(EntityManager em) {
        SpecialDeals specialDeals = new SpecialDeals()
            .dealDescription(DEFAULT_DEAL_DESCRIPTION)
            .startDate(DEFAULT_START_DATE)
            .endDate(DEFAULT_END_DATE)
            .discountAmount(DEFAULT_DISCOUNT_AMOUNT)
            .discountPercentage(DEFAULT_DISCOUNT_PERCENTAGE)
            .discountCode(DEFAULT_DISCOUNT_CODE)
            .unitPrice(DEFAULT_UNIT_PRICE);
        return specialDeals;
    }

    @Before
    public void initTest() {
        specialDeals = createEntity(em);
    }

    @Test
    @Transactional
    public void createSpecialDeals() throws Exception {
        int databaseSizeBeforeCreate = specialDealsRepository.findAll().size();

        // Create the SpecialDeals
        SpecialDealsDTO specialDealsDTO = specialDealsMapper.toDto(specialDeals);
        restSpecialDealsMockMvc.perform(post("/api/special-deals")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(specialDealsDTO)))
            .andExpect(status().isCreated());

        // Validate the SpecialDeals in the database
        List<SpecialDeals> specialDealsList = specialDealsRepository.findAll();
        assertThat(specialDealsList).hasSize(databaseSizeBeforeCreate + 1);
        SpecialDeals testSpecialDeals = specialDealsList.get(specialDealsList.size() - 1);
        assertThat(testSpecialDeals.getDealDescription()).isEqualTo(DEFAULT_DEAL_DESCRIPTION);
        assertThat(testSpecialDeals.getStartDate()).isEqualTo(DEFAULT_START_DATE);
        assertThat(testSpecialDeals.getEndDate()).isEqualTo(DEFAULT_END_DATE);
        assertThat(testSpecialDeals.getDiscountAmount()).isEqualTo(DEFAULT_DISCOUNT_AMOUNT);
        assertThat(testSpecialDeals.getDiscountPercentage()).isEqualTo(DEFAULT_DISCOUNT_PERCENTAGE);
        assertThat(testSpecialDeals.getDiscountCode()).isEqualTo(DEFAULT_DISCOUNT_CODE);
        assertThat(testSpecialDeals.getUnitPrice()).isEqualTo(DEFAULT_UNIT_PRICE);
    }

    @Test
    @Transactional
    public void createSpecialDealsWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = specialDealsRepository.findAll().size();

        // Create the SpecialDeals with an existing ID
        specialDeals.setId(1L);
        SpecialDealsDTO specialDealsDTO = specialDealsMapper.toDto(specialDeals);

        // An entity with an existing ID cannot be created, so this API call must fail
        restSpecialDealsMockMvc.perform(post("/api/special-deals")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(specialDealsDTO)))
            .andExpect(status().isBadRequest());

        // Validate the SpecialDeals in the database
        List<SpecialDeals> specialDealsList = specialDealsRepository.findAll();
        assertThat(specialDealsList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkDealDescriptionIsRequired() throws Exception {
        int databaseSizeBeforeTest = specialDealsRepository.findAll().size();
        // set the field null
        specialDeals.setDealDescription(null);

        // Create the SpecialDeals, which fails.
        SpecialDealsDTO specialDealsDTO = specialDealsMapper.toDto(specialDeals);

        restSpecialDealsMockMvc.perform(post("/api/special-deals")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(specialDealsDTO)))
            .andExpect(status().isBadRequest());

        List<SpecialDeals> specialDealsList = specialDealsRepository.findAll();
        assertThat(specialDealsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkStartDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = specialDealsRepository.findAll().size();
        // set the field null
        specialDeals.setStartDate(null);

        // Create the SpecialDeals, which fails.
        SpecialDealsDTO specialDealsDTO = specialDealsMapper.toDto(specialDeals);

        restSpecialDealsMockMvc.perform(post("/api/special-deals")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(specialDealsDTO)))
            .andExpect(status().isBadRequest());

        List<SpecialDeals> specialDealsList = specialDealsRepository.findAll();
        assertThat(specialDealsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkEndDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = specialDealsRepository.findAll().size();
        // set the field null
        specialDeals.setEndDate(null);

        // Create the SpecialDeals, which fails.
        SpecialDealsDTO specialDealsDTO = specialDealsMapper.toDto(specialDeals);

        restSpecialDealsMockMvc.perform(post("/api/special-deals")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(specialDealsDTO)))
            .andExpect(status().isBadRequest());

        List<SpecialDeals> specialDealsList = specialDealsRepository.findAll();
        assertThat(specialDealsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllSpecialDeals() throws Exception {
        // Initialize the database
        specialDealsRepository.saveAndFlush(specialDeals);

        // Get all the specialDealsList
        restSpecialDealsMockMvc.perform(get("/api/special-deals?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(specialDeals.getId().intValue())))
            .andExpect(jsonPath("$.[*].dealDescription").value(hasItem(DEFAULT_DEAL_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].startDate").value(hasItem(DEFAULT_START_DATE.toString())))
            .andExpect(jsonPath("$.[*].endDate").value(hasItem(DEFAULT_END_DATE.toString())))
            .andExpect(jsonPath("$.[*].discountAmount").value(hasItem(DEFAULT_DISCOUNT_AMOUNT.intValue())))
            .andExpect(jsonPath("$.[*].discountPercentage").value(hasItem(DEFAULT_DISCOUNT_PERCENTAGE.doubleValue())))
            .andExpect(jsonPath("$.[*].discountCode").value(hasItem(DEFAULT_DISCOUNT_CODE.toString())))
            .andExpect(jsonPath("$.[*].unitPrice").value(hasItem(DEFAULT_UNIT_PRICE.intValue())));
    }
    
    @Test
    @Transactional
    public void getSpecialDeals() throws Exception {
        // Initialize the database
        specialDealsRepository.saveAndFlush(specialDeals);

        // Get the specialDeals
        restSpecialDealsMockMvc.perform(get("/api/special-deals/{id}", specialDeals.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(specialDeals.getId().intValue()))
            .andExpect(jsonPath("$.dealDescription").value(DEFAULT_DEAL_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.startDate").value(DEFAULT_START_DATE.toString()))
            .andExpect(jsonPath("$.endDate").value(DEFAULT_END_DATE.toString()))
            .andExpect(jsonPath("$.discountAmount").value(DEFAULT_DISCOUNT_AMOUNT.intValue()))
            .andExpect(jsonPath("$.discountPercentage").value(DEFAULT_DISCOUNT_PERCENTAGE.doubleValue()))
            .andExpect(jsonPath("$.discountCode").value(DEFAULT_DISCOUNT_CODE.toString()))
            .andExpect(jsonPath("$.unitPrice").value(DEFAULT_UNIT_PRICE.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingSpecialDeals() throws Exception {
        // Get the specialDeals
        restSpecialDealsMockMvc.perform(get("/api/special-deals/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSpecialDeals() throws Exception {
        // Initialize the database
        specialDealsRepository.saveAndFlush(specialDeals);

        int databaseSizeBeforeUpdate = specialDealsRepository.findAll().size();

        // Update the specialDeals
        SpecialDeals updatedSpecialDeals = specialDealsRepository.findById(specialDeals.getId()).get();
        // Disconnect from session so that the updates on updatedSpecialDeals are not directly saved in db
        em.detach(updatedSpecialDeals);
        updatedSpecialDeals
            .dealDescription(UPDATED_DEAL_DESCRIPTION)
            .startDate(UPDATED_START_DATE)
            .endDate(UPDATED_END_DATE)
            .discountAmount(UPDATED_DISCOUNT_AMOUNT)
            .discountPercentage(UPDATED_DISCOUNT_PERCENTAGE)
            .discountCode(UPDATED_DISCOUNT_CODE)
            .unitPrice(UPDATED_UNIT_PRICE);
        SpecialDealsDTO specialDealsDTO = specialDealsMapper.toDto(updatedSpecialDeals);

        restSpecialDealsMockMvc.perform(put("/api/special-deals")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(specialDealsDTO)))
            .andExpect(status().isOk());

        // Validate the SpecialDeals in the database
        List<SpecialDeals> specialDealsList = specialDealsRepository.findAll();
        assertThat(specialDealsList).hasSize(databaseSizeBeforeUpdate);
        SpecialDeals testSpecialDeals = specialDealsList.get(specialDealsList.size() - 1);
        assertThat(testSpecialDeals.getDealDescription()).isEqualTo(UPDATED_DEAL_DESCRIPTION);
        assertThat(testSpecialDeals.getStartDate()).isEqualTo(UPDATED_START_DATE);
        assertThat(testSpecialDeals.getEndDate()).isEqualTo(UPDATED_END_DATE);
        assertThat(testSpecialDeals.getDiscountAmount()).isEqualTo(UPDATED_DISCOUNT_AMOUNT);
        assertThat(testSpecialDeals.getDiscountPercentage()).isEqualTo(UPDATED_DISCOUNT_PERCENTAGE);
        assertThat(testSpecialDeals.getDiscountCode()).isEqualTo(UPDATED_DISCOUNT_CODE);
        assertThat(testSpecialDeals.getUnitPrice()).isEqualTo(UPDATED_UNIT_PRICE);
    }

    @Test
    @Transactional
    public void updateNonExistingSpecialDeals() throws Exception {
        int databaseSizeBeforeUpdate = specialDealsRepository.findAll().size();

        // Create the SpecialDeals
        SpecialDealsDTO specialDealsDTO = specialDealsMapper.toDto(specialDeals);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSpecialDealsMockMvc.perform(put("/api/special-deals")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(specialDealsDTO)))
            .andExpect(status().isBadRequest());

        // Validate the SpecialDeals in the database
        List<SpecialDeals> specialDealsList = specialDealsRepository.findAll();
        assertThat(specialDealsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteSpecialDeals() throws Exception {
        // Initialize the database
        specialDealsRepository.saveAndFlush(specialDeals);

        int databaseSizeBeforeDelete = specialDealsRepository.findAll().size();

        // Delete the specialDeals
        restSpecialDealsMockMvc.perform(delete("/api/special-deals/{id}", specialDeals.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<SpecialDeals> specialDealsList = specialDealsRepository.findAll();
        assertThat(specialDealsList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(SpecialDeals.class);
        SpecialDeals specialDeals1 = new SpecialDeals();
        specialDeals1.setId(1L);
        SpecialDeals specialDeals2 = new SpecialDeals();
        specialDeals2.setId(specialDeals1.getId());
        assertThat(specialDeals1).isEqualTo(specialDeals2);
        specialDeals2.setId(2L);
        assertThat(specialDeals1).isNotEqualTo(specialDeals2);
        specialDeals1.setId(null);
        assertThat(specialDeals1).isNotEqualTo(specialDeals2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(SpecialDealsDTO.class);
        SpecialDealsDTO specialDealsDTO1 = new SpecialDealsDTO();
        specialDealsDTO1.setId(1L);
        SpecialDealsDTO specialDealsDTO2 = new SpecialDealsDTO();
        assertThat(specialDealsDTO1).isNotEqualTo(specialDealsDTO2);
        specialDealsDTO2.setId(specialDealsDTO1.getId());
        assertThat(specialDealsDTO1).isEqualTo(specialDealsDTO2);
        specialDealsDTO2.setId(2L);
        assertThat(specialDealsDTO1).isNotEqualTo(specialDealsDTO2);
        specialDealsDTO1.setId(null);
        assertThat(specialDealsDTO1).isNotEqualTo(specialDealsDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(specialDealsMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(specialDealsMapper.fromId(null)).isNull();
    }
}
