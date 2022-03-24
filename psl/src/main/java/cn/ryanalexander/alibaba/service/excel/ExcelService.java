package cn.ryanalexander.alibaba.service.excel;

import cn.ryanalexander.alibaba.domain.bo.excel.ExcelEntity;
import cn.ryanalexander.alibaba.domain.exceptions.ExceptionInfo;
import cn.ryanalexander.alibaba.domain.exceptions.code.ErrorCode;
import cn.ryanalexander.alibaba.domain.exceptions.AppException;
import cn.ryanalexander.alibaba.domain.exceptions.code.SubjectEnum;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.Map;

/**
 * <p><b>Excel相关服务</b></p>
   结合ExcelEntity提供的自检 multi开头 中间的检测以解决多个老师分配学时的问题
 * <p>2022-03-24 </p>

 * @since
 * @author  2022-03-24 22:47
 */
@Slf4j
public class ExcelService extends AnalysisEventListener<ExcelEntity> {

    public ExcelService() {

    }
    private static final int BATCH_COUNT = 64;
    private boolean multiStart = false; // 为true 强制不能截断！为false才行

//    private int masterMaskIdx = Integer.MAX_VALUE; // 每一批 如果多人 其母版的index
//    private int currentIdx = 0; // 当前index
    private final ArrayList<ExcelEntity> list = new ArrayList<>(BATCH_COUNT);
    private ExcelEntity masterMask = null;

    @Override
    public void invokeHeadMap(Map<Integer, String> headMap, AnalysisContext context) {
        log.info("解析到一条头数据:{}", JSON.toJSONString(headMap));
    }

    private void saveList(){
        try {
            ExcelEntity o1 = list.get(0);
            o1.transformAndSave(list, BATCH_COUNT);
        } catch (AppException e) {
            throw new AppException(e, null, new ErrorCode(SubjectEnum.INTERNAL));
        }
        finally {
            // 存储完成清理 list
            list.clear();
        }
    }
    @Override
    public void invoke(ExcelEntity data, AnalysisContext context) {
        if(data.isValidated()){
            log.info(data.toString());
            // 既不是multi开端 也不是 multi中间
            if(!data.multiStart() && !data.multiContinue()) {
                list.add(data);
                multiStart = false;
            }
            // 如果是multi中间 添加转换后的data 应该说masterMask基础上 根据data的参数改造的
            else if(data.multiContinue()) {
                list.add(masterMask.copyFromMasterMask(data));
            }
            // 如果multi开端
            else {
                multiStart = true;
                masterMask = data;
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
