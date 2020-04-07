package com.funnysec.richardtang.funnytools.handler;

import com.funnysec.richardtang.funnytools.constant.VoState;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import javax.validation.ConstraintViolationException;
import java.lang.reflect.Method;

/**
 * 全局异常处理类
 *
 * @author RichardTang
 * @date 2020年3月15日21:32:18
 */
@ControllerAdvice
public class ValidationExceptionHandler {

    /**
     * 参数校验不通过时捕获异常
     * 当Controller上使用了@ResponseBody时该异常处理器返回Json格式数据
     * 当Controller上使用视图的方式返回时,会将错误信息存入域中进行返回
     * 参数校验失败统一返回code:777
     */
    @ExceptionHandler(ConstraintViolationException.class)
    public ModelAndView constraintViolationExceptionHandler(ConstraintViolationException e, HandlerMethod handler) {
        Method method = handler.getMethod();
        // 判断是否使用了@ResponseBody注解,如果使用将以Json方式输出
        boolean isJson = method.isAnnotationPresent(ResponseBody.class);
        if (isJson) {
            ModelAndView mv = new ModelAndView(new MappingJackson2JsonView());
            mv.addObject("code", VoState.PARAM_FAIL);
            mv.addObject("msg", e.getConstraintViolations().iterator().next().getMessage());
            return mv;
        } else {
            ModelAndView mv = new ModelAndView();
            mv.addObject("msg", e.getConstraintViolations().iterator().next().getMessage());
            return mv;
        }
    }
}
