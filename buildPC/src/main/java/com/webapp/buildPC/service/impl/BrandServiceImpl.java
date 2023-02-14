package com.webapp.buildPC.service.impl;

import com.webapp.buildPC.dao.BrandMapper;
import com.webapp.buildPC.domain.Brand;
import com.webapp.buildPC.service.interf.BrandService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class BrandServiceImpl implements BrandService {

    private final BrandMapper brandMapper;

    @Override
    public List<Brand> getAllBrands() {
        log.debug("The list of all brands");
        return brandMapper.getAllBrand();

    }
}
