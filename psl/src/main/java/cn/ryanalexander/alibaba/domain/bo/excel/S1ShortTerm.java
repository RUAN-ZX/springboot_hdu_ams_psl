package cn.ryanalexander.alibaba.domain.bo.excel;

import cn.ryanalexander.alibaba.domain.exceptions.AppException;
import cn.ryanalexander.alibaba.domain.exceptions.code.ErrorCode;
import cn.ryanalexander.alibaba.domain.exceptions.code.SubjectEnum;
import cn.ryanalexander.alibaba.domain.po.ShortTermPO;
import cn.ryanalexander.alibaba.mapper.AccountMapper;
import cn.ryanalexander.alibaba.service.ShortTermService;
import cn.ryanalexander.alibaba.service.tool.DataUtil;
import cn.ryanalexander.alibaba.service.tool.SpringUtil;
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

 * <p>2022-04-08 </p>

 * @since 1.0.0
 * @author RyanAlexander 2022-04-08 19:03
 * @apiNote 对于多人 且多课号的情况 短学期的逻辑是 统计多个分身的总课时+记录课程信息（最下边的）+使用给定占比
 * 如果没有占比就使用标准学时（更快）
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel("工作量_短学期")
@ToString
@ExcelIgnoreUnannotated
public class S1ShortTerm implements ExcelEntity<S1ShortTerm>, Cloneable{
    /**
     *
     */
    @ExcelProperty(value = "课程代码")
    private String shortTermNum;

    /**
     *
     */
    @ExcelProperty(value = "学期")
    private String shortTermTerm;
    /**
     *
     */
    @ExcelProperty(value = "起始时间")
    private String shortTermTime;

    /**
     *
     */
    @ExcelProperty(value = "课程名称")
    private String shortTermName;

    /**
     *
     */
    @ExcelProperty(value = "上课地点")
    private String shortTermAddress;

    /**
     *
     */
    @ExcelProperty(value = "教师姓名")
    private String shortTermTeacherName;

    /**
     *
     */
    @ExcelProperty(value = "已选人数")
    private Integer shortTermCapacity;

    /**
     *
     */
    @ExcelProperty(value = "班级规模系数")
    private Double shortTermCapacityFactor;

    /**
     *
     */
    @ExcelProperty(value = "类别系数")
    private Double shortTermFactor;

    /**
     *
     */
    @ExcelProperty(value = "学时")
    private Double shortTermHours;

    /**
     *
     */
    @ExcelProperty(value = "标准学时")
    private Double shortTermHoursStd;

    @ExcelProperty(value = "教改")
    private Double shortTermReform;

    @ExcelProperty(value = "性质") // A B J
    private String shortTermProperties;

    @ExcelProperty(value = "教务处备注")
    private String shortTermNote1;

    @ExcelProperty(value = "备注")
    private String shortTermNote2;


    private Integer shortTermTeacherId;


    @Override
    public boolean isValidated() {
        // 有名字 有百分占比 正常情况！ 没有的话 有个std结果也行啊
        return shortTermTeacherName != null && (shortTermNum != null || shortTermHoursStd != null);
    }

    @Override
    public boolean multiStart(){
        return DataUtil.multiStart(shortTermTeacherName);
    }

    @Override // 没有学期 没有课程名字 又不是开头 只能是中间了！
    public boolean multiContinue(){
        return shortTermTerm == null && shortTermName == null ;
    }
    @Override
    public void fieldStandardized(){
        // 用对象前 先检测null
        if(this.shortTermNote1 != null && this.shortTermNote1.length() > 64)
            this.shortTermNote1 = this.shortTermNote1.substring(0, 64);
        if(shortTermNote2 != null && this.shortTermNote2.length() > 64)
            this.shortTermNote2 = this.shortTermNote2.substring(0, 64);

        if(shortTermName != null && this.shortTermName.length() > 24)
            this.shortTermTime = this.shortTermTime.substring(0, 24);

        if(shortTermName != null && this.shortTermName.length() > 24)
            this.shortTermName = this.shortTermName.substring(0, 24);

        if(shortTermAddress != null && this.shortTermAddress.length() > 64)
            this.shortTermAddress = this.shortTermAddress.substring(0, 64);

        if(shortTermTerm != null && this.shortTermTerm.length() > 11)
            this.shortTermTerm = this.shortTermTerm.substring(0, 11);
    }

    // 只有多人才会调用这个！
    @Override
    public ExcelEntity copyFromMasterMask(ExcelEntity data) {
        S1ShortTerm result = null;
        S1ShortTerm share = (S1ShortTerm) data;
        try {
            // 因为是自己clone自己 所以类型一定是对的
            result = (S1ShortTerm) this.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
            throw new AppException(new ErrorCode(SubjectEnum.INTERNAL));
        } catch (Exception e){
            e.printStackTrace();
        }

        // 老师名字必须改
        result.shortTermTeacherName = share.shortTermTeacherName;

        // 如果没有标注分成 那就不管了 反正都捕获到了（否则isValidate就进不来的） 直接放行到后边完事
        if(share.shortTermNum != null){
            // 获取比例 多人 选课课号标注了百分比的！
            Double ratio = Double.parseDouble(share.shortTermNum.split("%")[0]) / 100.0;
            // 课程总课时 这个就装个样子 因为有多个班级 是标准课时总和以后再分成！

            // this.shortTermHoursStd;可能是前边多个多人 标准化完 累加的结果！
            result.shortTermHoursStd = ratio * this.shortTermHoursStd;
        }
        else
            // 线程的标准课时 拿来吧!
            result.shortTermHoursStd = share.shortTermHoursStd;


        return result;
    }
    @Override
    public boolean prevIsMultiHeadOperation(ExcelEntity mask){
        S1ShortTerm s1ShortTerm = (S1ShortTerm) mask;
        this.shortTermHoursStd += s1ShortTerm.shortTermHoursStd;
        return false; // 这个多人不存储
    }

    @Override
    public void stdCalculator(List<Map<Integer, String>> headInfoMap){
        if(this.shortTermHours == null){
            return; // 直接使用指定的std了。别的都为null 因为数据库允许！
        }
        double hours = this.shortTermHours;
        double factor = this.shortTermFactor;
        double reform = this.shortTermReform;
        double capacity_factor = DataUtil.getCapacityFactorByProperty(
                this.shortTermProperties,
                this.shortTermCapacity
        );
        factor *= (capacity_factor * reform); // 归在一起了 类别本身*教改*规模*学时
        // 3位小数 而且
        this.shortTermCapacityFactor = capacity_factor;

        this.shortTermHoursStd = (double) Math.round(hours * factor);
    }
    @Override
    public void transformAndSave(ArrayList<S1ShortTerm> list, int size) {
        ShortTermService shortTermService = (ShortTermService) SpringUtil.getBean("shortTermServiceImpl");
        AccountMapper accountMapper = (AccountMapper) SpringUtil.getBean("accountMapper");

        ArrayList<String> accountNameList = new ArrayList<>(size);

        // 通过accountName 获取accountId
        // 另外顺便做些处理 处理多人课程 必须放在那里 因为是共性问题 但这里就是个性问题
        for (S1ShortTerm s1ShortTerm : list) {
            accountNameList.add(s1ShortTerm.shortTermTeacherName);
        }
        // todo 这里存在问题 如果这个老师不存在 找到的id为null 应当怎么处理为好？
        // 目前是计划 我先导入 虽然id为一个值 比如null 到时候再补充 全库批量找null 然后 update还是快的
        ArrayList<Integer> accountIdList = accountMapper.selectBatchIdByName(accountNameList);
        ArrayList<ShortTermPO> shortTerms = new ArrayList<>(size);

        // accountId 注入到CourseTheory
        S1ShortTerm s1ShortTerm = null;
        for(int i = 0 ; i < list.size() ; i++){
            try{
                s1ShortTerm = list.get(i);
                s1ShortTerm.setShortTermTeacherId(accountIdList.get(i));
                // 有些字段实在太长 删减点 别太过了
                s1ShortTerm.fieldStandardized();
                // 内置转换函数 能够将CourseTheory转换为Course 然后save！
                shortTerms.add(new ShortTermPO(s1ShortTerm));
            }
            catch (Exception e){
                e.printStackTrace();
                throw new AppException(e, "CourseShortTerm", "transformAndSave CourseShortTerm.saveBatch(courses)");
            }
        }
        try{
            shortTermService.saveBatch(shortTerms);

        }
        catch (Exception e){
            e.printStackTrace();
            throw new AppException(e, "CourseShortTerm", "transformAndSave CourseShortTerm.saveBatch(courses)");
        }
        finally {
            shortTerms.clear();
            accountIdList.clear();
            accountNameList.clear();
        }
    }
}
