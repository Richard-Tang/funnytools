package com.funnysec.richardtang.funnytools.controller;

import com.funnysec.richardtang.funnytools.constant.VoState;
import com.funnysec.richardtang.funnytools.vo.Vo;

/**
 * Controller 基类
 *
 * @author RichardTang
 * @date 2020/03/14
 */
public class BaseController {

    Vo s() {
        return vo(VoState.SUCCESS);
    }

    Vo f() {
        return vo(VoState.FAIL);
    }

    Vo vo(Long code) {
        return new Vo(code);
    }

    Vo vo(boolean isSuccess) {
        return isSuccess ? s() : f();
    }

    Vo vo(Long count, Object data) {
        return new Vo(count, data);
    }
}
