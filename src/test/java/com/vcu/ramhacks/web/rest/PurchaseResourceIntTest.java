package com.vcu.ramhacks.web.rest;

import com.vcu.ramhacks.Ramhacks2016App;

import com.vcu.ramhacks.domain.Purchase;
import com.vcu.ramhacks.repository.PurchaseRepository;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.hamcrest.Matchers.hasItem;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the PurchaseResource REST controller.
 *
 * @see PurchaseResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Ramhacks2016App.class)
public class PurchaseResourceIntTest {

    private static final String DEFAULT_PURCHASE_ID = "AAAAA";
    private static final String UPDATED_PURCHASE_ID = "BBBBB";
    private static final String DEFAULT_TYPE = "AAAAA";
    private static final String UPDATED_TYPE = "BBBBB";
    private static final String DEFAULT_PURCHASE_DATE = "AAAAA";
    private static final String UPDATED_PURCHASE_DATE = "BBBBB";

    private static final Double DEFAULT_AMOUNT = 1D;
    private static final Double UPDATED_AMOUNT = 2D;
    private static final String DEFAULT_DESCRIPTION = "AAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBB";

    @Inject
    private PurchaseRepository purchaseRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restPurchaseMockMvc;

    private Purchase purchase;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        PurchaseResource purchaseResource = new PurchaseResource();
        ReflectionTestUtils.setField(purchaseResource, "purchaseRepository", purchaseRepository);
        this.restPurchaseMockMvc = MockMvcBuilders.standaloneSetup(purchaseResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Purchase createEntity(EntityManager em) {
        Purchase purchase = new Purchase();
        purchase.setPurchaseId(DEFAULT_PURCHASE_ID);
        purchase.setType(DEFAULT_TYPE);
        purchase.setPurchaseDate(DEFAULT_PURCHASE_DATE);
        purchase.setAmount(DEFAULT_AMOUNT);
        purchase.setDescription(DEFAULT_DESCRIPTION);
        return purchase;
    }

    @Before
    public void initTest() {
        purchase = createEntity(em);
    }

    @Test
    @Transactional
    public void createPurchase() throws Exception {
        int databaseSizeBeforeCreate = purchaseRepository.findAll().size();

        // Create the Purchase

        restPurchaseMockMvc.perform(post("/api/purchases")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(purchase)))
                .andExpect(status().isCreated());

        // Validate the Purchase in the database
        List<Purchase> purchases = purchaseRepository.findAll();
        assertThat(purchases).hasSize(databaseSizeBeforeCreate + 1);
        Purchase testPurchase = purchases.get(purchases.size() - 1);
        assertThat(testPurchase.getPurchaseId()).isEqualTo(DEFAULT_PURCHASE_ID);
        assertThat(testPurchase.getType()).isEqualTo(DEFAULT_TYPE);
        assertThat(testPurchase.getPurchaseDate()).isEqualTo(DEFAULT_PURCHASE_DATE);
        assertThat(testPurchase.getAmount()).isEqualTo(DEFAULT_AMOUNT);
        assertThat(testPurchase.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllPurchases() throws Exception {
        // Initialize the database
        purchaseRepository.saveAndFlush(purchase);

        // Get all the purchases
        restPurchaseMockMvc.perform(get("/api/purchases?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.[*].id").value(hasItem(purchase.getId().intValue())))
                .andExpect(jsonPath("$.[*].purchaseId").value(hasItem(DEFAULT_PURCHASE_ID.toString())))
                .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE.toString())))
                .andExpect(jsonPath("$.[*].purchaseDate").value(hasItem(DEFAULT_PURCHASE_DATE.toString())))
                .andExpect(jsonPath("$.[*].amount").value(hasItem(DEFAULT_AMOUNT.doubleValue())))
                .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())));
    }

    @Test
    @Transactional
    public void getPurchase() throws Exception {
        // Initialize the database
        purchaseRepository.saveAndFlush(purchase);

        // Get the purchase
        restPurchaseMockMvc.perform(get("/api/purchases/{id}", purchase.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(purchase.getId().intValue()))
            .andExpect(jsonPath("$.purchaseId").value(DEFAULT_PURCHASE_ID.toString()))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE.toString()))
            .andExpect(jsonPath("$.purchaseDate").value(DEFAULT_PURCHASE_DATE.toString()))
            .andExpect(jsonPath("$.amount").value(DEFAULT_AMOUNT.doubleValue()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingPurchase() throws Exception {
        // Get the purchase
        restPurchaseMockMvc.perform(get("/api/purchases/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePurchase() throws Exception {
        // Initialize the database
        purchaseRepository.saveAndFlush(purchase);
        int databaseSizeBeforeUpdate = purchaseRepository.findAll().size();

        // Update the purchase
        Purchase updatedPurchase = purchaseRepository.findOne(purchase.getId());
        updatedPurchase.setPurchaseId(UPDATED_PURCHASE_ID);
        updatedPurchase.setType(UPDATED_TYPE);
        updatedPurchase.setPurchaseDate(UPDATED_PURCHASE_DATE);
        updatedPurchase.setAmount(UPDATED_AMOUNT);
        updatedPurchase.setDescription(UPDATED_DESCRIPTION);

        restPurchaseMockMvc.perform(put("/api/purchases")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedPurchase)))
                .andExpect(status().isOk());

        // Validate the Purchase in the database
        List<Purchase> purchases = purchaseRepository.findAll();
        assertThat(purchases).hasSize(databaseSizeBeforeUpdate);
        Purchase testPurchase = purchases.get(purchases.size() - 1);
        assertThat(testPurchase.getPurchaseId()).isEqualTo(UPDATED_PURCHASE_ID);
        assertThat(testPurchase.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testPurchase.getPurchaseDate()).isEqualTo(UPDATED_PURCHASE_DATE);
        assertThat(testPurchase.getAmount()).isEqualTo(UPDATED_AMOUNT);
        assertThat(testPurchase.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void deletePurchase() throws Exception {
        // Initialize the database
        purchaseRepository.saveAndFlush(purchase);
        int databaseSizeBeforeDelete = purchaseRepository.findAll().size();

        // Get the purchase
        restPurchaseMockMvc.perform(delete("/api/purchases/{id}", purchase.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Purchase> purchases = purchaseRepository.findAll();
        assertThat(purchases).hasSize(databaseSizeBeforeDelete - 1);
    }
}
