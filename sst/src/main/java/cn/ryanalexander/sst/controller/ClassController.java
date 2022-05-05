package cn.ryanalexander.sst.controller;

import cn.ryanalexander.common.domain.exceptions.AppException;
import cn.ryanalexander.sst.domain.po.ClassPO;
import cn.ryanalexander.sst.domain.po.RecordPO;
import cn.ryanalexander.sst.domain.po.UserPO;
import cn.ryanalexander.sst.mapper.ClassMapper;
import cn.ryanalexander.sst.mapper.RecordMapper;
import cn.ryanalexander.sst.mapper.UserMapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.Parameter;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * <p><b></b></p>
 *
 * <p>2022/5/5 </p>
 *
 * @author ryan 2022/5/5 15:15
 * @since 1.0.0
 **/

@Api(tags = "学生、班级 记录")
@RestController
public class ClassController {
    @Resource
    private RecordMapper recordMapper;

    @Resource
    private ClassMapper classMapper;

    @Resource
    private UserMapper userMapper;

//    @Require(RoleEnum.STUDENT)
    @ApiOperation("学生查看自己加入的所有班级")
    @GetMapping("/getAttendedClass")
    public List<ClassPO> getAttendedClass(@Parameter(description = "学生ID")int userId){
        List<Object> classIds = recordMapper.selectObjs(new QueryWrapper<RecordPO>()
                .select("record_class_id")
                .eq("record_student_id", userId));
        if(classIds.size() == 0) return new ArrayList<ClassPO>();
        return classMapper.selectList(new QueryWrapper<ClassPO>()
                .in("class_id", classIds));
    }

//    @Require
    @ApiOperation("查看班级里边的学生、同学 名字与昵称")
    @GetMapping("/getClassInfo")
    public List<UserPO> getClassInfo(int classId){
        // todo 应当检查 是否为这个班级的学生 或者班级创建人
        List<Object> classMateIds = recordMapper.selectObjs(new QueryWrapper<RecordPO>()
                .select("record_student_id")
                .eq("record_class_id", classId));
        
        if(classMateIds.size() == 0) return new ArrayList<UserPO>();

        return userMapper.selectList(new QueryWrapper<UserPO>()
                .select("user_alias", "user_name")
                .in("user_id", classMateIds));
    }

//    @Require(RoleEnum.STUDENT)
    @ApiOperation("学生加入班级")
    @GetMapping("/attendClass")
    public String attendClass(int userId, int classId){
        RecordPO recordPO = new RecordPO(null, userId, classId);
        recordMapper.insert(recordPO);
        return "Record id: " + recordPO.getRecordId();
    }

//    @Require(RoleEnum.TEACHER)
    @ApiOperation("教师添加班级")
    @PostMapping("/addClass")
    public Integer addClass(
            @Parameter(description = "创建班级的老师ID") int userId,
            String className){
        ClassPO classPO = new ClassPO(null, className, userId);
        classMapper.insert(classPO);
        return classPO.getClassId();
    }
    @ApiOperation("教师查看自己创建的班级")
    @GetMapping("/getCreatedClass")
    public List<ClassPO> getCreatedClass(
            @Parameter(description = "创建班级的老师ID")int userId
    ){
        return classMapper.selectList(new QueryWrapper<ClassPO>()
                .eq("class_teacher_id", userId));
    }
}
