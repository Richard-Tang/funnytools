package com.funnysec.richardtang.funnytools.task.ini;

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
public class DomainDicFuzzIni {

    private Integer threadSize;

    private String dicName;

    public DomainDicFuzzIni(Integer threadSize) {
        this.threadSize = threadSize;
    }
}
