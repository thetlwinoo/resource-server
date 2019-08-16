package com.resource.server.web.rest;

import com.resource.server.ResourceApp;

import com.resource.server.domain.StockGroups;
import com.resource.server.repository.StockGroupsRepository;
import com.resource.server.service.StockGroupsService;
import com.resource.server.service.dto.StockGroupsDTO;
import com.resource.server.service.mapper.StockGroupsMapper;
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
 * Test class for the StockGroupsResource REST controller.
 *
 * @see StockGroupsResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ResourceApp.class)
public class StockGroupsResourceIntTest {

    private static final String DEFAULT_STOCK_GROUP_NAME = "AAAAAAAAAA";
    private static final String UPDATED_STOCK_GROUP_NAME = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_VALID_FROM = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_VALID_FROM = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_VALID_TO = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_VALID_TO = LocalDate.now(ZoneId.systemDefault());

    @Autowired
    private StockGroupsRepository stockGroupsRepository;

    @Autowired
    private StockGroupsMapper stockGroupsMapper;

    @Autowired
    private StockGroupsService stockGroupsService;

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

    private MockMvc restStockGroupsMockMvc;

    private StockGroups stockGroups;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final StockGroupsResource stockGroupsResource = new StockGroupsResource(stockGroupsService);
        this.restStockGroupsMockMvc = MockMvcBuilders.standaloneSetup(stockGroupsResource)
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
    public static StockGroups createEntity(EntityManager em) {
        StockGroups stockGroups = new StockGroups()
            .stockGroupName(DEFAULT_STOCK_GROUP_NAME)
            .validFrom(DEFAULT_VALID_FROM)
            .validTo(DEFAULT_VALID_TO);
        return stockGroups;
    }

    @Before
    public void initTest() {
        stockGroups = createEntity(em);
    }

    @Test
    @Transactional
    public void createStockGroups() throws Exception {
        int databaseSizeBeforeCreate = stockGroupsRepository.findAll().size();

        // Create the StockGroups
        StockGroupsDTO stockGroupsDTO = stockGroupsMapper.toDto(stockGroups);
        restStockGroupsMockMvc.perform(post("/api/stock-groups")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(stockGroupsDTO)))
            .andExpect(status().isCreated());

        // Validate the StockGroups in the database
        List<StockGroups> stockGroupsList = stockGroupsRepository.findAll();
        assertThat(stockGroupsList).hasSize(databaseSizeBeforeCreate + 1);
        StockGroups testStockGroups = stockGroupsList.get(stockGroupsList.size() - 1);
        assertThat(testStockGroups.getStockGroupName()).isEqualTo(DEFAULT_STOCK_GROUP_NAME);
        assertThat(testStockGroups.getValidFrom()).isEqualTo(DEFAULT_VALID_FROM);
        assertThat(testStockGroups.getValidTo()).isEqualTo(DEFAULT_VALID_TO);
    }

    @Test
    @Transactional
    public void createStockGroupsWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = stockGroupsRepository.findAll().size();

        // Create the StockGroups with an existing ID
        stockGroups.setId(1L);
        StockGroupsDTO stockGroupsDTO = stockGroupsMapper.toDto(stockGroups);

        // An entity with an existing ID cannot be created, so this API call must fail
        restStockGroupsMockMvc.perform(post("/api/stock-groups")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(stockGroupsDTO)))
            .andExpect(status().isBadRequest());

        // Validate the StockGroups in the database
        List<StockGroups> stockGroupsList = stockGroupsRepository.findAll();
        assertThat(stockGroupsList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkStockGroupNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = stockGroupsRepository.findAll().size();
        // set the field null
        stockGroups.setStockGroupName(null);

        // Create the StockGroups, which fails.
        StockGroupsDTO stockGroupsDTO = stockGroupsMapper.toDto(stockGroups);

        restStockGroupsMockMvc.perform(post("/api/stock-groups")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(stockGroupsDTO)))
            .andExpect(status().isBadRequest());

        List<StockGroups> stockGroupsList = stockGroupsRepository.findAll();
        assertThat(stockGroupsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkValidFromIsRequired() throws Exception {
        int databaseSizeBeforeTest = stockGroupsRepository.findAll().size();
        // set the field null
        stockGroups.setValidFrom(null);

        // Create the StockGroups, which fails.
        StockGroupsDTO stockGroupsDTO = stockGroupsMapper.toDto(stockGroups);

        restStockGroupsMockMvc.perform(post("/api/stock-groups")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(stockGroupsDTO)))
            .andExpect(status().isBadRequest());

        List<StockGroups> stockGroupsList = stockGroupsRepository.findAll();
        assertThat(stockGroupsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkValidToIsRequired() throws Exception {
        int databaseSizeBeforeTest = stockGroupsRepository.findAll().size();
        // set the field null
        stockGroups.setValidTo(null);

        // Create the StockGroups, which fails.
        StockGroupsDTO stockGroupsDTO = stockGroupsMapper.toDto(stockGroups);

        restStockGroupsMockMvc.perform(post("/api/stock-groups")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(stockGroupsDTO)))
            .andExpect(status().isBadRequest());

        List<StockGroups> stockGroupsList = stockGroupsRepository.findAll();
        assertThat(stockGroupsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllStockGroups() throws Exception {
        // Initialize the database
        stockGroupsRepository.saveAndFlush(stockGroups);

        // Get all the stockGroupsList
        restStockGroupsMockMvc.perform(get("/api/stock-groups?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(stockGroups.getId().intValue())))
            .andExpect(jsonPath("$.[*].stockGroupName").value(hasItem(DEFAULT_STOCK_GROUP_NAME)))
            .andExpect(jsonPath("$.[*].validFrom").value(hasItem(DEFAULT_VALID_FROM.toString())))
            .andExpect(jsonPath("$.[*].validTo").value(hasItem(DEFAULT_VALID_TO.toString())));
    }

    @Test
    @Transactional
    public void getStockGroups() throws Exception {
        // Initialize the database
        stockGroupsRepository.saveAndFlush(stockGroups);

        // Get the stockGroups
        restStockGroupsMockMvc.perform(get("/api/stock-groups/{id}", stockGroups.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(stockGroups.getId().intValue()))
            .andExpect(jsonPath("$.stockGroupName").value(DEFAULT_STOCK_GROUP_NAME))
            .andExpect(jsonPath("$.validFrom").value(DEFAULT_VALID_FROM.toString()))
            .andExpect(jsonPath("$.validTo").value(DEFAULT_VALID_TO.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingStockGroups() throws Exception {
        // Get the stockGroups
        restStockGroupsMockMvc.perform(get("/api/stock-groups/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateStockGroups() throws Exception {
        // Initialize the database
        stockGroupsRepository.saveAndFlush(stockGroups);

        int databaseSizeBeforeUpdate = stockGroupsRepository.findAll().size();

        // Update the stockGroups
        StockGroups updatedStockGroups = stockGroupsRepository.findById(stockGroups.getId()).get();
        // Disconnect from session so that the updates on updatedStockGroups are not directly saved in db
        em.detach(updatedStockGroups);
        updatedStockGroups
            .stockGroupName(UPDATED_STOCK_GROUP_NAME)
            .validFrom(UPDATED_VALID_FROM)
            .validTo(UPDATED_VALID_TO);
        StockGroupsDTO stockGroupsDTO = stockGroupsMapper.toDto(updatedStockGroups);

        restStockGroupsMockMvc.perform(put("/api/stock-groups")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(stockGroupsDTO)))
            .andExpect(status().isOk());

        // Validate the StockGroups in the database
        List<StockGroups> stockGroupsList = stockGroupsRepository.findAll();
        assertThat(stockGroupsList).hasSize(databaseSizeBeforeUpdate);
        StockGroups testStockGroups = stockGroupsList.get(stockGroupsList.size() - 1);
        assertThat(testStockGroups.getStockGroupName()).isEqualTo(UPDATED_STOCK_GROUP_NAME);
        assertThat(testStockGroups.getValidFrom()).isEqualTo(UPDATED_VALID_FROM);
        assertThat(testStockGroups.getValidTo()).isEqualTo(UPDATED_VALID_TO);
    }

    @Test
    @Transactional
    public void updateNonExistingStockGroups() throws Exception {
        int databaseSizeBeforeUpdate = stockGroupsRepository.findAll().size();

        // Create the StockGroups
        StockGroupsDTO stockGroupsDTO = stockGroupsMapper.toDto(stockGroups);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restStockGroupsMockMvc.perform(put("/api/stock-groups")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(stockGroupsDTO)))
            .andExpect(status().isBadRequest());

        // Validate the StockGroups in the database
        List<StockGroups> stockGroupsList = stockGroupsRepository.findAll();
        assertThat(stockGroupsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteStockGroups() throws Exception {
        // Initialize the database
        stockGroupsRepository.saveAndFlush(stockGroups);

        int databaseSizeBeforeDelete = stockGroupsRepository.findAll().size();

        // Delete the stockGroups
        restStockGroupsMockMvc.perform(delete("/api/stock-groups/{id}", stockGroups.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<StockGroups> stockGroupsList = stockGroupsRepository.findAll();
        assertThat(stockGroupsList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(StockGroups.class);
        StockGroups stockGroups1 = new StockGroups();
        stockGroups1.setId(1L);
        StockGroups stockGroups2 = new StockGroups();
        stockGroups2.setId(stockGroups1.getId());
        assertThat(stockGroups1).isEqualTo(stockGroups2);
        stockGroups2.setId(2L);
        assertThat(stockGroups1).isNotEqualTo(stockGroups2);
        stockGroups1.setId(null);
        assertThat(stockGroups1).isNotEqualTo(stockGroups2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(StockGroupsDTO.class);
        StockGroupsDTO stockGroupsDTO1 = new StockGroupsDTO();
        stockGroupsDTO1.setId(1L);
        StockGroupsDTO stockGroupsDTO2 = new StockGroupsDTO();
        assertThat(stockGroupsDTO1).isNotEqualTo(stockGroupsDTO2);
        stockGroupsDTO2.setId(stockGroupsDTO1.getId());
        assertThat(stockGroupsDTO1).isEqualTo(stockGroupsDTO2);
        stockGroupsDTO2.setId(2L);
        assertThat(stockGroupsDTO1).isNotEqualTo(stockGroupsDTO2);
        stockGroupsDTO1.setId(null);
        assertThat(stockGroupsDTO1).isNotEqualTo(stockGroupsDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(stockGroupsMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(stockGroupsMapper.fromId(null)).isNull();
    }
}
