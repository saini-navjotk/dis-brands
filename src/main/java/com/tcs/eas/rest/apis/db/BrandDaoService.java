package com.tcs.eas.rest.apis.db;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.tcs.eas.rest.apis.Constants;
import com.tcs.eas.rest.apis.exception.BrandNotFound;
import com.tcs.eas.rest.apis.exception.BrandOriginNotFound;
import com.tcs.eas.rest.apis.model.Brand;
import com.tcs.eas.rest.apis.model.ProductBrandApiModel;
import com.tcs.eas.rest.apis.model.ProductEntity;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class BrandDaoService {

	@Autowired
	private BrandRepository brandRepository;

	@Autowired
	private ProductEntityRepository productEntityRepository;

	@Value(value = "${ADMIN_USER}")
	private String adminUser;

	@Value(value = "${API_USER}")
	private String apiUser;

	@Value(value = "${ENTITY_TYPE_BRAND_ORIGIN}")
	private String brandOriginEntityType;

	public List<ProductBrandApiModel> addBrands(
			List<ProductBrandApiModel> productBrands/* , MultipartFile brandLogo */) {


		List<Brand> brands = productBrands.stream().map(b -> new Brand(b.getBrandName(),
				getBrandOriginEntityIdByName(b.getBrandOrigin()), b.getBrandDescription(), adminUser, adminUser))
				.collect(Collectors.toList());

		brands = brandRepository.saveAll(brands);

		return productBrands = brands.stream().map(b -> new ProductBrandApiModel(b.getBrandId(), b.getBrandName(),
				b.getBrandOrigin().getEntityName(), b.getBrandDescription())).collect(Collectors.toList());
	}

	public List<ProductBrandApiModel> getAllBrands() {

		List<Brand> brands = brandRepository.findAll();

		return brands.stream().map(b -> new ProductBrandApiModel(b.getBrandId(),
				b.getBrandName(), b.getBrandOrigin().getEntityName(), b.getBrandDescription()))
				.collect(Collectors.toList());

		
	}

	public ProductBrandApiModel getBrandById(int id) {
		Optional<Brand> optionalBrand = brandRepository.findById(id);
		Brand brand = null;
		ProductBrandApiModel productBrand = null;
		if (optionalBrand.isPresent()) {
			brand = optionalBrand.get();
			productBrand = new ProductBrandApiModel();
			productBrand.setBrandId(brand.getBrandId());
			productBrand.setBrandName(brand.getBrandName());
			productBrand.setBrandDescription(brand.getBrandDescription());
			productBrand.setBrandOrigin(brand.getBrandOrigin().getEntityName());
		}

		return productBrand;
	}

	public String deleteBrandById(Integer id) {

		Optional<Brand> optionalBrand = brandRepository.findById(id);

		if (optionalBrand.isPresent()) {
			brandRepository.delete(optionalBrand.get());

			return "Brand Deleted Successfully !";
		} else {
			throw new BrandNotFound("Brand Id " + id + Constants.DOES_NOT_EXIST);
		}
	}

	public ProductBrandApiModel updateBrandById(ProductBrandApiModel brand) {

		ProductBrandApiModel oldApiBrand = getBrandById(brand.getBrandId());

		if (oldApiBrand != null) {
			Brand oldBrand = new Brand();
			oldBrand.setBrandId(brand.getBrandId());
			oldBrand.setBrandName(brand.getBrandName());
			oldBrand.setBrandOrigin(getBrandOriginEntityIdByName(brand.getBrandOrigin()));
			oldBrand.setBrandDescription(brand.getBrandDescription());
			oldBrand.setUpdatedBy(apiUser);
			oldBrand = brandRepository.save(oldBrand);

			return new ProductBrandApiModel(oldBrand.getBrandId(), oldBrand.getBrandName(),
					oldBrand.getBrandOrigin().getEntityName(), oldBrand.getBrandDescription());
		} else {
			throw new BrandNotFound("Brand id " + brand.getBrandId() + Constants.DOES_NOT_EXIST);
		}

	}

	private ProductEntity getBrandOriginEntityIdByName(String brandName) {

		ProductEntity brandOrigin = productEntityRepository.findByEntityNameAndEntityType(brandName.toUpperCase(),
				brandOriginEntityType);

		if (brandOrigin == null) {
			throw new BrandOriginNotFound("Brand Origin ID for " + brandName + Constants.DOES_NOT_EXIST);
		}

		return brandOrigin;
	}

}
