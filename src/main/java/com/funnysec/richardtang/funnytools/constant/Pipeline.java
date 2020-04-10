package com.funnysec.richardtang.funnytools.constant;

import com.funnysec.richardtang.funnytools.task.processer.BaiduSearchProcesser;
import com.funnysec.richardtang.funnytools.task.processer.DomainPageSearchProcesser;

/**
 * @author RichardTang
 * @date 2020/4/9
 */
public class Pipeline {

    public static final String BAIDU_SEARCH_PIPELINE_KEY = BaiduSearchProcesser.class.getName();

    public static final String DOMAIN_PAGE_PIPELINE_KEY = DomainPageSearchProcesser.class.getName();
}
