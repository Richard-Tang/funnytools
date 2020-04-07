package com.funnysec.richardtang.funnytools.controller;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.funnysec.richardtang.funnytools.entity.Domain;
import com.funnysec.richardtang.funnytools.service.IDomainService;
import com.funnysec.richardtang.funnytools.vo.Vo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.constraints.NotBlank;

/**
 * 域名管理
 *
 * @author RichardTang
 * @date 2020/03/14
 */
@Validated
@Controller
@Api(tags = "域名管理")
@RequestMapping("domain")
public class DomainController extends BaseController {

    @Autowired
    private IDomainService domainService;

    @GetMapping
    @ApiIgnore
    public String page() {
        return "domain";
    }

    @ResponseBody
    @GetMapping("list")
    @ApiOperation("域名列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", value = "当前页", paramType = "query", dataType = "Integer", defaultValue = "1"),
            @ApiImplicitParam(name = "limit", value = "每页显示条数", paramType = "query", dataType = "Integer", defaultValue = "20"),
            @ApiImplicitParam(name = "domain", value = "搜索参数-域名", paramType = "query", dataType = "String")
    })
    public Vo list(@RequestParam(defaultValue = "1") Integer page, @RequestParam(defaultValue = "20") Integer limit, String domain) {
        IPage<Domain> pageResult = domainService.query()
                .likeLeft(StrUtil.isNotEmpty(domain), "domain", domain)
                .orderByAsc("create_time")
                .page(new Page<Domain>(page, limit));
        return vo(pageResult.getTotal(), pageResult.getRecords());
    }

    @ResponseBody
    @PostMapping("add")
    @ApiOperation("添加域名")
    @ApiImplicitParam(name = "domain", value = "域名", required = true, paramType = "query", dataType = "String", example = "xxx.xxx")
    public Vo add(@NotBlank(message = "域名不能为空") @com.funnysec.richardtang.funnytools.annotation.Domain String domain) {
        Domain isExistDomain = domainService.query().eq("domain", domain).one();
        if (ObjectUtil.isNotNull(isExistDomain)) {
            return f();
        } else {
            return vo(domainService.insert(domain));
        }
    }

    @ResponseBody
    @PostMapping("del")
    @ApiOperation("删除域名")
    @ApiImplicitParam(name = "id", value = "域名ID", paramType = "query", dataType = "String", required = true)
    public Vo del(@NotBlank(message = "ID不能为空") String id) {
        return vo(domainService.removeById(id));
    }
}
