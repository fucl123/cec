package com.supply.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.supply.domain.Company;
import com.supply.domain.SearchDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @author fcl
 * @version v1.0.0
 * @date 2019/10/26
 * @Description
 */
@Mapper
public interface CompanyMapper extends BaseMapper<Company>{

    List<Company> list(Page<Company> page,@Param("searchDto") SearchDto searchDto);

}
