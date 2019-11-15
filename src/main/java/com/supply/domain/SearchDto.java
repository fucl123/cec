package com.supply.domain;

import com.baomidou.mybatisplus.plugins.Page;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * @author fcl
 * @version v1.0.0
 * @date 2019/10/26
 * @Description
 */
@Data
@ApiModel(value="SearchDto",description="查询dto")
public class SearchDto {

    /**
     * 企业中文名称
     */
    @ApiModelProperty("企业中文名称")
    private String companyName;

    /**
     *统一社会信用代码
     */
    @ApiModelProperty("统一社会信用代码")
    private String uscCode;

    /**
     * 搜索开始时间
     */
    @ApiModelProperty(value = "搜索开始时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date gtAddDate;

    /**
     * 搜索结束时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date ltAddDate;

    /**
     * 页码
     */
    @ApiModelProperty("页码")
    private Integer pageNumber =1;

    /**
     * 每页显示数量
     */
    @ApiModelProperty("每页显示数量")
    private Integer pageSize=10;
}
