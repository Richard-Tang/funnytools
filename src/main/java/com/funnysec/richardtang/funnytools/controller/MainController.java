package com.funnysec.richardtang.funnytools.controller;

import cn.hutool.core.collection.CollectionUtil;
import com.funnysec.richardtang.funnytools.service.IDomainService;
import com.funnysec.richardtang.funnytools.service.ITaskService;
import com.funnysec.richardtang.funnytools.service.IUserService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import springfox.documentation.annotations.ApiIgnore;

import java.util.Map;

/**
 * 系统框架
 *
 * @author RichardTang
 * @date 2020/03/14
 */
@Controller
@Api(tags = "系统框架")
@RequestMapping("main")
public class MainController extends BaseController {

    @Autowired
    private ITaskService taskService;

    @Autowired
    private IDomainService domainService;

    @Autowired
    private IUserService userService;

    @ApiIgnore
    @GetMapping
    public String page(ModelMap map) {
        Map<String, Integer> count = CollectionUtil.newHashMap(4);
        count.put("task", taskService.count());
        count.put("domain", domainService.count());
        count.put("user", userService.count());
        count.put("wait", taskService.query().eq("state", "0").count());
        map.addAttribute("count", count);
        return "main";
    }
}
