package com.funnysec.richardtang.funnytools.controller;

import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import springfox.documentation.annotations.ApiIgnore;

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

    @ApiIgnore
    @GetMapping
    public String page(ModelMap map) {
        map.addAttribute("portCount", 1);
        map.addAttribute("taskCount", 1);
        map.addAttribute("pathCount", 1);
        map.addAttribute("domainCount", 1);
        return "main";
    }
}
