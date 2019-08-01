package com.resource.server.web.rest;

import com.resource.server.ResourceApp;

import com.resource.server.domain.ShoppingCarts;
import com.resource.server.repository.ShoppingCartsRepository;
import com.resource.server.service.ShoppingCartsService;
import com.resource.server.service.dto.ShoppingCartsDTO;
import com.resource.server.service.mapper.ShoppingCartsMapper;
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
 * Test class for the ShoppingCartsResource REST controller.
 *
 * @see ShoppingCartsResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ResourceApp.class)
public class ShoppingCartsResourceIntTest {

    private static final Float DEFAULT_TOTAL_PRICE = 1F;
    private static final Float UPDATED_TOTAL_PRICE = 2F;

    private static final Float DEFAULT_TOTAL_CARGO_PRICE = 1F;
    private static final Float UPDATED_TOTAL_CARGO_PRICE = 2F;

    @Autowired
    private ShoppingCartsRepository shoppingCartsRepository;

    @Autowired
    private ShoppingCartsMapper shoppingCartsMapper;

    @Autowired
    private ShoppingCartsService shoppingCartsService;

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

    private MockMvc restShoppingCartsMockMvc;

    private ShoppingCarts shoppingCarts;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ShoppingCartsResource shoppingCartsResource = new ShoppingCartsResource(shoppingCartsService);
        this.restShoppingCartsMockMvc = MockMvcBuilders.standaloneSetup(shoppingCartsResource)
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
    public static ShoppingCarts createEntity(EntityManager em) {
        ShoppingCarts shoppingCarts = new ShoppingCarts()
            .totalPrice(DEFAULT_TOTAL_PRICE)
            .totalCargoPrice(DEFAULT_TOTAL_CARGO_PRICE);
        return shoppingCarts;
    }

    @Before
    public void initTest() {
        shoppingCarts = createEntity(em);
    }

    @Test
    @Transactional
    public void createShoppingCarts() throws Exception {
        int databaseSizeBeforeCreate = shoppingCartsRepository.findAll().size();

        // Create the ShoppingCarts
        ShoppingCartsDTO shoppingCartsDTO = shoppingCartsMapper.toDto(shoppingCarts);
        restShoppingCartsMockMvc.perform(post("/api/shopping-carts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(shoppingCartsDTO)))
            .andExpect(status().isCreated());

        // Validate the ShoppingCarts in the database
        List<ShoppingCarts> shoppingCartsList = shoppingCartsRepository.findAll();
        assertThat(shoppingCartsList).hasSize(databaseSizeBeforeCreate + 1);
        ShoppingCarts testShoppingCarts = shoppingCartsList.get(shoppingCartsList.size() - 1);
        assertThat(testShoppingCarts.getTotalPrice()).isEqualTo(DEFAULT_TOTAL_PRICE);
        assertThat(testShoppingCarts.getTotalCargoPrice()).isEqualTo(DEFAULT_TOTAL_CARGO_PRICE);
    }

    @Test
    @Transactional
    public void createShoppingCartsWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = shoppingCartsRepository.findAll().size();

        // Create the ShoppingCarts with an existing ID
        shoppingCarts.setId(1L);
        ShoppingCartsDTO shoppingCartsDTO = shoppingCartsMapper.toDto(shoppingCarts);

        // An entity with an existing ID cannot be created, so this API call must fail
        restShoppingCartsMockMvc.perform(post("/api/shopping-carts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(shoppingCartsDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ShoppingCarts in the database
        List<ShoppingCarts> shoppingCartsList = shoppingCartsRepository.findAll();
        assertThat(shoppingCartsList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllShoppingCarts() throws Exception {
        // Initialize the database
        shoppingCartsRepository.saveAndFlush(shoppingCarts);

        // Get all the shoppingCartsList
        restShoppingCartsMockMvc.perform(get("/api/shopping-carts?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(shoppingCarts.getId().intValue())))
            .andExpect(jsonPath("$.[*].totalPrice").value(hasItem(DEFAULT_TOTAL_PRICE.doubleValue())))
            .andExpect(jsonPath("$.[*].totalCargoPrice").value(hasItem(DEFAULT_TOTAL_CARGO_PRICE.doubleValue())));
    }
    
    @Test
    @Transactional
    public void getShoppingCarts() throws Exception {
        // Initialize the database
        shoppingCartsRepository.saveAndFlush(shoppingCarts);

        // Get the shoppingCarts
        restShoppingCartsMockMvc.perform(get("/api/shopping-carts/{id}", shoppingCarts.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(shoppingCarts.getId().intValue()))
            .andExpect(jsonPath("$.totalPrice").value(DEFAULT_TOTAL_PRICE.doubleValue()))
            .andExpect(jsonPath("$.totalCargoPrice").value(DEFAULT_TOTAL_CARGO_PRICE.doubleValue()));
    }

    @Test
    @Transactional
    public void getNonExistingShoppingCarts() throws Exception {
        // Get the shoppingCarts
        restShoppingCartsMockMvc.perform(get("/api/shopping-carts/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateShoppingCarts() throws Exception {
        // Initialize the database
        shoppingCartsRepository.saveAndFlush(shoppingCarts);

        int databaseSizeBeforeUpdate = shoppingCartsRepository.findAll().size();

        // Update the shoppingCarts
        ShoppingCarts updatedShoppingCarts = shoppingCartsRepository.findById(shoppingCarts.getId()).get();
        // Disconnect from session so that the updates on updatedShoppingCarts are not directly saved in db
        em.detach(updatedShoppingCarts);
        updatedShoppingCarts
            .totalPrice(UPDATED_TOTAL_PRICE)
            .totalCargoPrice(UPDATED_TOTAL_CARGO_PRICE);
        ShoppingCartsDTO shoppingCartsDTO = shoppingCartsMapper.toDto(updatedShoppingCarts);

        restShoppingCartsMockMvc.perform(put("/api/shopping-carts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(shoppingCartsDTO)))
            .andExpect(status().isOk());

        // Validate the ShoppingCarts in the database
        List<ShoppingCarts> shoppingCartsList = shoppingCartsRepository.findAll();
        assertThat(shoppingCartsList).hasSize(databaseSizeBeforeUpdate);
        ShoppingCarts testShoppingCarts = shoppingCartsList.get(shoppingCartsList.size() - 1);
        assertThat(testShoppingCarts.getTotalPrice()).isEqualTo(UPDATED_TOTAL_PRICE);
        assertThat(testShoppingCarts.getTotalCargoPrice()).isEqualTo(UPDATED_TOTAL_CARGO_PRICE);
    }

    @Test
    @Transactional
    public void updateNonExistingShoppingCarts() throws Exception {
        int databaseSizeBeforeUpdate = shoppingCartsRepository.findAll().size();

        // Create the ShoppingCarts
        ShoppingCartsDTO shoppingCartsDTO = shoppingCartsMapper.toDto(shoppingCarts);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restShoppingCartsMockMvc.perform(put("/api/shopping-carts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(shoppingCartsDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ShoppingCarts in the database
        List<ShoppingCarts> shoppingCartsList = shoppingCartsRepository.findAll();
        assertThat(shoppingCartsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteShoppingCarts() throws Exception {
        // Initialize the database
        shoppingCartsRepository.saveAndFlush(shoppingCarts);

        int databaseSizeBeforeDelete = shoppingCartsRepository.findAll().size();

        // Delete the shoppingCarts
        restShoppingCartsMockMvc.perform(delete("/api/shopping-carts/{id}", shoppingCarts.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<ShoppingCarts> shoppingCartsList = shoppingCartsRepository.findAll();
        assertThat(shoppingCartsList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ShoppingCarts.class);
        ShoppingCarts shoppingCarts1 = new ShoppingCarts();
        shoppingCarts1.setId(1L);
        ShoppingCarts shoppingCarts2 = new ShoppingCarts();
        shoppingCarts2.setId(shoppingCarts1.getId());
        assertThat(shoppingCarts1).isEqualTo(shoppingCarts2);
        shoppingCarts2.setId(2L);
        assertThat(shoppingCarts1).isNotEqualTo(shoppingCarts2);
        shoppingCarts1.setId(null);
        assertThat(shoppingCarts1).isNotEqualTo(shoppingCarts2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ShoppingCartsDTO.class);
        ShoppingCartsDTO shoppingCartsDTO1 = new ShoppingCartsDTO();
        shoppingCartsDTO1.setId(1L);
        ShoppingCartsDTO shoppingCartsDTO2 = new ShoppingCartsDTO();
        assertThat(shoppingCartsDTO1).isNotEqualTo(shoppingCartsDTO2);
        shoppingCartsDTO2.setId(shoppingCartsDTO1.getId());
        assertThat(shoppingCartsDTO1).isEqualTo(shoppingCartsDTO2);
        shoppingCartsDTO2.setId(2L);
        assertThat(shoppingCartsDTO1).isNotEqualTo(shoppingCartsDTO2);
        shoppingCartsDTO1.setId(null);
        assertThat(shoppingCartsDTO1).isNotEqualTo(shoppingCartsDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(shoppingCartsMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(shoppingCartsMapper.fromId(null)).isNull();
    }
}
