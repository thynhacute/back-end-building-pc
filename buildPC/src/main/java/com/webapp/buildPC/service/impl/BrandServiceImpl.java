package com.webapp.buildPC.service.impl;

import com.webapp.buildPC.dao.BrandMapper;
import com.webapp.buildPC.domain.Brand;
import com.webapp.buildPC.service.interf.BrandService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class BrandServiceImpl implements BrandService {

    private final BrandMapper brandMapper;

    private static final String HASH_KEY = "BRAND";
    private final RedisTemplate redisTemplate;

    @Override
    public List<Brand> getAllBrands() {
        log.debug("The list of all brands");
        return brandMapper.getAllBrand();
    }

    @Override
    public boolean saveBrand(Brand brand) {
        String brandID = String.valueOf(brand.getBrandID());
        try {
            redisTemplate.opsForHash().put(HASH_KEY, brandID, brand);
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public List<Brand> fetchAllBrand() {
        List<Brand> brands;
        brands = redisTemplate.opsForHash().values(HASH_KEY);
        return brands;
    }

    @Override
    public Brand findBrandByID(int brandid) {
        log.debug("find brand by id brand");
        return brandMapper.findBrandByID(brandid);
    }

    public Object fetchBrandById(int brandID) {
        String id = String.valueOf(brandID);
        Object brand = redisTemplate.opsForHash().get(HASH_KEY, id);
        return brand;
    }

    @Override
    public boolean deleteBrandRedis(int brandID) {
        String id = String.valueOf(brandID);
        try {
            redisTemplate.opsForHash().delete(HASH_KEY, id);
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }
}
