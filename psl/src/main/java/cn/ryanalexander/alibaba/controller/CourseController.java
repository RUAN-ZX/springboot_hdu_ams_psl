package cn.ryanalexander.alibaba.controller;

import cn.ryanalexander.alibaba.service.AccountService;
import cn.ryanalexander.alibaba.service.CourseService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p><b></b></p>
 *
 * <p>2022/3/24 </p>
 *
 * @author ryan 2022/3/24 19:28
 * @since 1.0.0
 **/
@RestController
public class CourseController {
    @Resource
    private CourseService courseService;

    @Resource
    private AccountService accountService;
}
