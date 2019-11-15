package com.supply.handler;

import com.supply.exception.GlobalException;
import com.supply.exception.GlobalExceptionCode;
import com.supply.response.ReturnValueLoader;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.stream.Collectors;


/**
 * 全局异常处理
 * @author liuda
 * @date 2019-06-02 18:26
 * @version v1.0.0
 * @Description
 *
 * Modification History:
 * Date                 Author          Version          Description
---------------------------------------------------------------------------------*
 * 2019-06-02 18:26     liuda          v1.0.0           Created
 *
 */
@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {



    /**
     * 其他的异常拦截处理
     * 不建议放开这些异常拦截，因为这些拦截无法完全掌控，且其中会包含一些其他不确定因素
     * 拦截这些异常会影响到熔断器的使用
     * @param request
     * @param e
     * @return
     * @throws Exception
     */
//    @ExceptionHandler(Exception.class)
//    @ResponseBody
//    public ReturnValueLoader defaultExceptionHandler(HttpServletRequest request, Exception e) throws Exception {
//        if (e instanceof org.springframework.web.servlet.NoHandlerFoundException) { //404处理
//            //全部的404异常
//            return new ReturnValueLoader("404", e.getMessage());
//        } else {
//            return new ReturnValueLoader("500", e.getMessage());
//        }
//
//    }

    /**
     * 注解参数异常处理
     * 将全部的异常参数拼接返回
     * @param request
     * @param e
     * @return
     * @throws Exception
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseBody
    public ReturnValueLoader methodArgumentNotValidExceptionHandler(HttpServletRequest request, MethodArgumentNotValidException e) throws Exception {

        //获取注解参数异常
        MethodArgumentNotValidException exception = (MethodArgumentNotValidException) e;
        //获取全部异常
        BindingResult bindingResult = exception.getBindingResult();
        //将全部异常信息以逗号分割拼接成字符串
        String message = bindingResult.getAllErrors().stream().map(s -> s.getDefaultMessage()).collect(Collectors.joining(","));

        return new ReturnValueLoader(GlobalExceptionCode.METHOD_ARGUMENT_EXCEPTION_CODE.getCode(), message);

    }

    /**
     * 自定义异常处理
     * @param request
     * @param e
     * @return
     * @throws Exception
     */
    @ExceptionHandler(GlobalException.class)
    @ResponseBody
    public ReturnValueLoader customerExceptionHandler(HttpServletRequest request, GlobalException e) throws Exception {
        return new ReturnValueLoader(e.getCode().getCode(), e.getMessage());
    }


}
