package com.vcu.ramhacks.repository;

import com.vcu.ramhacks.domain.MonthlyGoal;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the MonthlyGoal entity.
 */
@SuppressWarnings("unused")
public interface MonthlyGoalRepository extends JpaRepository<MonthlyGoal,Long> {

}
