package cn.ryanalexander.alibaba.service.excel;

import cn.ryanalexander.alibaba.domain.bo.excel.CourseShortTerm;
import cn.ryanalexander.alibaba.domain.bo.excel.CourseTheory;
import cn.ryanalexander.alibaba.domain.bo.excel.ExcelEntity;
import cn.ryanalexander.alibaba.domain.exceptions.ExceptionInfo;
import cn.ryanalexander.alibaba.domain.exceptions.code.ErrorCode;
import cn.ryanalexander.alibaba.domain.exceptions.AppException;
import cn.ryanalexander.alibaba.domain.exceptions.code.SubjectEnum;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.excel.exception.ExcelDataConvertException;
import com.alibaba.excel.metadata.CellExtra;
import com.alibaba.fastjson.JSON;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;
import java.util.Objects;

/**
 * <p><b>Excel相关服务</b></p>
   结合ExcelEntity提供的自检 multi开头 中间的检测以解决多个老师分配学时的问题
 * <p>2022-03-24 </p>

 * @since
 * @author  2022-03-24 22:47
 * 旧的多人 既往不咎 反正只要有标准学时直接用
 * 新的多人会针对多个多人表头优化 所以只需要课程代码那边写上百分占比即可！
 */
@Slf4j
@NoArgsConstructor
public class ExcelService extends AnalysisEventListener<ExcelEntity> {

    private static final int BATCH_COUNT = 64;
    private boolean multiStart = false; // 为true 强制不能截断！为false才行

//    private int masterMaskIdx = Integer.MAX_VALUE; // 每一批 如果多人 其母版的index
//    private int currentIdx = 0; // 当前index
    private final ArrayList<ExcelEntity> list = new ArrayList<>(BATCH_COUNT);
    private ExcelEntity masterMask = null;
    // 上一个是不是mask 也就是multi的开始
    private boolean prevIsNotMask = false;
    @Override
    public void onException(Exception exception, AnalysisContext context) throws Exception {
        log.error("解析失败:{}", Arrays.toString(exception.getStackTrace()));

        // 如果是某一个单元格的转换异常 能获取到具体行号

        // 如果要获取头的信息 配合invokeHeadMap使用
        if (exception instanceof ExcelDataConvertException) {
            ExcelDataConvertException excelDataConvertException = (ExcelDataConvertException) exception;
            log.error("第{}行，第{}列解析异常", excelDataConvertException.getRowIndex(),
                    excelDataConvertException.getColumnIndex());
        }
    }
    @Override
    public void extra(CellExtra extra, AnalysisContext context) {
        super.extra(extra, context);
    }

    @Override
    public void invokeHeadMap(Map<Integer, String> headMap, AnalysisContext context) {
        log.info("解析到一条头数据:{}", JSON.toJSONString(headMap));

    }

    private void saveList(){
        try {
            ExcelEntity o1 = list.get(0);
            o1.transformAndSave(list, BATCH_COUNT);
        } catch (Exception e) {
            e.printStackTrace();
//            throw new AppException(e, null, new ErrorCode(SubjectEnum.INTERNAL));
        }
        finally {
            // 存储完成清理 list
            list.clear();
        }
    }
    @Override
    public void invoke(ExcelEntity data, AnalysisContext context) {
//        log.info(data.toString());
//        CourseTheory data1 = (CourseTheory) data;
//        boolean fuck = Objects.equals(data1.courseTeacherName, "郑梁");
        CourseShortTerm a = (CourseShortTerm) data;
//        log.info(data1.courseHours );
        if(data.isValidated()){

            // 既不是multi开端 也不是 multi中间
            if(!data.multiStart() && !data.multiContinue()) {
                prevIsNotMask = false;
                list.add(data);
                multiStart = false;
            }
            // 如果是multi中间 添加转换后的data 应该说masterMask基础上 根据data的参数改造的
            else if(data.multiContinue()) {
                prevIsNotMask = false;
//                CourseTheory data1 = (CourseTheory) data;
//                先搞定分成 还有模板填充
                list.add(masterMask.copyFromMasterMask(data));
            }
            // 如果multi开端
            else if(data.multiStart()){
                multiStart = true;
                data.stdCalculator();
                // 多个多人 记得累加
                if(prevIsNotMask)
                    data.stdAccumulate(masterMask);
                // data的std是前面所有的总和！
                masterMask = data;
                // 标准化计算
                prevIsNotMask = true;

            }

            // 达到BATCH_COUNT了，需要去存储一次数据库，防止数据几万条数据在内存，容易OOM
            if (list.size() >= BATCH_COUNT && !multiStart) saveList();
        }
    }

    @Override // 最后一批
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {
        if(list.size() > 0) saveList();
    }
}
