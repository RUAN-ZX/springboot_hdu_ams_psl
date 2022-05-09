package cn.ryanalexander.psl.service.excel;

import cn.ryanalexander.psl.domain.bo.excel.*;
import cn.ryanalexander.psl.domain.bo.excel.out.S1Workload;
import cn.ryanalexander.psl.domain.bo.excel.out.SFinal;
import cn.ryanalexander.psl.service.tool.DataUtil;
import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelReader;
import com.alibaba.excel.read.metadata.ReadSheet;
import org.springframework.stereotype.Service;

import java.io.InputStream;
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
    private static final HashMap<String, Class<?>> ModelSheetList = new HashMap<>();
    static{
        // 最好先有工号邮箱 否则匹配的teacherId全是0 到时候还要清盘
        ModelSheetList.put("工号和邮箱", AccountIdAndEmail.class);
        ModelSheetList.put("职称信息表", TitleInfo.class);
//
        ModelSheetList.put("理论", S1CourseTheory.class);
        ModelSheetList.put("短学期", S1ShortTerm.class);
        ModelSheetList.put("实验", S1CourseExperiment.class);
        ModelSheetList.put("毕业设计", S1ThesisDesign.class);
//
//        // 历史数据则可以通过工作量表获取 新数据应该有
        ModelSheetList.put("标志性", S1Achievement.class);
        ModelSheetList.put("非标志性业绩点", S1Achievement.class);
        ModelSheetList.put("其他业绩", S1Achievement.class);
        ModelSheetList.put("学院专项", S1SpecialAssignment.class);

        ModelSheetList.put("总得分", S2Evaluation.class);

        ModelSheetList.put("课程", Course.class);

        // 汇总表需要灵活处理！
//        sheetAndExcelEntity.put("成绩明细表", S1234.class);
        // 让老师修剪自己的表 满足工作量表的要求！
        ModelSheetList.put("工作量", S1Workload.class);
        ModelSheetList.put("成绩汇总表", SFinal.class);
    }

    private static final HashMap<String, Class<?>> NonModelSheetList = new HashMap<>();
    static {
        NonModelSheetList.put("成绩明细表", NoModelDataListener.class);
    }

    public void excelRead(InputStream file){
        ExcelReader excelReader = null;

        // 匹配名字 然后执行对应的套路
        try {
            excelReader = EasyExcel.read(file).build();
            List<ReadSheet> sheets = excelReader.excelExecutor().sheetList();
            for (ReadSheet sheet : sheets) {
                String realName = sheet.getSheetName();
                // 可以用别的字符做区分 省的sheetName重复 但是中文对就行！
                Matcher matcher = Pattern.compile(DataUtil.CH_REGEX).matcher(realName);

                if(matcher.find()) { // 两种可能 都试一下 注意两个map不应该重合
                    String matchResult = matcher.group(1);
                    if(ModelSheetList.get(matchResult) != null){
                        System.out.println(matchResult + " realName: " + realName);
                        ReadSheet readSheet = EasyExcel.readSheet(realName)
                                .headRowNumber(2) // 其实还可以特别指定哪个表对应headRows Map嘛
                                .head(ModelSheetList.get(matchResult))
                                .registerReadListener(new ModalDataListener()).build();

                        excelReader.read(readSheet);
                    }
                    else if(NonModelSheetList.get(matchResult) != null){
                        EasyExcel.read(file, new NoModelDataListener()).headRowNumber(2)
                                .sheet(realName).doRead();
                    }
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
}
