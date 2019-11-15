package com.supply.domain;

import lombok.Data;

/**
 * @author fcl
 * @version v1.0.0
 * @date 2019/10/30
 * @Description
 */
@Data
public class ReceiveDto {
    private String companyCode;

    private String xmlId;

    private String status;

    private String data;
}
