package com.example.minitest.service;

import com.example.minitest.common.ICRUDService;
import com.example.minitest.model.Province;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface IProvinceService extends ICRUDService<Province> {
Page<Province> findAllByNameContaining(String name_province, Pageable pageable);


Page<Province> findAll(Pageable pageable);

}
