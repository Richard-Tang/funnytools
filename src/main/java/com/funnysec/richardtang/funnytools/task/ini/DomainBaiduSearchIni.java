package com.funnysec.richardtang.funnytools.task.ini;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 百度搜索
 *
 * @author RichardTang
 * @date 2020/4/10
 */
@Data
@NoArgsConstructor
public class DomainBaiduSearchIni {

    private String baiduSearchApi;

    public DomainBaiduSearchIni(String baiduSearchApi) {
        this.baiduSearchApi = baiduSearchApi;
    }
}
