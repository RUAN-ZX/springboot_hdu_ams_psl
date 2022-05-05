package cn.ryanalexander.sst.controller;

import cn.ryanalexander.sst.domain.po.SubjectPO;
import cn.ryanalexander.sst.mapper.SubjectMapper;
import com.alibaba.fastjson.JSONObject;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p><b></b></p>
 *
 * <p>2022/5/5 </p>
 *
 * @author ryan 2022/5/5 9:07
 * @since 1.0.0
 **/
@Api(tags = "科目")
@RestController
public class SubjectController {
    @Resource
    private SubjectMapper subjectMapper;

    @ApiOperation("获取所有科目")
    @GetMapping("/getAllSubject")
    public List<SubjectPO> getAllSubject(){
        return subjectMapper.selectList(null); // 返回所有科目
    }
}
