package com.tcs.eas.rest.apis.db;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tcs.eas.rest.apis.model.Brand;
import com.tcs.eas.rest.apis.model.ProductBrand;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class BrandDaoService {

	@Autowired
	private BrandRepository brandRepository;

	public List<ProductBrand> addBrands(List<ProductBrand> productBrands/* , MultipartFile brandLogo */) {

		/*
		 * try { brands.get(0).setBrandLogo(brandLogo.getBytes()); } catch (IOException
		 * e) { e.printStackTrace(); }
		 */

		List<Brand> brands = productBrands.stream()
				.map(b -> new Brand(b.getBrandName(), b.getBrandOrigin(), b.getBrandDescription(), b.getCreatedBy()))
				.collect(Collectors.toList());

		brands = brandRepository.saveAll(brands);

		return productBrands = brands.stream().map(b -> new ProductBrand(b.getBrandId(), b.getBrandName(),
				b.getBrandOrigin(), b.getBrandDescription(), b.getCreatedBy())).collect(Collectors.toList());
	}

	public List<ProductBrand> getAllBrands() {

		List<Brand> brands = brandRepository.findAll();

		List<ProductBrand> productBrands = brands.stream().map(b -> new ProductBrand(b.getBrandId(), b.getBrandName(),
				b.getBrandOrigin(), b.getBrandDescription(), b.getCreatedBy())).collect(Collectors.toList());

		return productBrands;
	}

	public ProductBrand getBrandById(int id) {
		Optional<Brand> optionalBrand = brandRepository.findById(id);
		Brand brand = null;
		ProductBrand productBrand = null;
		if (optionalBrand.isPresent()) {
			brand = optionalBrand.get();
			productBrand = new ProductBrand();
			productBrand.setBrandId(brand.getBrandId());
			productBrand.setBrandName(brand.getBrandName());
			productBrand.setBrandDescription(brand.getBrandDescription());
			productBrand.setBrandOrigin(brand.getBrandOrigin());
			productBrand.setCreatedBy(brand.getCreatedBy());
		}

		return productBrand;
	}

	public String deleteBrandById(Integer id) {

		Optional<Brand> optionalBrand = brandRepository.findById(id);

		if (optionalBrand.isPresent()) {
			brandRepository.delete(optionalBrand.get());

			return "Brand Deleted Successfully !";
		}
		return "Brand Not Found !";
	}

	public ProductBrand updateBrandById(ProductBrand brand) {

		Optional<Brand> optionalBrand = brandRepository.findById(brand.getBrandId());

		if (optionalBrand.isPresent()) {
			Brand oldBrand = optionalBrand.get();
			oldBrand.setBrandId(brand.getBrandId());
			oldBrand.setBrandName(brand.getBrandName());
			oldBrand.setBrandOrigin(brand.getBrandOrigin());
			oldBrand.setBrandDescription(brand.getBrandDescription());

			oldBrand = brandRepository.save(oldBrand);

			return new ProductBrand(oldBrand.getBrandId(), oldBrand.getBrandName(), oldBrand.getBrandOrigin(),
					oldBrand.getBrandDescription(), oldBrand.getCreatedBy());
		}

		return new ProductBrand();
	}
}
