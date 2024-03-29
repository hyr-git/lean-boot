package com.boot.mybatis.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.boot.mybatis.mapper.CityMapper;
import com.boot.mybatis.entity.City;
import com.boot.mybatis.service.ICityService;
import org.springframework.stereotype.Service;

@Service
public class CityServiceImpl extends ServiceImpl<CityMapper,City> implements ICityService {

}
