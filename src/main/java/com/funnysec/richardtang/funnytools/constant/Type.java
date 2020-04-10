package com.funnysec.richardtang.funnytools.constant;

/**
 * 类型常量
 *
 * @author RichardTang
 * @date 2020/3/16
 */
public class Type {

    /**
     * 不存在
     */
    public static final int TASK_NON_SCAN = -1;

    /**
     * 域名暴力枚举,字典扫描
     */
    public static final int TASK_DOMAIN_DIC_FUZZ = 0;

    /**
     * 通过爬虫在页面中查找域名,包过查找资源文件
     */
    public static final int TASK_DOMAIN_PAGE_SEARCH = 1;

    /**
     * 通过证书站进行域名搜索
     */
    public static final int TASK_DOMAIN_CERT_SEARCH = 2;

    /**
     * 百度搜索引擎搜索
     */
    public static final int TASK_DOMAIN_BAIDU_SEARCH = 3;

}
