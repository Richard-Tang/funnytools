package com.funnysec.richardtang.funnytools.controller;

import cn.hutool.core.util.StrUtil;
import com.funnysec.richardtang.funnytools.entity.System;
import com.funnysec.richardtang.funnytools.service.ISystemService;
import com.funnysec.richardtang.funnytools.vo.Vo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.constraints.NotEmpty;
import java.util.Map;

/**
 * 系统配置
 *
 * @author RichardTang
 * @date 2020/04/07
 */
@Validated
@Controller
@Api(tags = "系统配置")
@RequestMapping(value = "system")
public class SystemController extends BaseController {

    @Autowired
    private ISystemService systemService;

    @Autowired
    private System system;

    @ApiIgnore
    @GetMapping
    public String page() {
        return "system";
    }

    @ResponseBody
    @GetMapping("menu")
    @ApiOperation("获取系统菜单接口")
    public String menu() {
        return system.getMenu();
    }

    @PostMapping
    @ResponseBody
    @ApiOperation("更新系统配置")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "characteristics", value = "系统特色", paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "title", value = "系统标题", paramType = "query", dataType = "String", required = true),
            @ApiImplicitParam(name = "version", value = "系统版本", paramType = "query", dataType = "String", required = true),
            @ApiImplicitParam(name = "menu", value = "系统菜单", paramType = "query", dataType = "String", required = true)
    })
    public Vo update(String characteristics,
                     @NotEmpty(message = "系统名称不能为空") String title,
                     @NotEmpty(message = "系统版本不能为空") String version,
                     @NotEmpty(message = "系统菜单不能为空") String menu) {
        system.setTitle(title);
        system.setVersion(version);
        system.setCharacteristics(characteristics);
        system.setMenu(StrUtil.cleanBlank(menu).trim());
        boolean flag = systemService.saveOrUpdate(system);
        return vo(flag);
    }
}
