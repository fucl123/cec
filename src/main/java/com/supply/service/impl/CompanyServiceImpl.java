package com.supply.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.supply.domain.Company;
import com.supply.domain.SearchDto;
import com.supply.mapper.CompanyMapper;
import com.supply.response.ResultCode;
import com.supply.response.ReturnValueLoader;
import com.supply.service.CompanyService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author fcl
 * @version v1.0.0
 * @date 2019/10/26
 * @Description
 */
@Service
public class CompanyServiceImpl extends ServiceImpl<CompanyMapper, Company> implements CompanyService {

    @Override
    public ReturnValueLoader list(SearchDto searchDto) {
        Page<Company> page = new Page(searchDto.getPageNumber(),searchDto.getPageSize());
        List<Company> list = baseMapper.list(page, searchDto);
        page.setRecords(list);
        return new ReturnValueLoader(page);
    }
}
