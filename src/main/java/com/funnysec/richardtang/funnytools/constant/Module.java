package com.funnysec.richardtang.funnytools.constant;

/**
 * 类型常量
 *
 * @author RichardTang
 * @date 2020/3/16
 */
public class Module {

    /**
     * 不存在
     */
    public static final int NON_SCAN = -1;

    /**
     * 域名暴力枚举,字典扫描
     */
    public static final int DIC_FUZZ = 0;

    /**
     * 通过爬虫在页面中查找域名,包过查找资源文件
     */
    public static final int PAGE_SEARCH = 1;

    /**
     * CertSh证书站搜索
     */
    public static final int CRT_SH_SEARCH = 2;

    /**
     * 百度搜索引擎搜索
     */
    public static final int BAIDU_SEARCH = 3;

    /**
     * Ct证书站搜索
     */
    public static final int CT_SEARCH = 4;

    /**
     * 必应搜索引擎搜索
     */
    public static final int BING_SEARCH = 5;

    /**
     * 搜狗搜索引擎搜索
     */
    public static final int SOGOU_SEARCH = 6;
}
