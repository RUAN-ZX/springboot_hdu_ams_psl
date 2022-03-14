package cn.ryanalexander.alibaba.service.excel_ali;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.fastjson.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DataListener_T<T> extends AnalysisEventListener<T> {



    private EasyExcelService easyExcelService;

    public DataListener_T(EasyExcelService easyExcelService) {
        // 这里是demo，所以随便new一个。实际使用如果到了spring,请使用下面的有参构造函数
        this.easyExcelService = easyExcelService;
    }
    private static final Logger LOGGER = LoggerFactory.getLogger(DataListener_T.class);

    private static final int BATCH_COUNT = 10;
    private ArrayList<T> list = new ArrayList<T>();

    @Override
    public void invokeHeadMap(Map<Integer, String> headMap, AnalysisContext context) {
        LOGGER.info("解析到一条头数据:{}", JSON.toJSONString(headMap));
    }

    @Override
    public void invoke(T data, AnalysisContext context) {
        list.add(data);
        // 达到BATCH_COUNT了，需要去存储一次数据库，防止数据几万条数据在内存，容易OOM
        if (list.size() >= BATCH_COUNT) {
            try {
                easyExcelService.save(list); // save函数也是insert一批数据的
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
            // 存储完成清理 list
            list.clear();
        }
    }

    @Override // 最后一批
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {
        try {
            easyExcelService.save(list);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }



}
