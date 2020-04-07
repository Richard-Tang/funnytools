package com.funnysec.richardtang.funnytools.vo;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author RichardTang
 * @date 2020/3/17
 */
@Data
@NoArgsConstructor
public class Vo<T> {

    private String msg = "";

    private Long code = 0L;

    private Long count;

    private T data;

    public Vo(Long code) {
        this.code = code;
    }

    public Vo(Long code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public Vo(Long count, T data) {
        this.count = count;
        this.data = data;
    }

    public Vo(Long code, Long count, T data) {
        this.code = code;
        this.count = count;
        this.data = data;
    }
}
