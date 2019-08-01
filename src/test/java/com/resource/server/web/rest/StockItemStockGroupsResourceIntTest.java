package com.resource.server.web.rest;

import com.resource.server.ResourceApp;

import com.resource.server.domain.StockItemStockGroups;
import com.resource.server.repository.StockItemStockGroupsRepository;
import com.resource.server.service.StockItemStockGroupsService;
import com.resource.server.service.dto.StockItemStockGroupsDTO;
import com.resource.server.service.mapper.StockItemStockGroupsMapper;
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
import java.util.List;


import static com.resource.server.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the StockItemStockGroupsResource REST controller.
 *
 * @see StockItemStockGroupsResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ResourceApp.class)
public class StockItemStockGroupsResourceIntTest {

    @Autowired
    private StockItemStockGroupsRepository stockItemStockGroupsRepository;

    @Autowired
    private StockItemStockGroupsMapper stockItemStockGroupsMapper;

    @Autowired
    private StockItemStockGroupsService stockItemStockGroupsService;

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

    private MockMvc restStockItemStockGroupsMockMvc;

    private StockItemStockGroups stockItemStockGroups;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final StockItemStockGroupsResource stockItemStockGroupsResource = new StockItemStockGroupsResource(stockItemStockGroupsService);
        this.restStockItemStockGroupsMockMvc = MockMvcBuilders.standaloneSetup(stockItemStockGroupsResource)
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
    public static StockItemStockGroups createEntity(EntityManager em) {
        StockItemStockGroups stockItemStockGroups = new StockItemStockGroups();
        return stockItemStockGroups;
    }

    @Before
    public void initTest() {
        stockItemStockGroups = createEntity(em);
    }

    @Test
    @Transactional
    public void createStockItemStockGroups() throws Exception {
        int databaseSizeBeforeCreate = stockItemStockGroupsRepository.findAll().size();

        // Create the StockItemStockGroups
        StockItemStockGroupsDTO stockItemStockGroupsDTO = stockItemStockGroupsMapper.toDto(stockItemStockGroups);
        restStockItemStockGroupsMockMvc.perform(post("/api/stock-item-stock-groups")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(stockItemStockGroupsDTO)))
            .andExpect(status().isCreated());

        // Validate the StockItemStockGroups in the database
        List<StockItemStockGroups> stockItemStockGroupsList = stockItemStockGroupsRepository.findAll();
        assertThat(stockItemStockGroupsList).hasSize(databaseSizeBeforeCreate + 1);
        StockItemStockGroups testStockItemStockGroups = stockItemStockGroupsList.get(stockItemStockGroupsList.size() - 1);
    }

    @Test
    @Transactional
    public void createStockItemStockGroupsWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = stockItemStockGroupsRepository.findAll().size();

        // Create the StockItemStockGroups with an existing ID
        stockItemStockGroups.setId(1L);
        StockItemStockGroupsDTO stockItemStockGroupsDTO = stockItemStockGroupsMapper.toDto(stockItemStockGroups);

        // An entity with an existing ID cannot be created, so this API call must fail
        restStockItemStockGroupsMockMvc.perform(post("/api/stock-item-stock-groups")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(stockItemStockGroupsDTO)))
            .andExpect(status().isBadRequest());

        // Validate the StockItemStockGroups in the database
        List<StockItemStockGroups> stockItemStockGroupsList = stockItemStockGroupsRepository.findAll();
        assertThat(stockItemStockGroupsList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllStockItemStockGroups() throws Exception {
        // Initialize the database
        stockItemStockGroupsRepository.saveAndFlush(stockItemStockGroups);

        // Get all the stockItemStockGroupsList
        restStockItemStockGroupsMockMvc.perform(get("/api/stock-item-stock-groups?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(stockItemStockGroups.getId().intValue())));
    }
    
    @Test
    @Transactional
    public void getStockItemStockGroups() throws Exception {
        // Initialize the database
        stockItemStockGroupsRepository.saveAndFlush(stockItemStockGroups);

        // Get the stockItemStockGroups
        restStockItemStockGroupsMockMvc.perform(get("/api/stock-item-stock-groups/{id}", stockItemStockGroups.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(stockItemStockGroups.getId().intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingStockItemStockGroups() throws Exception {
        // Get the stockItemStockGroups
        restStockItemStockGroupsMockMvc.perform(get("/api/stock-item-stock-groups/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateStockItemStockGroups() throws Exception {
        // Initialize the database
        stockItemStockGroupsRepository.saveAndFlush(stockItemStockGroups);

        int databaseSizeBeforeUpdate = stockItemStockGroupsRepository.findAll().size();

        // Update the stockItemStockGroups
        StockItemStockGroups updatedStockItemStockGroups = stockItemStockGroupsRepository.findById(stockItemStockGroups.getId()).get();
        // Disconnect from session so that the updates on updatedStockItemStockGroups are not directly saved in db
        em.detach(updatedStockItemStockGroups);
        StockItemStockGroupsDTO stockItemStockGroupsDTO = stockItemStockGroupsMapper.toDto(updatedStockItemStockGroups);

        restStockItemStockGroupsMockMvc.perform(put("/api/stock-item-stock-groups")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(stockItemStockGroupsDTO)))
            .andExpect(status().isOk());

        // Validate the StockItemStockGroups in the database
        List<StockItemStockGroups> stockItemStockGroupsList = stockItemStockGroupsRepository.findAll();
        assertThat(stockItemStockGroupsList).hasSize(databaseSizeBeforeUpdate);
        StockItemStockGroups testStockItemStockGroups = stockItemStockGroupsList.get(stockItemStockGroupsList.size() - 1);
    }

    @Test
    @Transactional
    public void updateNonExistingStockItemStockGroups() throws Exception {
        int databaseSizeBeforeUpdate = stockItemStockGroupsRepository.findAll().size();

        // Create the StockItemStockGroups
        StockItemStockGroupsDTO stockItemStockGroupsDTO = stockItemStockGroupsMapper.toDto(stockItemStockGroups);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restStockItemStockGroupsMockMvc.perform(put("/api/stock-item-stock-groups")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(stockItemStockGroupsDTO)))
            .andExpect(status().isBadRequest());

        // Validate the StockItemStockGroups in the database
        List<StockItemStockGroups> stockItemStockGroupsList = stockItemStockGroupsRepository.findAll();
        assertThat(stockItemStockGroupsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteStockItemStockGroups() throws Exception {
        // Initialize the database
        stockItemStockGroupsRepository.saveAndFlush(stockItemStockGroups);

        int databaseSizeBeforeDelete = stockItemStockGroupsRepository.findAll().size();

        // Delete the stockItemStockGroups
        restStockItemStockGroupsMockMvc.perform(delete("/api/stock-item-stock-groups/{id}", stockItemStockGroups.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<StockItemStockGroups> stockItemStockGroupsList = stockItemStockGroupsRepository.findAll();
        assertThat(stockItemStockGroupsList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(StockItemStockGroups.class);
        StockItemStockGroups stockItemStockGroups1 = new StockItemStockGroups();
        stockItemStockGroups1.setId(1L);
        StockItemStockGroups stockItemStockGroups2 = new StockItemStockGroups();
        stockItemStockGroups2.setId(stockItemStockGroups1.getId());
        assertThat(stockItemStockGroups1).isEqualTo(stockItemStockGroups2);
        stockItemStockGroups2.setId(2L);
        assertThat(stockItemStockGroups1).isNotEqualTo(stockItemStockGroups2);
        stockItemStockGroups1.setId(null);
        assertThat(stockItemStockGroups1).isNotEqualTo(stockItemStockGroups2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(StockItemStockGroupsDTO.class);
        StockItemStockGroupsDTO stockItemStockGroupsDTO1 = new StockItemStockGroupsDTO();
        stockItemStockGroupsDTO1.setId(1L);
        StockItemStockGroupsDTO stockItemStockGroupsDTO2 = new StockItemStockGroupsDTO();
        assertThat(stockItemStockGroupsDTO1).isNotEqualTo(stockItemStockGroupsDTO2);
        stockItemStockGroupsDTO2.setId(stockItemStockGroupsDTO1.getId());
        assertThat(stockItemStockGroupsDTO1).isEqualTo(stockItemStockGroupsDTO2);
        stockItemStockGroupsDTO2.setId(2L);
        assertThat(stockItemStockGroupsDTO1).isNotEqualTo(stockItemStockGroupsDTO2);
        stockItemStockGroupsDTO1.setId(null);
        assertThat(stockItemStockGroupsDTO1).isNotEqualTo(stockItemStockGroupsDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(stockItemStockGroupsMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(stockItemStockGroupsMapper.fromId(null)).isNull();
    }
}
