package com.funnysec.richardtang.funnytools.controller;

import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.funnysec.richardtang.funnytools.constant.State;
import com.funnysec.richardtang.funnytools.constant.Type;
import com.funnysec.richardtang.funnytools.entity.Config;
import com.funnysec.richardtang.funnytools.service.IConfigService;
import com.funnysec.richardtang.funnytools.task.ini.DomainDicFuzzIni;
import com.funnysec.richardtang.funnytools.vo.Vo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * 功能配置
 *
 * @author RichardTang
 * @date 2020/03/14
 */
@Validated
@Controller
@Api(tags = "功能配置")
@RequestMapping("config")
public class ConfigController extends BaseController {

    @Autowired
    private IConfigService configService;

    @Autowired
    private DomainDicFuzzIni domainDicFuzzIni;

    @ResponseBody
    @PostMapping("update")
    @ApiOperation("更新配置")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "type", value = "配置类型,域名/端口/路径", required = true, paramType = "query", dataType = "String", example = "0/1/2"),
            @ApiImplicitParam(name = "ini", value = "具体配置参数,需要一个JSON字符对象", required = true, paramType = "query", dataType = "String", example = "{...}")
    })
    public Vo update(@NotNull(message = "类型不能为空") @Min(value = -1, message = "类型不正确") Integer type, @NotBlank(message = "配置不能为空") String ini) {
        Config config = new Config(type, ini);
        boolean isSuccess = configService.saveOrUpdate(config, new QueryWrapper<Config>().eq("type", type));
        if (isSuccess && type == Type.TASK_DOMAIN_DIC_FUZZ) {
            /*
            注意这里不能直接吧newIni赋值给domainDicFuzzIni
            因为@Bean是单例的重新赋值就相当于重新指向了一个对象
            重新指向的话将会操作失败
            */
            DomainDicFuzzIni newIni = JSONUtil.toBean(ini, DomainDicFuzzIni.class);
            domainDicFuzzIni.setDicName(newIni.getDicName());
            domainDicFuzzIni.setThreadSize(newIni.getThreadSize());
        }
        return vo(isSuccess);
    }

    @ResponseBody
    @GetMapping("get")
    @ApiOperation("获取配置")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "type", value = "配置类型,域名/端口/路径", required = true, paramType = "query", dataType = "String", example = "0/1/2"),
    })
    public Vo get(@NotNull(message = "类型不能为空") @Min(value = -1, message = "类型不正确") Integer type) {
        if (type == Type.TASK_DOMAIN_DIC_FUZZ) {
            return vo(State.VO_SUCCESS, JSONUtil.toJsonStr(domainDicFuzzIni));
        }
        return vo(State.VO_SUCCESS);
    }
}
