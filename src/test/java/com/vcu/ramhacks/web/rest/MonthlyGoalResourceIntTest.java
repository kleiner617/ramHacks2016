package com.vcu.ramhacks.web.rest;

import com.vcu.ramhacks.Ramhacks2016App;

import com.vcu.ramhacks.domain.MonthlyGoal;
import com.vcu.ramhacks.repository.MonthlyGoalRepository;

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
 * Test class for the MonthlyGoalResource REST controller.
 *
 * @see MonthlyGoalResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Ramhacks2016App.class)
public class MonthlyGoalResourceIntTest {

    private static final String DEFAULT_MONTH = "AAAAA";
    private static final String UPDATED_MONTH = "BBBBB";

    private static final Double DEFAULT_PERCENTAGE = 1D;
    private static final Double UPDATED_PERCENTAGE = 2D;

    @Inject
    private MonthlyGoalRepository monthlyGoalRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restMonthlyGoalMockMvc;

    private MonthlyGoal monthlyGoal;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        MonthlyGoalResource monthlyGoalResource = new MonthlyGoalResource();
        ReflectionTestUtils.setField(monthlyGoalResource, "monthlyGoalRepository", monthlyGoalRepository);
        this.restMonthlyGoalMockMvc = MockMvcBuilders.standaloneSetup(monthlyGoalResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MonthlyGoal createEntity(EntityManager em) {
        MonthlyGoal monthlyGoal = new MonthlyGoal();
        monthlyGoal.setMonth(DEFAULT_MONTH);
        monthlyGoal.setPercentage(DEFAULT_PERCENTAGE);
        return monthlyGoal;
    }

    @Before
    public void initTest() {
        monthlyGoal = createEntity(em);
    }

    @Test
    @Transactional
    public void createMonthlyGoal() throws Exception {
        int databaseSizeBeforeCreate = monthlyGoalRepository.findAll().size();

        // Create the MonthlyGoal

        restMonthlyGoalMockMvc.perform(post("/api/monthly-goals")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(monthlyGoal)))
                .andExpect(status().isCreated());

        // Validate the MonthlyGoal in the database
        List<MonthlyGoal> monthlyGoals = monthlyGoalRepository.findAll();
        assertThat(monthlyGoals).hasSize(databaseSizeBeforeCreate + 1);
        MonthlyGoal testMonthlyGoal = monthlyGoals.get(monthlyGoals.size() - 1);
        assertThat(testMonthlyGoal.getMonth()).isEqualTo(DEFAULT_MONTH);
        assertThat(testMonthlyGoal.getPercentage()).isEqualTo(DEFAULT_PERCENTAGE);
    }

    @Test
    @Transactional
    public void getAllMonthlyGoals() throws Exception {
        // Initialize the database
        monthlyGoalRepository.saveAndFlush(monthlyGoal);

        // Get all the monthlyGoals
        restMonthlyGoalMockMvc.perform(get("/api/monthly-goals?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.[*].id").value(hasItem(monthlyGoal.getId().intValue())))
                .andExpect(jsonPath("$.[*].month").value(hasItem(DEFAULT_MONTH.toString())))
                .andExpect(jsonPath("$.[*].percentage").value(hasItem(DEFAULT_PERCENTAGE.doubleValue())));
    }

    @Test
    @Transactional
    public void getMonthlyGoal() throws Exception {
        // Initialize the database
        monthlyGoalRepository.saveAndFlush(monthlyGoal);

        // Get the monthlyGoal
        restMonthlyGoalMockMvc.perform(get("/api/monthly-goals/{id}", monthlyGoal.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(monthlyGoal.getId().intValue()))
            .andExpect(jsonPath("$.month").value(DEFAULT_MONTH.toString()))
            .andExpect(jsonPath("$.percentage").value(DEFAULT_PERCENTAGE.doubleValue()));
    }

    @Test
    @Transactional
    public void getNonExistingMonthlyGoal() throws Exception {
        // Get the monthlyGoal
        restMonthlyGoalMockMvc.perform(get("/api/monthly-goals/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateMonthlyGoal() throws Exception {
        // Initialize the database
        monthlyGoalRepository.saveAndFlush(monthlyGoal);
        int databaseSizeBeforeUpdate = monthlyGoalRepository.findAll().size();

        // Update the monthlyGoal
        MonthlyGoal updatedMonthlyGoal = monthlyGoalRepository.findOne(monthlyGoal.getId());
        updatedMonthlyGoal.setMonth(UPDATED_MONTH);
        updatedMonthlyGoal.setPercentage(UPDATED_PERCENTAGE);

        restMonthlyGoalMockMvc.perform(put("/api/monthly-goals")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedMonthlyGoal)))
                .andExpect(status().isOk());

        // Validate the MonthlyGoal in the database
        List<MonthlyGoal> monthlyGoals = monthlyGoalRepository.findAll();
        assertThat(monthlyGoals).hasSize(databaseSizeBeforeUpdate);
        MonthlyGoal testMonthlyGoal = monthlyGoals.get(monthlyGoals.size() - 1);
        assertThat(testMonthlyGoal.getMonth()).isEqualTo(UPDATED_MONTH);
        assertThat(testMonthlyGoal.getPercentage()).isEqualTo(UPDATED_PERCENTAGE);
    }

    @Test
    @Transactional
    public void deleteMonthlyGoal() throws Exception {
        // Initialize the database
        monthlyGoalRepository.saveAndFlush(monthlyGoal);
        int databaseSizeBeforeDelete = monthlyGoalRepository.findAll().size();

        // Get the monthlyGoal
        restMonthlyGoalMockMvc.perform(delete("/api/monthly-goals/{id}", monthlyGoal.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<MonthlyGoal> monthlyGoals = monthlyGoalRepository.findAll();
        assertThat(monthlyGoals).hasSize(databaseSizeBeforeDelete - 1);
    }
}
