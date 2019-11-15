package com.supply.domain;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.Date;

@Data
@TableName("tb_company")
public class Company extends Model<Company>{
    @ApiModelProperty(value = "id")
    private Integer id;
    @ApiModelProperty(value = "统一社会信用代码")
    @NotBlank
    private String uscCode;
    @ApiModelProperty(value = "组织机构代码")
    @NotBlank
    private String orgCode;
    @ApiModelProperty(value = "企业名称")
    @NotBlank
    private String companyName;
    @ApiModelProperty(value = "企业编码")
    @NotBlank
    private String companyCode;
    @ApiModelProperty(value = "法人姓名")
    @NotBlank
    private String legalCode;
    @ApiModelProperty(value = "联系电话")
    @NotBlank
    private String contactTel;
    @ApiModelProperty(value = "密码")
    @NotBlank
    private String password;
    @ApiModelProperty(value = "mq账号")
    private String mqUsername;
    @ApiModelProperty(value = "mq密码")
    private String mqPassword;
    @ApiModelProperty(value = "mq队列")
    private String mqSendQueue;
    @ApiModelProperty(value = "mq回执队列")
    private String mqReturnQueue;
    @ApiModelProperty(value = "mq地址")
    private String mqAddress;
    @ApiModelProperty(value = "mq虚拟主机")
    private String mqVirtualHost;
    @ApiModelProperty(value = "公钥")
    private  String publicKey;

    @ApiModelProperty(value = "私钥")
    private  String privateKey;

    @ApiModelProperty(value = "用户类型")
    @NotBlank
    private String type;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss" ,timezone = "GMT+8")
    private Date createTime;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss" ,timezone = "GMT+8")
    private Date updateTime;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }
}