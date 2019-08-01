package com.fernanda.tdd;

import java.text.SimpleDateFormat;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.jdbc.JdbcTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fernanda.tdd.controllers.SalarioMinimoController;
import com.fernanda.tdd.model.SalarioMinimo;
import com.fernanda.tdd.services.SalarioMinimoService;

public class SalarioMinimoControllerTest extends TddApplicationTests {

	private MockMvc mockMvc;

	@Autowired
	private SalarioMinimoController controller;
	
	@Autowired
	private SalarioMinimoService service;
	
	@Autowired
	private JdbcTemplate jdbcTemplate;	

	@Before
	public void setUp() {
		this.mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
	}
	
	@After
	public void tearDown() {
	  JdbcTestUtils.deleteFromTables(jdbcTemplate, "salarios_minimos");
	}

	@Test
	public void testGetIndexSalarioMinimoController() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/salarios")).andExpect(MockMvcResultMatchers.status().isOk());
	}

	@Test
	public void testGetSaveSalarioMinimoController() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/salarios/novo"))
				.andExpect(MockMvcResultMatchers.model().attributeExists("salarioMinimo"))
				.andExpect(MockMvcResultMatchers.status().isOk());
	}
	
	@Test
	public void testPostSaveSalarioMinimoController() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.post("/salarios")
				.param("estado", "BR")
				.param("salario", "800")
				.param("dataInicio", "01/01/2019")
				.param("dataFim", "02/02/2019")).andExpect(MockMvcResultMatchers.redirectedUrl("/salarios/novo"));
	}
	
	@Test
	public void testPUTSalarioMinimoController() throws Exception {
		SalarioMinimo salarioMinimo = (SalarioMinimo) service.save(new SalarioMinimo("AL", "800", new SimpleDateFormat("dd/MM/yyyy").parse("01/01/2016"), new SimpleDateFormat("dd/MM/yyyy").parse("01/02/2016"), "decreto test"));
		this.mockMvc.perform(MockMvcRequestBuilders.put("/salarios/" + salarioMinimo.getId() + "/editar")).andExpect(MockMvcResultMatchers.redirectedUrl("/salarios"));
	}

}
