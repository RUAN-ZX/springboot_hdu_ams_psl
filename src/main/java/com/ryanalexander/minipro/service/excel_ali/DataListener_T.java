package com.ryanalexander.minipro.service.excel_ali;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.fastjson.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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



    private static String className;
    private static final int BATCH_COUNT = 10;
    private List<T> list = new ArrayList<T>();

    @Override
    public void invokeHeadMap(Map<Integer, String> headMap, AnalysisContext context) {
        LOGGER.info("解析到一条头数据:{}", JSON.toJSONString(headMap));
    }

    @Override
    public void invoke(T data, AnalysisContext context) {

        String pattern = "([A-Za-z]+)Entity$";
        Pattern r = Pattern.compile(pattern);
        Matcher m = r.matcher(data.getClass().toString());
        if(m.find()) className = m.group(0);
        list.add(data);


        // 达到BATCH_COUNT了，需要去存储一次数据库，防止数据几万条数据在内存，容易OOM
        if (list.size() >= BATCH_COUNT) {
            easyExcelService.save(list,m.group(0));
            // 存储完成清理 list
            list.clear();
        }
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {
        easyExcelService.save(list,className);
    }



}
