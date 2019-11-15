package com.supply.controller;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.supply.domain.Company;
import com.supply.domain.LoginDto;
import com.supply.domain.SearchDto;
import com.supply.response.ResultCode;
import com.supply.response.ReturnValueLoader;
import com.supply.service.CompanyService;
import com.supply.utils.JWTUtil;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * @author fcl
 * @version v1.0.0
 * @date 2019/10/26
 * @Description
 */
@RestController
@RequestMapping("company")
public class CompanyController {
    @Autowired
    CompanyService companyService;

    @PostMapping("register")
    @ApiOperation(value = "创建用户接口", notes = "创建用户接口详细描述")
    public ReturnValueLoader register(@RequestBody @Valid Company company){

        companyService.insert(company);
        return new ReturnValueLoader(ResultCode.SUCCESS.getCode(),ResultCode.SUCCESS.getMsg());
    }

    @ApiOperation(value = "企业列表查询接口", notes = "企业列表查询")
    @PostMapping("list")
    public ReturnValueLoader list(@RequestBody(required = false) @Valid  SearchDto searchDto) {

        return companyService.list(searchDto);

    }

    @PostMapping("update")
    @ApiOperation(value = "修改用户接口", notes = "修改用户接口详细描述")
    public ReturnValueLoader update(@RequestBody @Valid Company company){

        companyService.updateById(company);
        return new ReturnValueLoader(ResultCode.SUCCESS.getCode(),ResultCode.SUCCESS.getMsg());
    }

    @PostMapping("login")
    @ApiOperation(value = "用户登入接口", notes = "用户登入接口详细描述")
    public ReturnValueLoader login(@RequestParam("orgCode")String orgCode ,@RequestParam("password")String password ){

        EntityWrapper<Company> entityWrapper=new EntityWrapper<>();
        entityWrapper.eq("org_code",orgCode);
        entityWrapper.eq("password",password);
       Company company =companyService.selectOne(entityWrapper);
        LoginDto loginDto=new LoginDto();
       if (company!=null)
       {

         String token= JWTUtil.getInstance().generateToken(company.getId()+"",company.getOrgCode(),company.getType(),60,"signService");
           loginDto.setType(company.getType());
           loginDto.setToken(token);
           loginDto.setId(company.getId());
           return new ReturnValueLoader(ResultCode.SUCCESS.getCode(),ResultCode.SUCCESS.getMsg(),loginDto);
       }
        return new ReturnValueLoader(ResultCode.USERNAME_OR_PASSWORD_ERROR.getCode(),ResultCode.USERNAME_OR_PASSWORD_ERROR.getMsg());
    }

    @GetMapping("detail")
    @ApiOperation(value = "查看用户明细接口", notes = "查看用户明细接口详细描述")
    public ReturnValueLoader login(@RequestParam("id")int id ){

        Company company =companyService.selectById(id);
        return new ReturnValueLoader(ResultCode.SUCCESS.getCode(),ResultCode.SUCCESS.getMsg(),company);
    }
 }
