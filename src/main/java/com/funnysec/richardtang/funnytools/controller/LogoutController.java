package com.funnysec.richardtang.funnytools.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpSession;

/**
 * 注销
 *
 * @author RichardTang
 * @date 2020/03/14
 */
@Controller
@Api(tags = "注销")
public class LogoutController extends BaseController {

    @ApiOperation("用户注销")
    @RequestMapping("logout")
    public String logout(@ApiIgnore HttpSession session) {
        session.removeAttribute("user");
        return "login";
    }
}
