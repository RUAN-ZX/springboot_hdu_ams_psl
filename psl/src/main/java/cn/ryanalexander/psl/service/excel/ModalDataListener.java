package cn.ryanalexander.psl.service.excel;

import cn.ryanalexander.psl.domain.bo.excel.ExcelEntity;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.excel.exception.ExcelDataConvertException;
import com.alibaba.excel.metadata.CellExtra;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
public class ModalDataListener extends AnalysisEventListener<ExcelEntity> {

    private static final int BATCH_COUNT = 512; // 数据库插入1000条太常见了。。
    private boolean multiStart = false; // 为true 强制不能截断！为false才行

//    private int masterMaskIdx = Integer.MAX_VALUE; // 每一批 如果多人 其母版的index
//    private int currentIdx = 0; // 当前index
    private final ArrayList<ExcelEntity> list = new ArrayList<>(BATCH_COUNT);
    private ExcelEntity masterMask = null;
    // 上一个是不是mask 也就是multi的开始
    private boolean prevIsMultiHead = false;

    // currentHeadInfo 比如毕设的年份 日期等等
    private List<Map<Integer, String>> headMapList = new ArrayList<>();
    @Override
    public void onException(Exception exception, AnalysisContext context) throws Exception {
//        log.error("解析失败:{}", Arrays.toString(exception.getStackTrace()));
        exception.printStackTrace();

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
//        log.info("解析到一条头数据:{}", JSON.toJSONString(headMap));
        if(headMapList.size() == 0){
            // 拿100的位置存表名！
            headMap.put(100, context.readSheetHolder().getSheetName());
            headMapList.add(headMap);
        }

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
        if(data.isValidated()){
            // 既不是multi开端 也不是 multi中间
            if(!data.multiStart() && !data.multiContinue()) {
                data.stdCalculator(headMapList);
                list.add(data);
                multiStart = false;
                prevIsMultiHead = false;
            }
            // 如果是multi中间 添加转换后的data 应该说masterMask基础上 根据data的参数改造的
            else if(data.multiContinue()) {
                prevIsMultiHead = false;
//                先搞定分成 还有模板填充
                // 即便对于毕设这种 没有分成 也可以补充模板的内容！
                list.add(masterMask.copyFromMasterMask(data));
            }
            // 如果multi开端
            else if(data.multiStart()){
                data.stdCalculator(headMapList);
                // 多个多人 记得累加
                if(prevIsMultiHead){
                    boolean addMultiHead = data.prevIsMultiHeadOperation(masterMask);
                    if(addMultiHead) list.add(masterMask); // 意思存储这个多头 用于毕设那边
                }
                // data的std是前面所有的总和！
                masterMask = data;
                // 标准化计算
                prevIsMultiHead = true;
                multiStart = true;
            }
            // 达到BATCH_COUNT了，需要去存储一次数据库 已经开头了别断！
            if (list.size() >= BATCH_COUNT && !multiStart) saveList();
        }
    }

    @Override // 最后一批
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {
        if(list.size() > 0) saveList();
        headMapList.clear(); // 清空！
    }
}
