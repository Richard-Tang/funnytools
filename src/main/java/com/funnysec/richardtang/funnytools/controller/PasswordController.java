package com.funnysec.richardtang.funnytools.controller;

import cn.hutool.crypto.SecureUtil;
import com.funnysec.richardtang.funnytools.constant.State;
import com.funnysec.richardtang.funnytools.entity.User;
import com.funnysec.richardtang.funnytools.service.IUserService;
import com.funnysec.richardtang.funnytools.vo.Vo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpSession;
import javax.validation.constraints.NotBlank;

/**
 * 修改密码
 *
 * @author RichardTang
 * @date 2020/03/14
 */
@Validated
@Controller
@Api(tags = "修改密码")
@RequestMapping("password")
public class PasswordController extends BaseController {

    @Autowired
    private IUserService userService;

    @ApiIgnore
    @GetMapping
    public String page() {
        return "password";
    }

    @ResponseBody
    @PostMapping
    @ApiOperation("修改密码接口")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "oldPassword", value = "旧密码", paramType = "query", dataType = "String", required = true),
            @ApiImplicitParam(name = "newPassword", value = "新密码", paramType = "query", dataType = "String", required = true)
    })
    public Vo edit(@NotBlank(message = "旧密码不能为空") String oldPassword, @NotBlank(message = "新密码不能为空") String newPassword,
                   @ApiIgnore HttpSession session, Model model) {
        User user = (User) session.getAttribute("user");
        if (SecureUtil.md5(oldPassword).equals(user.getPassword())) {
            user.setPassword(SecureUtil.md5(newPassword));
            return vo(userService.updateById(user));
        } else {
            return vo(State.VO_UNQUALIFIED);
        }
    }
}
