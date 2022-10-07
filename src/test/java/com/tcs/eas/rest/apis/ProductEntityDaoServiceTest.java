package com.tcs.eas.rest.apis;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import com.tcs.eas.rest.apis.db.ProductEntityDaoService;
import com.tcs.eas.rest.apis.db.ProductEntityRepository;
import com.tcs.eas.rest.apis.model.ProductEntity;
import com.tcs.eas.rest.apis.model.ProductEntityApiModel;

@ExtendWith(MockitoExtension.class)
class ProductEntityDaoServiceTest {

	@InjectMocks
	private ProductEntityDaoService productEntityDaoService;
	
	@Mock
	ProductEntityRepository productEntityRepository;
	
	private ProductEntity prodEntity ;
	
	  @BeforeEach
			 void setup() {
				MockitoAnnotations.openMocks(this);
				 prodEntity = new ProductEntity();
				prodEntity.setProductEntityId(100);
				prodEntity.setEntityName("Brand");
				prodEntity.setEntityDescription("Brand Name");
				prodEntity.setEntityType("Type");
				prodEntity.setStatus("A");
				prodEntity.setCreatedBy("Admin");
				prodEntity.setCreatedTimestamp(new Date());
				prodEntity.setUpdatedTimestamp(new Date());
				prodEntity.setUpdatedBy("Admin");
			}
	  
	@Test
	 void getAllProductEntity() {
		List<ProductEntity> entityList = new ArrayList<ProductEntity>();
		
		entityList.add(prodEntity);
		
		ProductEntity prodEntity2 = new ProductEntity();
		prodEntity2.setProductEntityId(100);
		prodEntity2.setEntityName("Brand");
		prodEntity2.setEntityDescription("Brand Name");
		prodEntity2.setEntityType("Type");
		prodEntity2.setStatus("A");
		prodEntity2.setCreatedBy("Admin");
		prodEntity2.setCreatedTimestamp(new Date());
		prodEntity2.setUpdatedTimestamp(new Date());
		prodEntity2.setUpdatedBy("Admin");
		
		entityList.add(prodEntity2);
		
		when(productEntityRepository.findAll()).thenReturn(entityList);
		
		//test
	  	List<ProductEntityApiModel> list = productEntityDaoService.getAllProductEntity();
	  	assertEquals(2, list.size());
       assertThat(list.get(1).getEntityName().equalsIgnoreCase("Brand"));
	}
	
	@Test
	 void addProductEntity() {
	when(productEntityRepository.save(prodEntity)).thenReturn(prodEntity);
	ProductEntityApiModel prodEntity2  = productEntityDaoService.addProductEntity(new ProductEntityApiModel(prodEntity));
	assertThat(prodEntity2).isNotNull();
	}
	
	@Test
	 void getProductEntityById() {
		
		when(productEntityRepository.findById(100)).thenReturn(Optional.of(prodEntity));	
	
		ProductEntityApiModel prodEntity2  = productEntityDaoService.getProductEntityById(100);
	assertThat(prodEntity2).isNotNull();
	}
	
	@Test
	 void updateProductEntityById() {
		when(productEntityRepository.findById(100)).thenReturn(Optional.of(prodEntity));	
		when(productEntityRepository.save(prodEntity)).thenReturn(prodEntity);
		prodEntity.setEntityType("Test");
		ProductEntityApiModel prodEntity2  = productEntityDaoService.updateProductEntityById(prodEntity);
		assertThat(prodEntity2.getEntityType()).isEqualTo("Test");
	}
	
	@Test
	@MockitoSettings(strictness = Strictness.LENIENT)
	 void deleteProductEntityById() {
		
		ProductEntity prodEntity2 = new ProductEntity();
		prodEntity2.setProductEntityId(100);
		prodEntity2.setEntityName("Brand");
		prodEntity2.setEntityDescription("Brand Name");
		prodEntity2.setEntityType("Type");
		prodEntity2.setStatus("A");
		prodEntity2.setCreatedBy("Admin");
		prodEntity2.setCreatedTimestamp(new Date());
		prodEntity2.setUpdatedTimestamp(new Date());
		prodEntity2.setUpdatedBy("Admin");
		
		 willDoNothing().given(productEntityRepository).deleteById(100);
		
		String result  = productEntityDaoService.deleteEntityById(100);
		assertThat(result).contains("Success");
	}
	
	@Test
	@MockitoSettings(strictness = Strictness.LENIENT)
	 void deleteProductEntityByIdNotFound() {
		
		
		 willDoNothing().given(productEntityRepository).deleteById(100);
		
		String result  = productEntityDaoService.deleteEntityById(101);
		assertThat(result).contains("Not Found");
	}
	
}
