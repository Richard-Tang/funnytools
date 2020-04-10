package com.funnysec.richardtang.funnytools.constant;

/**
 * 状态常量
 *
 * @author RichardTang
 * @date 2020/3/17
 */
public class State {

    // ↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓ Task ↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓

    /**
     * 等待中
     */
    public static final int TASK_WAIT = 0;

    /**
     * 进行中
     */
    public static final int TASK_ING = 1;

    /**
     * 已完成
     */
    public static final int TASK_COMPLETE = 2;

    /**
     * 泛域名
     */
    public static final int TASK_PAN_DOMAIN = 3;

    // ↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓ Vo ↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓

    /**
     * 成功-正常处理
     */
    public static final Long VO_SUCCESS = 200L;

    /**
     * 失败-发生异常-数据库执行结果为错误
     */
    public static final Long VO_FAIL = 500L;

    /**
     * 重定向-条件不符合-校验不通过-手动校验
     */
    public static final Long VO_UNQUALIFIED = 302L;

    /**
     * 找不到资源-文件为空
     */
    public static final Long VO_NOT_FOUND = 404L;

    /**
     * 参数校验不通过-hibernate validator 校验
     */
    public static final Long VO_PARAM_FAIL = 777L;
}
