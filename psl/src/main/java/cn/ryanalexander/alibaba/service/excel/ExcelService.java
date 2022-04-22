package cn.ryanalexander.alibaba.service.excel;

import cn.ryanalexander.alibaba.domain.bo.excel.*;
import cn.ryanalexander.alibaba.domain.bo.excel.out.S1234;
import cn.ryanalexander.alibaba.domain.bo.excel.out.S1Workload;
import cn.ryanalexander.alibaba.domain.bo.excel.out.SFinal;
import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelReader;
import com.alibaba.excel.read.metadata.ReadSheet;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * <p><b></b></p>
 *
 * <p>2022/4/17 </p>
 *
 * @author ryan 2022/4/17 10:11
 * @since 1.0.0
 **/
@Service
public class ExcelService {
    private static final HashMap<String, Class<?>> sheetAndExcelEntity = new HashMap<>();
    static{
        // 最好先有工号邮箱 否则匹配的teacherId全是0 到时候还要清盘
        sheetAndExcelEntity.put("工号和邮箱", AccountIdAndEmail.class);
        sheetAndExcelEntity.put("职称信息表", TitleInfo.class);
//
        sheetAndExcelEntity.put("理论", S1CourseTheory.class);
        sheetAndExcelEntity.put("短学期", S1ShortTerm.class);
        sheetAndExcelEntity.put("实验", S1CourseExperiment.class);
        sheetAndExcelEntity.put("毕业设计", S1ThesisDesign.class);
//
//        // 历史数据则可以通过工作量表获取 新数据应该有
        sheetAndExcelEntity.put("标志性", S1Achievement.class);
        sheetAndExcelEntity.put("非标志性业绩点", S1Achievement.class);
        sheetAndExcelEntity.put("其他业绩", S1Achievement.class);
        sheetAndExcelEntity.put("学院专项", S1SpecialAssignment.class);

        sheetAndExcelEntity.put("学评教", S2Evaluation.class);

        // 汇总表需要灵活处理！
//        sheetAndExcelEntity.put("成绩明细表", S1234.class);
        // 让老师修剪自己的表 满足工作量表的要求！
        sheetAndExcelEntity.put("工作量", S1Workload.class);
        sheetAndExcelEntity.put("成绩汇总表", SFinal.class);
    }
    private final static String chineseRegex = "([\u4e00-\u9fa5]+)";
    private final ArrayList<String> noModalSheetList = new ArrayList<>();
    public void modelRead(String url){
        ExcelReader excelReader = null;

        // 匹配名字 然后执行对应的套路
        try {
            excelReader = EasyExcel.read(url).build();
            List<ReadSheet> sheets = excelReader.excelExecutor().sheetList();
            for (ReadSheet sheet : sheets) {
                // 可以用别的字符做区分 省的sheetName重复 但是中文对就行！
                Matcher matcher = Pattern.compile(chineseRegex).matcher(sheet.getSheetName());

                if(matcher.find()) { // 两种可能 都试一下 注意两个map不应该重合
                    String matchResult = matcher.group(1);
                    Class<?> excelModel = sheetAndExcelEntity.get(matchResult);
                    if(excelModel != null){
                        System.out.println(matchResult);
                        ReadSheet readSheet = EasyExcel.readSheet(matchResult)
                                .headRowNumber(2) // 其实还可以特别指定哪个表对应headRows Map嘛
                                .head(excelModel)
                                .registerReadListener(new ModalDataListener()).build();

                        excelReader.read(readSheet);
                    }
                    else noModalSheetList.add(matchResult);
                }
            }
        }
        catch (Exception e){
            e.printStackTrace();
//            throw new AppException(e, "ExcelController" ,"updateExcel");
        }
        finally {
            if(excelReader != null) excelReader.finish();
        }
    }
    public void noModelRead(String url){
        for (String noModalSheetName:noModalSheetList) {
            EasyExcel.read(url, new NoModelDataListener()).headRowNumber(2).sheet(noModalSheetName).doRead();
        }
    }
}
