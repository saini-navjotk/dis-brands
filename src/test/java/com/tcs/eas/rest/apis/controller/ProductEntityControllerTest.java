package com.tcs.eas.rest.apis.controller;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tcs.eas.rest.apis.db.ProductEntityDaoService;
import com.tcs.eas.rest.apis.log.LoggingService;
import com.tcs.eas.rest.apis.model.ProductEntity;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class ProductEntityControllerTest {

	@InjectMocks
	ProductEntityController productEntityController;

	@Autowired
	private MockMvc mockMvc;

	@Mock
	private ProductEntityDaoService productEntityService;

	@Autowired
	private ObjectMapper objectMapper;
	
	@Mock
	LoggingService loggingService;

	// This object will be magically initialized by the initFields method below.
	private JacksonTester<ProductEntity> jsonProductEntity;
	private JacksonTester<List<ProductEntity>> jsonProductEntityList;

	@BeforeEach
	public void setup() {
		// We would need this line if we would not use the MockitoExtension

		MockitoAnnotations.openMocks(this);

		// Here we can't use @AutoConfigureJsonTesters because there isn't a Spring
		// context
		JacksonTester.initFields(this, new ObjectMapper());
		// MockMvc standalone approach
		mockMvc = MockMvcBuilders.standaloneSetup(productEntityController).build();
	}

	@Test
	void testGetAllProductEntity() throws Exception {
		// given - precondition or setup
		List<ProductEntity> productEntityList = new ArrayList<ProductEntity>();
		ProductEntity productEntity1 = new ProductEntity();
		productEntity1.setEntityName("Brand");
		productEntity1.setEntityType("Brand");
		productEntity1.setStatus("A");
		ProductEntity productEntity2 = new ProductEntity();
		productEntity1.setEntityName("Category");
		productEntity1.setEntityType("Category");
		productEntity1.setStatus("A");
		productEntityList.add(productEntity1);
		productEntityList.add(productEntity2);

		given(productEntityService.getAllProductEntity()).willReturn(productEntityList);

		// when - action or the behaviour that we are going test
		ResultActions response = mockMvc.perform(get("/api/v1/dis/productentity"));

		// loggingService.writeProcessLog("GET", "brands", "getAllBrands", brands);
		// then - verify the output
		response.andExpect(status().isOk()).andDo(print())
				.andExpect(jsonPath("$.size()", is(productEntityList.size())));
	}

	@Test
	void testGetProductEntityById() throws Exception {
		// given - precondition or setup
		ProductEntity productEntity1 = new ProductEntity();
		productEntity1.setProductEntityId(100);
		productEntity1.setEntityName("Brand");
		productEntity1.setEntityType("Brand");
		productEntity1.setStatus("A");

		given(productEntityService.getProductEntityById(100)).willReturn(productEntity1);

		// when - action or the behaviour that we are going test
		ResultActions response = mockMvc.perform(get("/api/v1/dis/productentity/{id}", 100));

		// then - verify the output
		response.andExpect(status().isOk()).andDo(print())
				.andExpect(jsonPath("$.productEntityId", is(productEntity1.getProductEntityId())))
				.andExpect(jsonPath("$.entityName", is(productEntity1.getEntityName())));
	}
	
	@Test
	void testGetProductEntityByIdNotFound() throws Exception {
		// given - precondition or setup
		ProductEntity productEntity1 = new ProductEntity();
		productEntity1.setProductEntityId(100);
		productEntity1.setEntityName("Brand");
		productEntity1.setEntityType("Brand");
		productEntity1.setStatus("A");

		given(productEntityService.getProductEntityById(100)).willReturn(productEntity1);

		// when - action or the behaviour that we are going test
		ResultActions response = mockMvc.perform(get("/api/v1/dis/productentity/{id}", 101));

		// then - verify the output
		response.andExpect(status().is4xxClientError()).andDo(print());
	}

	@Test
	void testAddProductEntity() throws Exception {
		// given - precondition or setup
		ProductEntity productEntity1 = new ProductEntity();
		productEntity1.setProductEntityId(100);
		productEntity1.setProductEntityId(100);
		productEntity1.setEntityName("Brand");
		productEntity1.setEntityType("Brand");
		productEntity1.setStatus("A");
		given(productEntityService.addProductEntity(productEntity1)).willReturn(productEntity1);

		// when - action or the behaviour that we are going test
		ResultActions response = mockMvc
				.perform(MockMvcRequestBuilders.post("/api/v1/dis/productentity").header("transaction-id", 123).contentType(MediaType.APPLICATION_JSON)
						.content("{\n" + "        \"productEntityId\": 4,\n" + "        \"entityType\": \"Brand\",\n"
								+ "        \"entityName\": \"Brand\",\n"
								+ "        \"entityDescription\": \"Goood mobile brands\"\n" + "    }\n" ));

		// then - verify the output
		response.andExpect(status().isCreated()).andDo(print());
	}

	@Test
	void testUpdateProductEntityById() throws IOException, Exception {
		// given - precondition or setup
		
		ProductEntity productEntity1 = new ProductEntity();
		productEntity1.setProductEntityId(100);
		productEntity1.setEntityName("Brand");
		productEntity1.setEntityType("Brand");
		productEntity1.setStatus("A");
		
		given(productEntityService.getProductEntityById(100)).willReturn(productEntity1);				
		given(productEntityService.updateProductEntityById(productEntity1)).willReturn(productEntity1);
						
		// when -  action or the behaviour that we are going test
		ResultActions response = mockMvc.perform(MockMvcRequestBuilders.put("/api/v1/dis/productentity").header("transaction-id", 123).contentType(MediaType.APPLICATION_JSON)
				        						.content(jsonProductEntity.write(productEntity1).getJson()));//.andReturn().getResponse();
				        
		// then - verify the output
		response.andExpect(status().isOk())
			 .andDo(print());

	}
	
	@Test
	void testUpdateProductEntityByIdNotFound() throws IOException, Exception {
		// given - precondition or setup
		
		ProductEntity productEntity1 = new ProductEntity();
		productEntity1.setProductEntityId(100);
		productEntity1.setEntityName("Brand");
		productEntity1.setEntityType("Brand");
		productEntity1.setStatus("A");
		

		ProductEntity productEntity2 = new ProductEntity();
		productEntity2.setProductEntityId(101);
		productEntity2.setEntityName("Category");
		productEntity2.setEntityType("Category");
		productEntity2.setStatus("A");
		
		given(productEntityService.updateProductEntityById(productEntity1)).willReturn(productEntity1);
						
		// when -  action or the behaviour that we are going test
		ResultActions response = mockMvc.perform(MockMvcRequestBuilders.put("/api/v1/dis/productentity").header("transaction-id", 123).contentType(MediaType.APPLICATION_JSON)
				        						.content(jsonProductEntity.write(productEntity2).getJson()));//.andReturn().getResponse();
				        
		// then - verify the output
		response.andExpect(status().is4xxClientError())
			 .andDo(print());
	}

	@Test
	void testDeleteProductEntityById() throws IOException, Exception {
		// given - precondition or setup		
		given(productEntityService.deleteEntityById(100)).willReturn("Successfull");
		ProductEntity productEntity1 = new ProductEntity();
		productEntity1.setProductEntityId(100);
		productEntity1.setEntityName("Brand");
		productEntity1.setEntityType("Brand");
		productEntity1.setStatus("A");

		given(productEntityService.getProductEntityById(100)).willReturn(productEntity1);
								
		// when -  action or the behaviour that we are going test
		ResultActions response = mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/dis/productentity/100").header("transaction-id", 123));
						        
		// then - verify the output
		response.andExpect(status().isOk())
					 .andDo(print());
					
	}
	
	@Test
	void testDeleteProductEntityByIdNotFound() throws IOException, Exception {
		// given - precondition or setup		
		given(productEntityService.deleteEntityById(100)).willReturn("Successfull");

								
		// when -  action or the behaviour that we are going test
		ResultActions response = mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/dis/productentity/100").header("transaction-id", 123));
						        
		// then - verify the output
		response.andExpect(status().is4xxClientError())
					 .andDo(print());
					
	}

}
