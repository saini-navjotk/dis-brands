package com.tcs.eas.rest.apis.db;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tcs.eas.rest.apis.model.Brand;

public interface BrandRepository extends JpaRepository<Brand, Integer> {

}
