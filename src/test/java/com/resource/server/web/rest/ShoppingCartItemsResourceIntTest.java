package com.resource.server.web.rest;

import com.resource.server.ResourceApp;

import com.resource.server.domain.ShoppingCartItems;
import com.resource.server.repository.ShoppingCartItemsRepository;
import com.resource.server.service.ShoppingCartItemsService;
import com.resource.server.service.dto.ShoppingCartItemsDTO;
import com.resource.server.service.mapper.ShoppingCartItemsMapper;
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
 * Test class for the ShoppingCartItemsResource REST controller.
 *
 * @see ShoppingCartItemsResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ResourceApp.class)
public class ShoppingCartItemsResourceIntTest {

    private static final Integer DEFAULT_QUANTITY = 1;
    private static final Integer UPDATED_QUANTITY = 2;

    @Autowired
    private ShoppingCartItemsRepository shoppingCartItemsRepository;

    @Autowired
    private ShoppingCartItemsMapper shoppingCartItemsMapper;

    @Autowired
    private ShoppingCartItemsService shoppingCartItemsService;

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

    private MockMvc restShoppingCartItemsMockMvc;

    private ShoppingCartItems shoppingCartItems;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ShoppingCartItemsResource shoppingCartItemsResource = new ShoppingCartItemsResource(shoppingCartItemsService);
        this.restShoppingCartItemsMockMvc = MockMvcBuilders.standaloneSetup(shoppingCartItemsResource)
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
    public static ShoppingCartItems createEntity(EntityManager em) {
        ShoppingCartItems shoppingCartItems = new ShoppingCartItems()
            .quantity(DEFAULT_QUANTITY);
        return shoppingCartItems;
    }

    @Before
    public void initTest() {
        shoppingCartItems = createEntity(em);
    }

    @Test
    @Transactional
    public void createShoppingCartItems() throws Exception {
        int databaseSizeBeforeCreate = shoppingCartItemsRepository.findAll().size();

        // Create the ShoppingCartItems
        ShoppingCartItemsDTO shoppingCartItemsDTO = shoppingCartItemsMapper.toDto(shoppingCartItems);
        restShoppingCartItemsMockMvc.perform(post("/api/shopping-cart-items")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(shoppingCartItemsDTO)))
            .andExpect(status().isCreated());

        // Validate the ShoppingCartItems in the database
        List<ShoppingCartItems> shoppingCartItemsList = shoppingCartItemsRepository.findAll();
        assertThat(shoppingCartItemsList).hasSize(databaseSizeBeforeCreate + 1);
        ShoppingCartItems testShoppingCartItems = shoppingCartItemsList.get(shoppingCartItemsList.size() - 1);
        assertThat(testShoppingCartItems.getQuantity()).isEqualTo(DEFAULT_QUANTITY);
    }

    @Test
    @Transactional
    public void createShoppingCartItemsWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = shoppingCartItemsRepository.findAll().size();

        // Create the ShoppingCartItems with an existing ID
        shoppingCartItems.setId(1L);
        ShoppingCartItemsDTO shoppingCartItemsDTO = shoppingCartItemsMapper.toDto(shoppingCartItems);

        // An entity with an existing ID cannot be created, so this API call must fail
        restShoppingCartItemsMockMvc.perform(post("/api/shopping-cart-items")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(shoppingCartItemsDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ShoppingCartItems in the database
        List<ShoppingCartItems> shoppingCartItemsList = shoppingCartItemsRepository.findAll();
        assertThat(shoppingCartItemsList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllShoppingCartItems() throws Exception {
        // Initialize the database
        shoppingCartItemsRepository.saveAndFlush(shoppingCartItems);

        // Get all the shoppingCartItemsList
        restShoppingCartItemsMockMvc.perform(get("/api/shopping-cart-items?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(shoppingCartItems.getId().intValue())))
            .andExpect(jsonPath("$.[*].quantity").value(hasItem(DEFAULT_QUANTITY)));
    }
    
    @Test
    @Transactional
    public void getShoppingCartItems() throws Exception {
        // Initialize the database
        shoppingCartItemsRepository.saveAndFlush(shoppingCartItems);

        // Get the shoppingCartItems
        restShoppingCartItemsMockMvc.perform(get("/api/shopping-cart-items/{id}", shoppingCartItems.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(shoppingCartItems.getId().intValue()))
            .andExpect(jsonPath("$.quantity").value(DEFAULT_QUANTITY));
    }

    @Test
    @Transactional
    public void getNonExistingShoppingCartItems() throws Exception {
        // Get the shoppingCartItems
        restShoppingCartItemsMockMvc.perform(get("/api/shopping-cart-items/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateShoppingCartItems() throws Exception {
        // Initialize the database
        shoppingCartItemsRepository.saveAndFlush(shoppingCartItems);

        int databaseSizeBeforeUpdate = shoppingCartItemsRepository.findAll().size();

        // Update the shoppingCartItems
        ShoppingCartItems updatedShoppingCartItems = shoppingCartItemsRepository.findById(shoppingCartItems.getId()).get();
        // Disconnect from session so that the updates on updatedShoppingCartItems are not directly saved in db
        em.detach(updatedShoppingCartItems);
        updatedShoppingCartItems
            .quantity(UPDATED_QUANTITY);
        ShoppingCartItemsDTO shoppingCartItemsDTO = shoppingCartItemsMapper.toDto(updatedShoppingCartItems);

        restShoppingCartItemsMockMvc.perform(put("/api/shopping-cart-items")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(shoppingCartItemsDTO)))
            .andExpect(status().isOk());

        // Validate the ShoppingCartItems in the database
        List<ShoppingCartItems> shoppingCartItemsList = shoppingCartItemsRepository.findAll();
        assertThat(shoppingCartItemsList).hasSize(databaseSizeBeforeUpdate);
        ShoppingCartItems testShoppingCartItems = shoppingCartItemsList.get(shoppingCartItemsList.size() - 1);
        assertThat(testShoppingCartItems.getQuantity()).isEqualTo(UPDATED_QUANTITY);
    }

    @Test
    @Transactional
    public void updateNonExistingShoppingCartItems() throws Exception {
        int databaseSizeBeforeUpdate = shoppingCartItemsRepository.findAll().size();

        // Create the ShoppingCartItems
        ShoppingCartItemsDTO shoppingCartItemsDTO = shoppingCartItemsMapper.toDto(shoppingCartItems);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restShoppingCartItemsMockMvc.perform(put("/api/shopping-cart-items")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(shoppingCartItemsDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ShoppingCartItems in the database
        List<ShoppingCartItems> shoppingCartItemsList = shoppingCartItemsRepository.findAll();
        assertThat(shoppingCartItemsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteShoppingCartItems() throws Exception {
        // Initialize the database
        shoppingCartItemsRepository.saveAndFlush(shoppingCartItems);

        int databaseSizeBeforeDelete = shoppingCartItemsRepository.findAll().size();

        // Delete the shoppingCartItems
        restShoppingCartItemsMockMvc.perform(delete("/api/shopping-cart-items/{id}", shoppingCartItems.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<ShoppingCartItems> shoppingCartItemsList = shoppingCartItemsRepository.findAll();
        assertThat(shoppingCartItemsList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ShoppingCartItems.class);
        ShoppingCartItems shoppingCartItems1 = new ShoppingCartItems();
        shoppingCartItems1.setId(1L);
        ShoppingCartItems shoppingCartItems2 = new ShoppingCartItems();
        shoppingCartItems2.setId(shoppingCartItems1.getId());
        assertThat(shoppingCartItems1).isEqualTo(shoppingCartItems2);
        shoppingCartItems2.setId(2L);
        assertThat(shoppingCartItems1).isNotEqualTo(shoppingCartItems2);
        shoppingCartItems1.setId(null);
        assertThat(shoppingCartItems1).isNotEqualTo(shoppingCartItems2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ShoppingCartItemsDTO.class);
        ShoppingCartItemsDTO shoppingCartItemsDTO1 = new ShoppingCartItemsDTO();
        shoppingCartItemsDTO1.setId(1L);
        ShoppingCartItemsDTO shoppingCartItemsDTO2 = new ShoppingCartItemsDTO();
        assertThat(shoppingCartItemsDTO1).isNotEqualTo(shoppingCartItemsDTO2);
        shoppingCartItemsDTO2.setId(shoppingCartItemsDTO1.getId());
        assertThat(shoppingCartItemsDTO1).isEqualTo(shoppingCartItemsDTO2);
        shoppingCartItemsDTO2.setId(2L);
        assertThat(shoppingCartItemsDTO1).isNotEqualTo(shoppingCartItemsDTO2);
        shoppingCartItemsDTO1.setId(null);
        assertThat(shoppingCartItemsDTO1).isNotEqualTo(shoppingCartItemsDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(shoppingCartItemsMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(shoppingCartItemsMapper.fromId(null)).isNull();
    }
}
