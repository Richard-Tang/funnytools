package com.funnysec.richardtang.funnytools.controller;

import cn.hutool.core.lang.UUID;
import cn.hutool.core.util.StrUtil;
import com.funnysec.richardtang.funnytools.constant.Character;
import com.funnysec.richardtang.funnytools.constant.Suffix;
import com.funnysec.richardtang.funnytools.constant.VoState;
import com.funnysec.richardtang.funnytools.vo.Vo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

/**
 * 文件上传
 *
 * @author RichardTang
 * @date 2020/03/14
 */
@Api(tags = "文件上传")
@Controller
@RequestMapping("upload")
public class UploadController extends BaseController {

    @Value("${project.file-base-path}")
    private String fileSavePath;

    @PostMapping
    @ResponseBody
    @ApiOperation("接收文件上传")
    @ApiImplicitParam(name = "file", value = "文件", paramType = "query", dataType = "org.springframework.web.multipart.MultipartFile", required = true)
    public Vo upload(MultipartFile file) {

        String fileName = file.getOriginalFilename();

        // 文件为空,文件名空！
        if (file.isEmpty() || StrUtil.isEmpty(fileName)) {
            return vo(VoState.NOT_FOUND);
        }

        String suffix = fileName.substring(fileName.lastIndexOf(Character.POINTER) + 1);

        if (!Suffix.TXT.equals(suffix)) {
            return vo(VoState.UNQUALIFIED);
        }

        try {
            fileName = UUID.randomUUID() + Character.POINTER + Suffix.TXT;
            file.transferTo(new File(fileSavePath + fileName));
            return new Vo(VoState.SUCCESS, fileName);
        } catch (IOException e) {
            return f();
        }
    }
}
