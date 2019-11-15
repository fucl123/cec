package com.supply.domain;

import lombok.Data;

/**
 * @author fcl
 * @version v1.0.0
 * @date 2019/10/29
 * @Description
 */
@Data
public class LoginDto {

    private  int id;
    private String type;

    private String token;
}
