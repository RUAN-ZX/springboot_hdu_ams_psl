package cn.ryanalexander.psl.domain.bo.excel;

import cn.ryanalexander.common.domain.exceptions.AppException;
import cn.ryanalexander.common.domain.exceptions.code.ErrorCode;
import cn.ryanalexander.common.domain.exceptions.code.SubjectEnum;
import cn.ryanalexander.psl.domain.po.SpecialAssignmentPO;
import cn.ryanalexander.psl.mapper.AccountMapper;
import cn.ryanalexander.psl.service.SpecialAssignmentService;
import cn.ryanalexander.psl.service.tool.DataUtil;
import cn.ryanalexander.psl.service.tool.SpringUtil;
import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * <p><b></b></p>
 *
 * <p>2022/4/10 </p>
 *
 * @author ryan 2022/4/10 12:17
 * @since 1.0.0
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel("学院专项")
@ToString
@ExcelIgnoreUnannotated
public class S1SpecialAssignment implements ExcelEntity<S1SpecialAssignment>, Cloneable{

    @ExcelProperty("姓名")
    private String specialAssignmentTeacherName;
    @ExcelProperty("非标志性绩分补贴/年")
    private Double specialAssignmentKpi;

    @ExcelProperty("专项名称")
    private String specialAssignmentName;

    @ExcelProperty("专项项目")
    private String specialAssignmentProject;

    private Integer specialAssignmentYear;
    private Integer specialAssignmentTeacherId;

    @Override
    public boolean isValidated() {
        // 有名字 名字存在 有时长 这记录才有用！ 如果只有老师+标准学时 也认了。。
        return specialAssignmentTeacherName != null;

    }

    @Override
    public boolean multiStart() {
        return DataUtil.multiStart(specialAssignmentTeacherName);
    }

    // 多行 多人头出现
    @Override
    public boolean prevIsMultiHeadOperation(ExcelEntity mask) {
        S1SpecialAssignment lastMultiHead = (S1SpecialAssignment) mask;
        // 专项项目要叠加 多个多人头。。
        this.specialAssignmentProject += lastMultiHead.specialAssignmentProject;
        return false;
    }

    @Override // 计划 课程代码那边可能要写分配的占比！
    public boolean multiContinue() {
        return specialAssignmentProject == null && specialAssignmentName == null;
    }

    @Override
    public void fieldStandardized() {
        // 用对象前 先检测null
        if (this.specialAssignmentName != null && this.specialAssignmentName.length() > 24)
            this.specialAssignmentName = this.specialAssignmentName.substring(0, 24);

        if (this.specialAssignmentProject != null && this.specialAssignmentProject.length() > 36)
            this.specialAssignmentProject = this.specialAssignmentProject.substring(0, 36);
    }

    @Override
    public ExcelEntity copyFromMasterMask(ExcelEntity data) {
        S1SpecialAssignment result = null;
        S1SpecialAssignment share = (S1SpecialAssignment) data;
        try {
            // 此时this就是多人的模板 result就是每个老师的分成 对象实例
            // share就是excel读取的分成 数据
            result = (S1SpecialAssignment) this.clone();
        } catch (CloneNotSupportedException e) {
            throw new AppException(new ErrorCode(SubjectEnum.INTERNAL));
        }

        // 老师名字必须改
        result.specialAssignmentTeacherName = share.getSpecialAssignmentTeacherName();

        result.specialAssignmentKpi = share.getSpecialAssignmentKpi(); // 有标准课时可用就拿来咯
        // 课程总课时 等等其他参数 全部放进来即可 不需要分成。。
        return result;
    }

    @Override
    public void stdCalculator(List<Map<Integer, String>> headMapList) {
        try{
            // null的含义就是 我不想更新这个时间
            if(headMapList != null){
                this.specialAssignmentYear = Integer.valueOf(headMapList.get(0).get(0));
            }
        }
        catch (Exception e){
            System.out.println("Invalid currentHeadInfo, a integer for current date(year) Expected");
            e.printStackTrace();
        }
    }

    @Override
    public void transformAndSave(ArrayList<S1SpecialAssignment> list, int size) {
        SpecialAssignmentService specialAssignmentService =
                (SpecialAssignmentService) SpringUtil.getBean("specialAssignmentServiceImpl");
        AccountMapper accountMapper = (AccountMapper) SpringUtil.getBean("accountMapper");

        ArrayList<String> accountNameList = new ArrayList<>(size);

        // 通过accountName 获取accountId
        // 另外顺便做些处理 处理多人课程 必须放在那里 因为是共性问题 但这里就是个性问题
        for (S1SpecialAssignment s1SpecialAssignment : list) {
            accountNameList.add(s1SpecialAssignment.getSpecialAssignmentTeacherName());
        }
        // todo 这里存在问题 如果这个老师不存在 找到的id为null 应当怎么处理为好？
        // 目前是计划 我先导入 虽然id为一个值 比如null 到时候再补充 全库批量找null 然后 update还是快的
        ArrayList<Integer> accountIdList = accountMapper.selectBatchIdByName(accountNameList);
        ArrayList<SpecialAssignmentPO> specialAssignmentPOS = new ArrayList<>(size);

        // accountId 注入到CourseTheory
        S1SpecialAssignment s1SpecialAssignment = null;
        for (int i = 0; i < list.size(); i++) {
            try {
                s1SpecialAssignment = list.get(i);
                s1SpecialAssignment.setSpecialAssignmentTeacherId(accountIdList.get(i));
                // 有些字段实在太长 删减点 别太过了
                s1SpecialAssignment.fieldStandardized();
                // 内置转换函数 能够将CourseTheory转换为Course 然后save！
                specialAssignmentPOS.add(new SpecialAssignmentPO(s1SpecialAssignment));
            } catch (Exception e) {
                e.printStackTrace();
//                throw new AppException(e, "CourseTheory", "transformAndSave courseService.saveBatch(courses)");
            }
        }
        try {
            specialAssignmentService.saveBatch(specialAssignmentPOS);

        } catch (Exception e) {
            e.printStackTrace();
            throw new AppException(e, "CourseTheory", "transformAndSave courseService.saveBatch(courses)");
        } finally {
            specialAssignmentPOS.clear();
            accountIdList.clear();
            accountNameList.clear();
        }
    }
}