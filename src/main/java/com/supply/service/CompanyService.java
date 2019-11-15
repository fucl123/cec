package com.supply.service;

import com.baomidou.mybatisplus.service.IService;
import com.supply.domain.Company;
import com.supply.domain.SearchDto;
import com.supply.response.ReturnValueLoader;

import javax.validation.Valid;

/**
 * @author fcl
 * @version v1.0.0
 * @date 2019/10/26
 * @Description
 */
public interface CompanyService extends IService<Company>{

    ReturnValueLoader list(SearchDto searchDto);
}
