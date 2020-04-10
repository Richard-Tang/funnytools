package com.funnysec.richardtang.funnytools.module.domain.ini;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 域名扫描配置对象
 *
 * @author RichardTang
 * @date 2020/3/17
 */
@Data
@NoArgsConstructor
public class DomainModuleDicFuzzIni {

    private String dicName;

    private Integer threadSize;

    public DomainModuleDicFuzzIni(Integer threadSize) {
        this.threadSize = threadSize;
    }
}
