package cn.ryanalexander.alibaba.service.excel;

import cn.ryanalexander.alibaba.domain.bo.excel.ExcelEntity;
import cn.ryanalexander.alibaba.domain.exceptions.code.ErrorCode;
import cn.ryanalexander.alibaba.domain.exceptions.code.ErrorCodeEnum;
import cn.ryanalexander.alibaba.domain.exceptions.AppException;
import cn.ryanalexander.alibaba.domain.exceptions.code.SubjectEnum;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.Map;
@Slf4j
public class ExcelService extends AnalysisEventListener<ExcelEntity> {

    public ExcelService() {

    }
    private static final int BATCH_COUNT = 64;
    private final ArrayList<ExcelEntity> list = new ArrayList<>(BATCH_COUNT);

    @Override
    public void invokeHeadMap(Map<Integer, String> headMap, AnalysisContext context) {
        log.info("解析到一条头数据:{}", JSON.toJSONString(headMap));
    }

    private void saveList(){
        try {
            ExcelEntity o1 = list.get(0);
            o1.transformAndSave(list, BATCH_COUNT);
        } catch (Exception e) {
            throw new AppException(new ErrorCode(SubjectEnum.INTERNAL), e.getMessage());
        }
        finally {
            // 存储完成清理 list
            list.clear();
        }
    }
    @Override
    public void invoke(ExcelEntity data, AnalysisContext context) {
        if(data.isValidated()) list.add(data);
        // 达到BATCH_COUNT了，需要去存储一次数据库，防止数据几万条数据在内存，容易OOM
        if (list.size() >= BATCH_COUNT) saveList();
    }

    @Override // 最后一批
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {
        saveList();
    }
}
