package com.vcu.ramhacks.repository;

import com.vcu.ramhacks.domain.Purchase;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Purchase entity.
 */
@SuppressWarnings("unused")
public interface PurchaseRepository extends JpaRepository<Purchase,Long> {

}
