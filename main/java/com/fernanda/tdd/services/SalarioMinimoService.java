package com.fernanda.tdd.services;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fernanda.tdd.model.SalarioMinimo;

public interface SalarioMinimoService extends JpaRepository<SalarioMinimo, Integer> {

	SalarioMinimo findSalarioById(int id);
	
}
