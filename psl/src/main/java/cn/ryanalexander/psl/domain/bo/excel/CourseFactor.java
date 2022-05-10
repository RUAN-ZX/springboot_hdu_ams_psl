package cn.ryanalexander.psl.domain.bo.excel;

import cn.ryanalexander.psl.domain.exceptions.AppException;
import cn.ryanalexander.psl.domain.po.CourseUnionPO;
import cn.ryanalexander.psl.mapper.CourseUnionMapper;
import cn.ryanalexander.psl.service.CourseUnionService;
import cn.ryanalexander.psl.service.tool.CourseNumDecoder;
import cn.ryanalexander.psl.service.tool.DataUtil;
import cn.ryanalexander.psl.service.tool.SpringUtil;
import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.checkerframework.checker.units.qual.K;

import java.util.*;

/**
 * <p><b></b></p>
 *
 * <p>2022/5/10 </p>
 *
 * @author ryan 2022/5/10 9:48
 * @since 1.0.0
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel("课程 属性导入")
@ToString
@ExcelIgnoreUnannotated
public class CourseFactor implements ExcelEntity<CourseFactor>, Cloneable {
    @ExcelProperty("课号")
    private String courseNum;

    @ExcelProperty("类别系数")
    private String courseData;

    @ExcelProperty("其他系数")
    private String courseDataOther;

    private JSONObject data = new JSONObject();
    private JSONObject dataOthers = new JSONObject();

    @Override
    public boolean isValidated() {
        // 有名字 有百分占比 正常情况！
        return courseNum != null;
    }

    @Override
    public boolean multiStart(){
        return false;
    }
    // 这里 之前也是multiStart 应该怎么处理
    @Override
    public boolean prevIsMultiHeadOperation(ExcelEntity mask){
        return false;
    }

    @Override // 下一行的补充数据
    public boolean multiContinue(){
        return false;
    }


    @Override
    public void fieldStandardized(){


    }
    @Override
    public void stdCalculator(List<Map<Integer, String>> headInfoMap){

    }

    @Override
    public void transformAndSave(ArrayList<CourseFactor> list, int size) {
        CourseUnionService courseUnionService =
                (CourseUnionService) SpringUtil.getBean("courseUnionServiceImpl");
        CourseUnionMapper courseUnionMapper =
                (CourseUnionMapper) SpringUtil.getBean("courseUnionMapper");

        // todo 这里 致命缺点 1 课程表必须有这些课 2 不能重复 否则从重复的开始全部错位！
        ArrayList<String> nums = new ArrayList<>(size);
        for(CourseFactor courseFactor : list){
            nums.add(courseFactor.getCourseNum()); // 添加id
            String dataRaw = courseFactor.getCourseData();
            String dataOthersRaw = courseFactor.getCourseDataOther();
            if(dataRaw != null){
                String[] dataStr = dataRaw.split("；"); //中文分号分割
                for(String kvData : dataStr){
                    // 因为系数都是1.x格式 我直接取后三位为v 前面为k
                    int len = kvData.length();
                    data.put(
                            kvData.substring(0, len - 3),
                            kvData.substring(len - 3, len)
                    );
                }
            }
            if(dataOthersRaw != null){
                String[] dataOthersStr = dataOthersRaw.split("；"); //中文分号分割
                for(String kvData : dataOthersStr){
                    int len = kvData.length();
                    dataOthers.put(
                            kvData.substring(0, len - 3),
                            kvData.substring(len - 3, len)
                    );
                }
            }

            // 源数据转成JSONObject
        }
        // 批量把data data_other查出来 然后添加！
        List<CourseUnionPO> courseUnionPOS = courseUnionMapper.selectList(new QueryWrapper<CourseUnionPO>()
                .in("course_num", nums));
        for(CourseUnionPO course : courseUnionPOS){
            String data_ = course.getCourseData();
            String dataOthers_ = course.getCourseDataOthers();

            JSONObject dataFinal = data_ == null ? new JSONObject() : JSON.parseObject(data_);
            JSONObject dataOthersFinal =
                    dataOthers_ == null ? new JSONObject() : JSON.parseObject(dataOthers_);

            dataFinal.putAll(data);
            dataOthersFinal.putAll(dataOthers);

            course.setCourseData(dataFinal.toJSONString());
            course.setCourseData(dataOthersFinal.toJSONString());
        }
        try{
            courseUnionService.updateBatchById(courseUnionPOS);
        }
        catch (Exception e){
            e.printStackTrace();
            throw new AppException(e, "courseUnion", "transformAndSave CourseShortTerm.saveBatch(courses)");
        }
        finally {
            courseUnionPOS.clear();
        }
    }
}

