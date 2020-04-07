package com.funnysec.richardtang.funnytools.controller;

import cn.hutool.crypto.SecureUtil;
import com.funnysec.richardtang.funnytools.entity.User;
import com.funnysec.richardtang.funnytools.service.IUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpSession;
import javax.validation.constraints.NotBlank;

/**
 * 登录
 *
 * @author RichardTang
 * @date 2020/03/14
 */
@Controller
@RequestMapping("login")
@Api(tags = "登录")
public class LoginController extends BaseController {

    @Autowired
    private IUserService userService;

    @GetMapping
    @ApiIgnore
    public String page() {
        return "login";
    }

    @PostMapping
    @ApiOperation("用户登录")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "username", value = "用户名", paramType = "query", dataType = "String", required = true),
            @ApiImplicitParam(name = "password", value = "密码", paramType = "query", dataType = "String", required = true),
            @ApiImplicitParam(name = "code", value = "验证码", paramType = "query", dataType = "String", required = true)
    })
    public ModelAndView login(@NotBlank(message = "密码不能为空") String username, @NotBlank(message = "密码不能为空") String password,
                              @NotBlank(message = "验证码不能为空") String code, @ApiIgnore HttpSession session, @ApiIgnore ModelAndView map) {
        String captchaCode = (String) session.getAttribute("captchaCode");
        if (!captchaCode.equals(code)) {
            map.addObject("msg", "验证码错误!");
        } else {
            User user = userService.query().eq("username", username).one();
            if (user != null && user.getPassword().equals(SecureUtil.md5(password))) {
                session.setAttribute("user", user);
                map.setViewName("redirect:index");
                return map;
            }
            map.addObject("msg", "用户名或者密码错误!");
        }
        map.setViewName("login");
        return map;
    }
}
