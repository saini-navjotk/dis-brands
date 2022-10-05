package com.tcs.eas.rest.apis.controller;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
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
import com.tcs.eas.rest.apis.db.BrandDaoService;
import com.tcs.eas.rest.apis.log.LoggingService;
import com.tcs.eas.rest.apis.model.ProductBrandApiModel;
import com.tcs.eas.rest.apis.model.ProductEntity;


@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class BrandControllerTest {

	@InjectMocks
	BrandController brandController;
	
	@Autowired
    private MockMvc mockMvc;

	@Mock
	private BrandDaoService brandService;
	
	@Autowired
    private ObjectMapper objectMapper;
	
	@Mock
	LoggingService loggingService;
	// This object will be magically initialized by the initFields method below.
    private JacksonTester<ProductBrandApiModel> jsonBrand;
    private JacksonTester<List<ProductBrandApiModel>> jsonBrandList;
    
	@BeforeEach
    public void setup() {
        // We would need this line if we would not use the MockitoExtension
	
		MockitoAnnotations.openMocks(this);
		
        // Here we can't use @AutoConfigureJsonTesters because there isn't a Spring context
        JacksonTester.initFields(this, new ObjectMapper());
        // MockMvc standalone approach
        mockMvc = MockMvcBuilders.standaloneSetup(brandController).build();
    }
	
	@Test
	void testGetAllBrands() throws Exception {
		
		 // given - precondition or setup
		ProductEntity origin = new ProductEntity();
		  origin.setEntityName("Brand");
		  origin.setEntityType("Brand");
		  List<ProductBrandApiModel> brands = new ArrayList<ProductBrandApiModel>();
		  ProductBrandApiModel brand1 = new ProductBrandApiModel(100, "Apple", "Brand", "Test Brand");
		  ProductBrandApiModel brand2 = new ProductBrandApiModel(101, "Samsung", "Brand", "Test Brand");
		  ProductBrandApiModel brand3 = new ProductBrandApiModel(101, "Samsung", "Brand", "Test Brand");
		  brands.add(brand1);
		  brands.add(brand2);
		  brands.add(brand3);
		  
		  given(brandService.getAllBrands()).willReturn(brands);
		  
		  // when -  action or the behaviour that we are going test
	       ResultActions response = mockMvc.perform(get("/api/v1/dis/brands"));
	       
	       loggingService.writeProcessLog("GET", "brands", "getAllBrands", brands);
	    // then - verify the output
	        response.andExpect(status().isOk())
	                .andDo(print())
	                .andExpect(jsonPath("$.size()",
	                        is(brands.size())));
		
	}

	@Test
	void testGetBrandById() throws Exception {
		// given - precondition or setup
		 ProductBrandApiModel brand1 = new ProductBrandApiModel(100, "Apple", "Brand", "Test Brand");
		 given(brandService.getBrandById(100)).willReturn(brand1);
		 
		// when -  action or the behaviour that we are going test
	        ResultActions response = mockMvc.perform(get("/api/v1/dis/brands/{id}", 100));

	        loggingService.writeProcessLog("GET", "brands", "getBrandById", brand1);
	    // then - verify the output
	    response.andExpect(status().isOk())
	          .andDo(print())
	          .andExpect(jsonPath("$.brandId", is(brand1.getBrandId())))
	          .andExpect(jsonPath("$.brandDescription", is(brand1.getBrandDescription())))
	          .andExpect(jsonPath("$.brandName", is(brand1.getBrandName())));
	}

	@Test
	void testAddBrands() throws Exception {
		// given - precondition or setup
		List <ProductBrandApiModel> list = new ArrayList<ProductBrandApiModel>();
		ProductBrandApiModel brand1 = new ProductBrandApiModel(100, "Apple", "Brand", "Test Brand");
		list.add(brand1);
		given(brandService.addBrands(list)).willReturn(list);
		
		// when -  action or the behaviour that we are going test
        ResultActions response = mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/dis/brands").contentType(MediaType.APPLICATION_JSON)
        						.content("[\n" + 
        								"    {\n" + 
        								"        \"brandId\": 4,\n" + 
        								"        \"brandName\": \"APPLE\",\n" + 
        								"        \"brandOrigin\": \"USA\",\n" + 
        								"        \"brandDescription\": \"Goood mobile brands\"\n" + 
        								"    }\n" + 
        							"]"));
        
     // then - verify the output
	    response.andExpect(status().isCreated())
	          .andDo(print());
	       //   .andExpect(jsonPath("$.[0].brandId",
                  //    is(brand1.getBrandId())));
	}

	@Test
	void testUpdateBrandById() throws Exception {
		// given - precondition or setup
				
		ProductBrandApiModel brand1 = new ProductBrandApiModel(100, "Apple", "Brand", "Test Brand");
				
		given(brandService.updateBrandById(brand1)).willReturn(brand1);
				
		// when -  action or the behaviour that we are going test
		ResultActions response = mockMvc.perform(MockMvcRequestBuilders.put("/api/v1/dis/brands").header("transaction-id", 123).contentType(MediaType.APPLICATION_JSON)
		        						.content(jsonBrand.write(brand1).getJson()));//.andReturn().getResponse();
		        
		// then - verify the output
		//	assertThat(response.getStatus()).isEqualTo(HttpStatus.CREATED.value());
		 response.andExpect(status().isOk())
			      .andDo(print());
			     //  .andExpect(jsonPath("$.brandId", is(brand1.getBrandId())));
	}
	
	@Test
	void testUpdateBrandByIdIsNull() throws Exception {
		// given - precondition or setup
				
		ProductBrandApiModel brand1 = new ProductBrandApiModel(100, "Apple", "Brand", "Test Brand");
		ProductBrandApiModel brand2 = new ProductBrandApiModel(101, "Samsung", "Brand", "Test Brand");
				
		given(brandService.updateBrandById(brand1)).willReturn(brand1);
				
		// when -  action or the behaviour that we are going test
		ResultActions response = mockMvc.perform(MockMvcRequestBuilders.put("/api/v1/dis/brands").header("transaction-id", 123).contentType(MediaType.APPLICATION_JSON)
		        						.content(jsonBrand.write(brand2).getJson()));//.andReturn().getResponse();
		        
		// then - verify the output
	
		 //response.andExpect(status().is4xxClientError());
	}

	@Test
	void testDeleteBrandById() throws IOException, Exception {
		// given - precondition or setup
		
				//ProductBrandApiModel brand1 = new ProductBrandApiModel(100, "Apple", "Brand", "Test Brand");
						
				given(brandService.deleteBrandById(100)).willReturn("Successfull");
						
				// when -  action or the behaviour that we are going test
				ResultActions response = mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/dis/brands/100").header("transaction-id", 123));
				        
				// then - verify the output
				//	assertThat(response.getStatus()).isEqualTo(HttpStatus.CREATED.value());
				 response.andExpect(status().isOk())
					      .andDo(print());
	}

}
