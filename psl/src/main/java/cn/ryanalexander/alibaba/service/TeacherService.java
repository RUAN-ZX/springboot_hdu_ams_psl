package cn.ryanalexander.alibaba.service;

import cn.ryanalexander.alibaba.domain.po.Teacher;
import com.baomidou.mybatisplus.extension.service.IService;

import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;

/**
 * <p><b>老师相关CRUD的Service类</b></p>
 * <p>2022-03-22 </p>
 * @since 1.0.0
 * @author RyanAlexander 2022-03-22 12:33
 */
@Service
public interface TeacherService extends IService<Teacher> {
    void insertByIdNameEmail(ArrayList<?> list);

}