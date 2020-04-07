package com.funnysec.richardtang.funnytools.controller;

import com.funnysec.richardtang.funnytools.annotation.Phone;
import com.funnysec.richardtang.funnytools.entity.User;
import com.funnysec.richardtang.funnytools.service.impl.UserServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpSession;
import javax.validation.constraints.Email;

/**
 * 账号资料
 *
 * @author RichardTang
 * @date 2020/03/14
 */
@Controller
@Validated
@RequestMapping("userinfo")
@Api(tags = "账号资料")
public class UserInfoController extends BaseController {

    @Autowired
    private UserServiceImpl userService;

    @GetMapping
    @ApiIgnore
    public String page() {
        return "userinfo";
    }

    @PostMapping
    @ApiOperation("更新账号资料")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "email", value = "邮箱", paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "phone", value = "手机号", paramType = "query", dataType = "String")
    })
    public ModelAndView update(@Email(message = "邮箱格式不正确") String email, @Phone String phone,
                               @ApiIgnore HttpSession session, @ApiIgnore ModelAndView modelAndView) {
        User user = (User) session.getAttribute("user");
        user.setEmail(email);
        user.setPhone(phone);
        if (userService.updateById(user)) {
            modelAndView.addObject("msg", "修改成功");
        } else {
            modelAndView.addObject("msg", "修改失败");
        }
        modelAndView.setViewName("userinfo");
        return modelAndView;
    }
}
