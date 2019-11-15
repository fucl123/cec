package com.supply.exception;


/**
 * 基础参数异常
 * @author fcl
 * @date 2019-07-25 10:34
 * @version v1.0.0
 *
 */
public class BaseParamException extends GlobalException {


    public BaseParamException(String message) {
        super(message);
    }

    public BaseParamException(String message, GlobalExceptionCode code) {
        super(message, code);
    }


}
