package com.vcu.ramhacks.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.vcu.ramhacks.domain.MonthlyGoal;

import com.vcu.ramhacks.repository.MonthlyGoalRepository;
import com.vcu.ramhacks.web.rest.util.HeaderUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing MonthlyGoal.
 */
@RestController
@RequestMapping("/api")
public class MonthlyGoalResource {

    private final Logger log = LoggerFactory.getLogger(MonthlyGoalResource.class);
        
    @Inject
    private MonthlyGoalRepository monthlyGoalRepository;

    /**
     * POST  /monthly-goals : Create a new monthlyGoal.
     *
     * @param monthlyGoal the monthlyGoal to create
     * @return the ResponseEntity with status 201 (Created) and with body the new monthlyGoal, or with status 400 (Bad Request) if the monthlyGoal has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/monthly-goals",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<MonthlyGoal> createMonthlyGoal(@RequestBody MonthlyGoal monthlyGoal) throws URISyntaxException {
        log.debug("REST request to save MonthlyGoal : {}", monthlyGoal);
        if (monthlyGoal.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("monthlyGoal", "idexists", "A new monthlyGoal cannot already have an ID")).body(null);
        }
        MonthlyGoal result = monthlyGoalRepository.save(monthlyGoal);
        return ResponseEntity.created(new URI("/api/monthly-goals/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("monthlyGoal", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /monthly-goals : Updates an existing monthlyGoal.
     *
     * @param monthlyGoal the monthlyGoal to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated monthlyGoal,
     * or with status 400 (Bad Request) if the monthlyGoal is not valid,
     * or with status 500 (Internal Server Error) if the monthlyGoal couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/monthly-goals",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<MonthlyGoal> updateMonthlyGoal(@RequestBody MonthlyGoal monthlyGoal) throws URISyntaxException {
        log.debug("REST request to update MonthlyGoal : {}", monthlyGoal);
        if (monthlyGoal.getId() == null) {
            return createMonthlyGoal(monthlyGoal);
        }
        MonthlyGoal result = monthlyGoalRepository.save(monthlyGoal);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("monthlyGoal", monthlyGoal.getId().toString()))
            .body(result);
    }

    /**
     * GET  /monthly-goals : get all the monthlyGoals.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of monthlyGoals in body
     */
    @RequestMapping(value = "/monthly-goals",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<MonthlyGoal> getAllMonthlyGoals() {
        log.debug("REST request to get all MonthlyGoals");
        List<MonthlyGoal> monthlyGoals = monthlyGoalRepository.findAll();
        return monthlyGoals;
    }

    /**
     * GET  /monthly-goals/:id : get the "id" monthlyGoal.
     *
     * @param id the id of the monthlyGoal to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the monthlyGoal, or with status 404 (Not Found)
     */
    @RequestMapping(value = "/monthly-goals/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<MonthlyGoal> getMonthlyGoal(@PathVariable Long id) {
        log.debug("REST request to get MonthlyGoal : {}", id);
        MonthlyGoal monthlyGoal = monthlyGoalRepository.findOne(id);
        return Optional.ofNullable(monthlyGoal)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /monthly-goals/:id : delete the "id" monthlyGoal.
     *
     * @param id the id of the monthlyGoal to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @RequestMapping(value = "/monthly-goals/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteMonthlyGoal(@PathVariable Long id) {
        log.debug("REST request to delete MonthlyGoal : {}", id);
        monthlyGoalRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("monthlyGoal", id.toString())).build();
    }

}
