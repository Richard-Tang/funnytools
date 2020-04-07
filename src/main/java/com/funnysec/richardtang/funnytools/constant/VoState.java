package com.funnysec.richardtang.funnytools.constant;

/**
 * vo状态码
 *
 * @author RichardTang
 * @date 2020/3/17
 */
public class VoState {

    /**
     * 成功-正常处理
     */
    public static final Long SUCCESS = 200L;

    /**
     * 失败-发生异常-数据库执行结果为错误
     */
    public static final Long FAIL = 500L;

    /**
     * 重定向-条件不符合-校验不通过-手动校验
     */
    public static final Long UNQUALIFIED = 302L;

    /**
     * 找不到资源-文件为空
     */
    public static final Long NOT_FOUND = 404L;

    /**
     * 参数校验不通过-hibernate validator 校验
     */
    public static final Long  PARAM_FAIL = 777L;
}
