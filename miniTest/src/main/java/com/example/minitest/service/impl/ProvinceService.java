package com.example.minitest.service.impl;

import com.example.minitest.model.Province;
import com.example.minitest.repository.IProvinceRepository;
import com.example.minitest.service.IProvinceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public class ProvinceService implements IProvinceService {

    @Autowired
    private IProvinceRepository iProvinceRepository;

    @Override
    public List<Province> findAll() {
        return iProvinceRepository.findAll();
    }

    @Override
    public Optional<Province> findById(Long id) {
        return iProvinceRepository.findById(id);
    }

    @Override
    public Province save(Province province) {
        return iProvinceRepository.save(province);
    }

    @Override
    public void delete(Long id) {
        iProvinceRepository.deleteById(id);
    }

//    @Override
//    public List<Province> findBySearch( String name) {
//        return iProvinceRepository.findAllName("%" + name + "%");
//    }

    @Override
    public Page<Province> findAllByNameContaining(String name, Pageable pageable) {
        return iProvinceRepository.findAllByNameContaining(name, pageable);
    }

    @Override
    public Page<Province> findAll(Pageable pageable) {
        return iProvinceRepository.findAll(pageable);
    }
}
