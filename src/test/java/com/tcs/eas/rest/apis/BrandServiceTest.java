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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.tcs.eas.rest.apis.db.BrandDaoService;
import com.tcs.eas.rest.apis.db.BrandRepository;
import com.tcs.eas.rest.apis.db.ProductEntityRepository;
import com.tcs.eas.rest.apis.log.LoggingService;
import com.tcs.eas.rest.apis.model.Brand;
import com.tcs.eas.rest.apis.model.ProductBrandApiModel;
import com.tcs.eas.rest.apis.model.ProductEntity;

//@DataJpaTest
//@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class BrandServiceTest {
	@InjectMocks
	private BrandDaoService brandDaoService;

	@Mock
	private BrandRepository brandRepository;

	@Mock
	private ProductEntityRepository productEntityRepository;

	Brand brand;
	ProductEntity origin;
	ProductBrandApiModel brand1;

	@Autowired
	LoggingService loggingService;
	// @Autowired
	// private TestEntityManager entityManager;

	@BeforeEach
	public void setup() {

		MockitoAnnotations.openMocks(this);
		origin = new ProductEntity();
		origin.setEntityName("USA");
		origin.setEntityType("Brand");
		productEntityRepository.save(origin);
		brand1 = new ProductBrandApiModel(100, "Apple", "USA", "Goood mobile brands");
		brand = new Brand();
		brand.setBrandName("Apple");
		brand.setBrandOrigin( origin);
		brand.setBrandDescription("Test Brand");
		brand.setCreatedBy("admin");
		brand.setUpdatedBy("admin");
		brand.setCreatedTimestamp(new Date());
		brand.setUpdatedTimestamp(new Date());

	}

	@Test
	void getAllBrands() {

		// Brand brand1 =new Brand( "Apple", origin, "Test Brand", "admin", "admin");
		Brand brand2 = new Brand("Samsung", origin,  "Test Brand" , "admin", "admin");

		List<Brand> brands = new ArrayList<Brand>();

		brands.add(brand);
		brands.add(brand2);

		// Iterable <Brand>tutorials = brandRepository.findAll();

		when(brandRepository.findAll()).thenReturn(brands);
		// test
		List<ProductBrandApiModel> brandList = brandDaoService.getAllBrands();
		loggingService.writeProcessLog("GET", "brands", "getAllBrands", brandList);
		assertEquals(brandList.size(), 2);

		/*
		 * List<ProductBrandApiModel> brands = new ArrayList<ProductBrandApiModel>();
		 * ProductBrandApiModel brand1 = new ProductBrandApiModel(100, "Apple", "Brand",
		 * "Test Brand"); ProductBrandApiModel brand2 = new ProductBrandApiModel(101,
		 * "Samsung", "Brand", "Test Brand"); ProductBrandApiModel brand3 = new
		 * ProductBrandApiModel(101, "Samsung", "Brand", "Test Brand");
		 * brands.add(brand1); brands.add(brand2); brands.add(brand3);
		 * 
		 * 
		 * when(brandDaoService.getAllBrands()).thenReturn(brands);
		 * 
		 * //test List<ProductBrandApiModel> brandList = brandDaoService.getAllBrands();
		 * assertEquals(3, brandList.size()); verify(brandDaoService,
		 * times(1)).getAllBrands();
		 */
	}

	@Test
	void getBrandById() {

		when(brandRepository.findById(100)).thenReturn(Optional.of(brand));
		ProductBrandApiModel prodbrand = brandDaoService.getBrandById(100);
		loggingService.writeProcessLog("GET", "brands", "getBrandById", prodbrand);
		assertEquals("Apple", prodbrand.getBrandName());
	}

	@Test
	@MockitoSettings(strictness = Strictness.LENIENT)
	public void addBrand() {
		productEntityRepository.save(origin);
		when(productEntityRepository.findByEntityNameAndEntityType(brand.getBrandOrigin().getEntityName(), null))
				.thenReturn(origin);
		productEntityRepository.save(origin);
		List<ProductBrandApiModel> brands = new ArrayList<ProductBrandApiModel>();
		brands.add(brand1);
		when(brandRepository.save(brand)).thenReturn(brand);
		List<ProductBrandApiModel> result = brandDaoService.addBrands(brands);
		// loggingService.writeProcessLog("POST", "brands", "addBrands", brands);
		assertThat(result.size() > 0);
	}

	@Test
	@MockitoSettings(strictness = Strictness.LENIENT)
	public void deleteBrandById() {
		when(brandRepository.findById(1000)).thenReturn(Optional.of(brand));
	
		willDoNothing().given(brandRepository).deleteById(1000);

		String result = brandDaoService.deleteBrandById(1000);
		
		assertThat(result.contains("Success"));
	}

	@Test
	@MockitoSettings(strictness = Strictness.LENIENT)
	public void deleteBrandByIdNotFound() {
		String result = null;
		when(brandRepository.findById(1000)).thenReturn(Optional.of(brand));
		willDoNothing().given(brandRepository).deleteById(1000);
		try {
		 result = brandDaoService.deleteBrandById(1001);
		}
		catch(Exception e) {
			assertThat(e.getMessage().contains("does not exist"));
		}
		
		
	}

	@Test
	public void updateBrandById() {
		
		brand1 = new ProductBrandApiModel(100, "Apple", "USA", "Goood mobile brands");
		brand = new Brand(100, "Apple", origin, "Test Brand", "admin", "admin");
	
		when(productEntityRepository.findByEntityNameAndEntityType(brand.getBrandOrigin().getEntityName(), null))
		.thenReturn(origin);
		when(brandRepository.findById(100)).thenReturn(Optional.of(brand));
		when(brandRepository.save(brand)).thenReturn(brand);
		brand1.setBrandName("Apple1");
		ProductBrandApiModel brand2 = brandDaoService.updateBrandById(brand1);
		loggingService.writeProcessLog("UPDATE", "brands", "updateBrands", brand2);
		assertThat(brand2.getBrandName().equalsIgnoreCase("Apple1"));
	}

	/*
	 * @Test public void updateBrandByIdIsNull() {
	 * when(brandRepository.findById(100)).thenReturn(Optional.of(brand));
	 * when(brandRepository.save(brand)).thenReturn(brand); brand1.setBrandId(9865);
	 * ProductBrandApiModel brand2 = brandDaoService.updateBrandById(brand1);
	 * assertThat(brand2.getBrandName().equalsIgnoreCase("Apple1")); }
	 */
}
