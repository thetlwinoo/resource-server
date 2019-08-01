package com.resource.server.web.rest;

import com.resource.server.ResourceApp;

import com.resource.server.domain.CurrencyRate;
import com.resource.server.repository.CurrencyRateRepository;
import com.resource.server.service.CurrencyRateService;
import com.resource.server.service.dto.CurrencyRateDTO;
import com.resource.server.service.mapper.CurrencyRateMapper;
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
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;


import static com.resource.server.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the CurrencyRateResource REST controller.
 *
 * @see CurrencyRateResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ResourceApp.class)
public class CurrencyRateResourceIntTest {

    private static final LocalDate DEFAULT_CURRENCY_RATE_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_CURRENCY_RATE_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_FROM_CURRENCY_CODE = "AAAAAAAAAA";
    private static final String UPDATED_FROM_CURRENCY_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_TO_CURRENCY_CODE = "AAAAAAAAAA";
    private static final String UPDATED_TO_CURRENCY_CODE = "BBBBBBBBBB";

    private static final Float DEFAULT_AVERAGE_RATE = 1F;
    private static final Float UPDATED_AVERAGE_RATE = 2F;

    private static final Float DEFAULT_END_OF_DAY_RATE = 1F;
    private static final Float UPDATED_END_OF_DAY_RATE = 2F;

    @Autowired
    private CurrencyRateRepository currencyRateRepository;

    @Autowired
    private CurrencyRateMapper currencyRateMapper;

    @Autowired
    private CurrencyRateService currencyRateService;

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

    private MockMvc restCurrencyRateMockMvc;

    private CurrencyRate currencyRate;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final CurrencyRateResource currencyRateResource = new CurrencyRateResource(currencyRateService);
        this.restCurrencyRateMockMvc = MockMvcBuilders.standaloneSetup(currencyRateResource)
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
    public static CurrencyRate createEntity(EntityManager em) {
        CurrencyRate currencyRate = new CurrencyRate()
            .currencyRateDate(DEFAULT_CURRENCY_RATE_DATE)
            .fromCurrencyCode(DEFAULT_FROM_CURRENCY_CODE)
            .toCurrencyCode(DEFAULT_TO_CURRENCY_CODE)
            .averageRate(DEFAULT_AVERAGE_RATE)
            .endOfDayRate(DEFAULT_END_OF_DAY_RATE);
        return currencyRate;
    }

    @Before
    public void initTest() {
        currencyRate = createEntity(em);
    }

    @Test
    @Transactional
    public void createCurrencyRate() throws Exception {
        int databaseSizeBeforeCreate = currencyRateRepository.findAll().size();

        // Create the CurrencyRate
        CurrencyRateDTO currencyRateDTO = currencyRateMapper.toDto(currencyRate);
        restCurrencyRateMockMvc.perform(post("/api/currency-rates")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(currencyRateDTO)))
            .andExpect(status().isCreated());

        // Validate the CurrencyRate in the database
        List<CurrencyRate> currencyRateList = currencyRateRepository.findAll();
        assertThat(currencyRateList).hasSize(databaseSizeBeforeCreate + 1);
        CurrencyRate testCurrencyRate = currencyRateList.get(currencyRateList.size() - 1);
        assertThat(testCurrencyRate.getCurrencyRateDate()).isEqualTo(DEFAULT_CURRENCY_RATE_DATE);
        assertThat(testCurrencyRate.getFromCurrencyCode()).isEqualTo(DEFAULT_FROM_CURRENCY_CODE);
        assertThat(testCurrencyRate.getToCurrencyCode()).isEqualTo(DEFAULT_TO_CURRENCY_CODE);
        assertThat(testCurrencyRate.getAverageRate()).isEqualTo(DEFAULT_AVERAGE_RATE);
        assertThat(testCurrencyRate.getEndOfDayRate()).isEqualTo(DEFAULT_END_OF_DAY_RATE);
    }

    @Test
    @Transactional
    public void createCurrencyRateWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = currencyRateRepository.findAll().size();

        // Create the CurrencyRate with an existing ID
        currencyRate.setId(1L);
        CurrencyRateDTO currencyRateDTO = currencyRateMapper.toDto(currencyRate);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCurrencyRateMockMvc.perform(post("/api/currency-rates")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(currencyRateDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CurrencyRate in the database
        List<CurrencyRate> currencyRateList = currencyRateRepository.findAll();
        assertThat(currencyRateList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkCurrencyRateDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = currencyRateRepository.findAll().size();
        // set the field null
        currencyRate.setCurrencyRateDate(null);

        // Create the CurrencyRate, which fails.
        CurrencyRateDTO currencyRateDTO = currencyRateMapper.toDto(currencyRate);

        restCurrencyRateMockMvc.perform(post("/api/currency-rates")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(currencyRateDTO)))
            .andExpect(status().isBadRequest());

        List<CurrencyRate> currencyRateList = currencyRateRepository.findAll();
        assertThat(currencyRateList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllCurrencyRates() throws Exception {
        // Initialize the database
        currencyRateRepository.saveAndFlush(currencyRate);

        // Get all the currencyRateList
        restCurrencyRateMockMvc.perform(get("/api/currency-rates?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(currencyRate.getId().intValue())))
            .andExpect(jsonPath("$.[*].currencyRateDate").value(hasItem(DEFAULT_CURRENCY_RATE_DATE.toString())))
            .andExpect(jsonPath("$.[*].fromCurrencyCode").value(hasItem(DEFAULT_FROM_CURRENCY_CODE.toString())))
            .andExpect(jsonPath("$.[*].toCurrencyCode").value(hasItem(DEFAULT_TO_CURRENCY_CODE.toString())))
            .andExpect(jsonPath("$.[*].averageRate").value(hasItem(DEFAULT_AVERAGE_RATE.doubleValue())))
            .andExpect(jsonPath("$.[*].endOfDayRate").value(hasItem(DEFAULT_END_OF_DAY_RATE.doubleValue())));
    }
    
    @Test
    @Transactional
    public void getCurrencyRate() throws Exception {
        // Initialize the database
        currencyRateRepository.saveAndFlush(currencyRate);

        // Get the currencyRate
        restCurrencyRateMockMvc.perform(get("/api/currency-rates/{id}", currencyRate.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(currencyRate.getId().intValue()))
            .andExpect(jsonPath("$.currencyRateDate").value(DEFAULT_CURRENCY_RATE_DATE.toString()))
            .andExpect(jsonPath("$.fromCurrencyCode").value(DEFAULT_FROM_CURRENCY_CODE.toString()))
            .andExpect(jsonPath("$.toCurrencyCode").value(DEFAULT_TO_CURRENCY_CODE.toString()))
            .andExpect(jsonPath("$.averageRate").value(DEFAULT_AVERAGE_RATE.doubleValue()))
            .andExpect(jsonPath("$.endOfDayRate").value(DEFAULT_END_OF_DAY_RATE.doubleValue()));
    }

    @Test
    @Transactional
    public void getNonExistingCurrencyRate() throws Exception {
        // Get the currencyRate
        restCurrencyRateMockMvc.perform(get("/api/currency-rates/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCurrencyRate() throws Exception {
        // Initialize the database
        currencyRateRepository.saveAndFlush(currencyRate);

        int databaseSizeBeforeUpdate = currencyRateRepository.findAll().size();

        // Update the currencyRate
        CurrencyRate updatedCurrencyRate = currencyRateRepository.findById(currencyRate.getId()).get();
        // Disconnect from session so that the updates on updatedCurrencyRate are not directly saved in db
        em.detach(updatedCurrencyRate);
        updatedCurrencyRate
            .currencyRateDate(UPDATED_CURRENCY_RATE_DATE)
            .fromCurrencyCode(UPDATED_FROM_CURRENCY_CODE)
            .toCurrencyCode(UPDATED_TO_CURRENCY_CODE)
            .averageRate(UPDATED_AVERAGE_RATE)
            .endOfDayRate(UPDATED_END_OF_DAY_RATE);
        CurrencyRateDTO currencyRateDTO = currencyRateMapper.toDto(updatedCurrencyRate);

        restCurrencyRateMockMvc.perform(put("/api/currency-rates")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(currencyRateDTO)))
            .andExpect(status().isOk());

        // Validate the CurrencyRate in the database
        List<CurrencyRate> currencyRateList = currencyRateRepository.findAll();
        assertThat(currencyRateList).hasSize(databaseSizeBeforeUpdate);
        CurrencyRate testCurrencyRate = currencyRateList.get(currencyRateList.size() - 1);
        assertThat(testCurrencyRate.getCurrencyRateDate()).isEqualTo(UPDATED_CURRENCY_RATE_DATE);
        assertThat(testCurrencyRate.getFromCurrencyCode()).isEqualTo(UPDATED_FROM_CURRENCY_CODE);
        assertThat(testCurrencyRate.getToCurrencyCode()).isEqualTo(UPDATED_TO_CURRENCY_CODE);
        assertThat(testCurrencyRate.getAverageRate()).isEqualTo(UPDATED_AVERAGE_RATE);
        assertThat(testCurrencyRate.getEndOfDayRate()).isEqualTo(UPDATED_END_OF_DAY_RATE);
    }

    @Test
    @Transactional
    public void updateNonExistingCurrencyRate() throws Exception {
        int databaseSizeBeforeUpdate = currencyRateRepository.findAll().size();

        // Create the CurrencyRate
        CurrencyRateDTO currencyRateDTO = currencyRateMapper.toDto(currencyRate);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCurrencyRateMockMvc.perform(put("/api/currency-rates")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(currencyRateDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CurrencyRate in the database
        List<CurrencyRate> currencyRateList = currencyRateRepository.findAll();
        assertThat(currencyRateList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteCurrencyRate() throws Exception {
        // Initialize the database
        currencyRateRepository.saveAndFlush(currencyRate);

        int databaseSizeBeforeDelete = currencyRateRepository.findAll().size();

        // Delete the currencyRate
        restCurrencyRateMockMvc.perform(delete("/api/currency-rates/{id}", currencyRate.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<CurrencyRate> currencyRateList = currencyRateRepository.findAll();
        assertThat(currencyRateList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CurrencyRate.class);
        CurrencyRate currencyRate1 = new CurrencyRate();
        currencyRate1.setId(1L);
        CurrencyRate currencyRate2 = new CurrencyRate();
        currencyRate2.setId(currencyRate1.getId());
        assertThat(currencyRate1).isEqualTo(currencyRate2);
        currencyRate2.setId(2L);
        assertThat(currencyRate1).isNotEqualTo(currencyRate2);
        currencyRate1.setId(null);
        assertThat(currencyRate1).isNotEqualTo(currencyRate2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CurrencyRateDTO.class);
        CurrencyRateDTO currencyRateDTO1 = new CurrencyRateDTO();
        currencyRateDTO1.setId(1L);
        CurrencyRateDTO currencyRateDTO2 = new CurrencyRateDTO();
        assertThat(currencyRateDTO1).isNotEqualTo(currencyRateDTO2);
        currencyRateDTO2.setId(currencyRateDTO1.getId());
        assertThat(currencyRateDTO1).isEqualTo(currencyRateDTO2);
        currencyRateDTO2.setId(2L);
        assertThat(currencyRateDTO1).isNotEqualTo(currencyRateDTO2);
        currencyRateDTO1.setId(null);
        assertThat(currencyRateDTO1).isNotEqualTo(currencyRateDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(currencyRateMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(currencyRateMapper.fromId(null)).isNull();
    }
}
