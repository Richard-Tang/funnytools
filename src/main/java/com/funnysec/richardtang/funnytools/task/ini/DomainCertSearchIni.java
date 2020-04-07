package com.funnysec.richardtang.funnytools.task.ini;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 证书接口接口搜索配置
 *
 * @author RichardTang
 * @date 2020年3月21日20:49:46
 */
@Data
@NoArgsConstructor
public class DomainCertSearchIni {

    /**
     * https://crt.sh/?q=
     */
    private String crtShApi;

    /**
     * https://ctsearch.entrust.com/api/v1/certificates?fields=subjectDN&includeExpired=true&exactMatch=false&limit=5000&domain=
     */
    private String ctSearchApi;

    public DomainCertSearchIni(String crtShApi, String ctSearchApi) {
        this.crtShApi = crtShApi;
        this.ctSearchApi = ctSearchApi;
    }
}
