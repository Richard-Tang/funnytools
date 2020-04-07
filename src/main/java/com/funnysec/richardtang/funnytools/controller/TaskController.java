package com.funnysec.richardtang.funnytools.controller;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.funnysec.richardtang.funnytools.annotation.IpOrDomain;
import com.funnysec.richardtang.funnytools.constant.TaskState;
import com.funnysec.richardtang.funnytools.constant.VoState;
import com.funnysec.richardtang.funnytools.entity.Task;
import com.funnysec.richardtang.funnytools.service.ITaskService;
import com.funnysec.richardtang.funnytools.vo.Vo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.constraints.*;
import java.lang.annotation.Target;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 任务管理
 *
 * @author RichardTang
 * @date 2020/03/14
 */
@Validated
@Controller
@Api(tags = "任务管理")
@RequestMapping("task")
public class TaskController extends BaseController {

    @Autowired
    private ITaskService taskService;

    @ApiIgnore
    @GetMapping
    public String page() {
        return "task";
    }

    @ResponseBody
    @GetMapping("list")
    @ApiOperation("任务列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", value = "当前页", paramType = "query", dataType = "Integer", defaultValue = "1"),
            @ApiImplicitParam(name = "limit", value = "每页显示条数", paramType = "query", dataType = "Integer", defaultValue = "20"),
            @ApiImplicitParam(name = "target", value = "搜索参数-目标", paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "state", value = "搜索参数-状态", paramType = "query", dataType = "Integer"),
            @ApiImplicitParam(name = "type", value = "搜索参数-类型", paramType = "query", dataType = "Integer")
    })
    public Vo list(@RequestParam(defaultValue = "1") Integer page, @RequestParam(defaultValue = "20") Integer limit,
                   String target, Integer state, Integer type) {
        IPage<Task> pageResult = taskService.query()
                .eq(StrUtil.isNotEmpty(target), "target", target)
                .eq(ObjectUtil.isNotNull(state), "state", state)
                .eq(ObjectUtil.isNotNull(type), "type", type)
                .page(new Page<Task>(page, limit));

        return vo(pageResult.getTotal(), pageResult.getRecords());
    }

    @ResponseBody
    @PostMapping("add")
    @ApiOperation("添加任务")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "target", value = "要进行任务的IP或者域名,目标可以是多个", paramType = "query", dataType = "List", required = true),
            @ApiImplicitParam(name = "type", value = "要进行的模块", paramType = "query", dataType = "Integer", required = true)
    })
    public Vo add(@NotNull(message = "模块不能为空") @Min(value = 0, message = "该模块不存在") @Max(value = 2, message = "该模块不存在") Integer type,
                  @Size(message = "目标不能为空") @IpOrDomain @RequestParam("target[]") List<String> target) {
        return vo(taskService.saveBatchTask(target, type));
    }

    @ResponseBody
    @PostMapping("del")
    @ApiOperation("删除任务")
    @ApiImplicitParam(name = "id", value = "任务ID", paramType = "query", dataType = "Integer", required = true)
    public Vo del(@NotNull(message = "ID不能为空") @Min(value = 0, message = "id范围不正确") Integer id) {
        Task task = taskService.getById(id);
        if (task.getState().equals(TaskState.ING)) {
            return vo(VoState.UNQUALIFIED);
        }
        return vo(taskService.removeById(id));
    }
}
