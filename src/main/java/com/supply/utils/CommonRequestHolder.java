package com.supply.utils;


import com.supply.exception.BaseParamException;
import com.supply.exception.GlobalExceptionCode;

import java.time.LocalDateTime;

/**
 * 通用参数
 *
 * @author liuda
 * @version v1.0.0
 * @date 2019-07-16 16:04
 * @Description Modification History:
 * Date                 Author          Version          Description
 * ---------------------------------------------------------------------------------*
 * 2019-07-16 16:04     liuda          v1.0.0           Created
 */
public class CommonRequestHolder {


    /**
     * 当前线程是否进行过初始化
     */
    private static final ThreadLocal<Boolean> isInit = new ThreadLocal<>();

    /**
     * 记录当前线程操作用户主键 ID
     */
    public static final ThreadLocal<Long> currentUserId = new ThreadLocal<>();

    /**
     * 记录当前线程操作用户名称
     */
    public static final ThreadLocal<String> currentUserName = new ThreadLocal<>();

    /**
     * 记录当前线程操作用户类型
     */
    public static final ThreadLocal<String> currentUserType = new ThreadLocal<>();


    /**
     * 记录当前线程的统一时间错
     */
    public static final ThreadLocal<LocalDateTime> currentTimestamp = new ThreadLocal<>();



    /**
     * 初始化数据
     * @param userId
     * @param userName
     * @param userType
     */
    public static void init(Long userId, String userName, String userType) {
        currentUserId.set(userId);
        currentUserName.set(userName);
        currentUserType.set(userType);
        currentTimestamp.set(LocalDateTime.now());
        isInit.set(true);
    }


    public static void isInit() {
        Boolean bool =isInit.get();

        if (bool == null || !bool) {
            throw new BaseParamException("当前线程基础数据未进行初始化", GlobalExceptionCode.METHOD_ARGUMENT_EXCEPTION_CODE);
        }
    }

    /**
     * 获取当前用户ID
     * @return
     */
    public static Long getCurrentUserId() {
        Long userId = currentUserId.get();

        isInit();

        return userId;
    }



    /**
     * 获取当前统一时间戳
     * @return
     */
    public static LocalDateTime getCurrentLocalDateTime() {

        LocalDateTime now = currentTimestamp.get();

        isInit();

        return now;
    }

    /**
     * 获取当前登陆用户名称
     * @return
     */
    public static String getCurrentUserName() {

        String userName = currentUserName.get();

        isInit();

        return userName;
    }

    /**
     * 获取当前用户类型
     * @return
     */
    public static String getCurrentUserType() {

        String userType = currentUserType.get();

        isInit();

        return userType;
    }
    /**
     * 关闭
     */
    public static void close() {
        isInit.remove();
        currentUserName.remove();
        currentUserType.remove();
        currentUserId.remove();
        currentTimestamp.remove();
    }


}
