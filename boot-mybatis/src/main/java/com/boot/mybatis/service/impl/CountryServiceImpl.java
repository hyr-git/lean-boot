package com.boot.mybatis.service.impl;

import com.boot.mybatis.entity.Country;
import com.boot.mybatis.mapper.CountryMapper;
import com.boot.mybatis.service.ICountryService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author jobob
 * @since 2024-03-29
 */
@Service
public class CountryServiceImpl extends ServiceImpl<CountryMapper, Country> implements ICountryService {

}
