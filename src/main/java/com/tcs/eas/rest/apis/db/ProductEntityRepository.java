package com.tcs.eas.rest.apis.db;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tcs.eas.rest.apis.model.ProductEntity;

public interface ProductEntityRepository extends JpaRepository<ProductEntity, Integer> {

	ProductEntity findByEntityNameAndEntityType(String brandName, String brandOriginEntityType);

}
