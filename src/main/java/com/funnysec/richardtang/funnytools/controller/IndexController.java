package com.funnysec.richardtang.funnytools.controller;

import io.swagger.annotations.Api;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import springfox.documentation.annotations.ApiIgnore;

/**
 * 系统首页
 *
 * @author RichardTang
 * @date 2020/03/14
 */
@Controller
@Api(tags = "系统首页")
@RequestMapping(value = {"", "index"})
public class IndexController extends BaseController {

    @ApiIgnore
    @GetMapping
    public String page() {
        return "index";
    }
}
