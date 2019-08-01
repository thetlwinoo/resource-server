package com.resource.server.web.rest;

import com.resource.server.ResourceApp;

import com.resource.server.domain.Compares;
import com.resource.server.repository.ComparesRepository;
import com.resource.server.service.ComparesService;
import com.resource.server.service.dto.ComparesDTO;
import com.resource.server.service.mapper.ComparesMapper;
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
 * Test class for the ComparesResource REST controller.
 *
 * @see ComparesResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ResourceApp.class)
public class ComparesResourceIntTest {

    @Autowired
    private ComparesRepository comparesRepository;

    @Autowired
    private ComparesMapper comparesMapper;

    @Autowired
    private ComparesService comparesService;

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

    private MockMvc restComparesMockMvc;

    private Compares compares;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ComparesResource comparesResource = new ComparesResource(comparesService);
        this.restComparesMockMvc = MockMvcBuilders.standaloneSetup(comparesResource)
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
    public static Compares createEntity(EntityManager em) {
        Compares compares = new Compares();
        return compares;
    }

    @Before
    public void initTest() {
        compares = createEntity(em);
    }

    @Test
    @Transactional
    public void createCompares() throws Exception {
        int databaseSizeBeforeCreate = comparesRepository.findAll().size();

        // Create the Compares
        ComparesDTO comparesDTO = comparesMapper.toDto(compares);
        restComparesMockMvc.perform(post("/api/compares")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(comparesDTO)))
            .andExpect(status().isCreated());

        // Validate the Compares in the database
        List<Compares> comparesList = comparesRepository.findAll();
        assertThat(comparesList).hasSize(databaseSizeBeforeCreate + 1);
        Compares testCompares = comparesList.get(comparesList.size() - 1);
    }

    @Test
    @Transactional
    public void createComparesWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = comparesRepository.findAll().size();

        // Create the Compares with an existing ID
        compares.setId(1L);
        ComparesDTO comparesDTO = comparesMapper.toDto(compares);

        // An entity with an existing ID cannot be created, so this API call must fail
        restComparesMockMvc.perform(post("/api/compares")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(comparesDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Compares in the database
        List<Compares> comparesList = comparesRepository.findAll();
        assertThat(comparesList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllCompares() throws Exception {
        // Initialize the database
        comparesRepository.saveAndFlush(compares);

        // Get all the comparesList
        restComparesMockMvc.perform(get("/api/compares?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(compares.getId().intValue())));
    }
    
    @Test
    @Transactional
    public void getCompares() throws Exception {
        // Initialize the database
        comparesRepository.saveAndFlush(compares);

        // Get the compares
        restComparesMockMvc.perform(get("/api/compares/{id}", compares.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(compares.getId().intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingCompares() throws Exception {
        // Get the compares
        restComparesMockMvc.perform(get("/api/compares/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCompares() throws Exception {
        // Initialize the database
        comparesRepository.saveAndFlush(compares);

        int databaseSizeBeforeUpdate = comparesRepository.findAll().size();

        // Update the compares
        Compares updatedCompares = comparesRepository.findById(compares.getId()).get();
        // Disconnect from session so that the updates on updatedCompares are not directly saved in db
        em.detach(updatedCompares);
        ComparesDTO comparesDTO = comparesMapper.toDto(updatedCompares);

        restComparesMockMvc.perform(put("/api/compares")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(comparesDTO)))
            .andExpect(status().isOk());

        // Validate the Compares in the database
        List<Compares> comparesList = comparesRepository.findAll();
        assertThat(comparesList).hasSize(databaseSizeBeforeUpdate);
        Compares testCompares = comparesList.get(comparesList.size() - 1);
    }

    @Test
    @Transactional
    public void updateNonExistingCompares() throws Exception {
        int databaseSizeBeforeUpdate = comparesRepository.findAll().size();

        // Create the Compares
        ComparesDTO comparesDTO = comparesMapper.toDto(compares);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restComparesMockMvc.perform(put("/api/compares")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(comparesDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Compares in the database
        List<Compares> comparesList = comparesRepository.findAll();
        assertThat(comparesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteCompares() throws Exception {
        // Initialize the database
        comparesRepository.saveAndFlush(compares);

        int databaseSizeBeforeDelete = comparesRepository.findAll().size();

        // Delete the compares
        restComparesMockMvc.perform(delete("/api/compares/{id}", compares.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Compares> comparesList = comparesRepository.findAll();
        assertThat(comparesList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Compares.class);
        Compares compares1 = new Compares();
        compares1.setId(1L);
        Compares compares2 = new Compares();
        compares2.setId(compares1.getId());
        assertThat(compares1).isEqualTo(compares2);
        compares2.setId(2L);
        assertThat(compares1).isNotEqualTo(compares2);
        compares1.setId(null);
        assertThat(compares1).isNotEqualTo(compares2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ComparesDTO.class);
        ComparesDTO comparesDTO1 = new ComparesDTO();
        comparesDTO1.setId(1L);
        ComparesDTO comparesDTO2 = new ComparesDTO();
        assertThat(comparesDTO1).isNotEqualTo(comparesDTO2);
        comparesDTO2.setId(comparesDTO1.getId());
        assertThat(comparesDTO1).isEqualTo(comparesDTO2);
        comparesDTO2.setId(2L);
        assertThat(comparesDTO1).isNotEqualTo(comparesDTO2);
        comparesDTO1.setId(null);
        assertThat(comparesDTO1).isNotEqualTo(comparesDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(comparesMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(comparesMapper.fromId(null)).isNull();
    }
}
