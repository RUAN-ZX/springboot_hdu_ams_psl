package cn.ryanalexander.alibaba.controller;

import cn.ryanalexander.alibaba.domain.bo.excel.*;
import cn.ryanalexander.alibaba.domain.bo.excel.out.S1234;
import cn.ryanalexander.alibaba.domain.bo.excel.out.S1Workload;
import cn.ryanalexander.alibaba.domain.dto.Result;
import cn.ryanalexander.alibaba.domain.po.SDetailPO;
import cn.ryanalexander.alibaba.service.S1Service;
import cn.ryanalexander.alibaba.service.excel.ExcelService;
import cn.ryanalexander.alibaba.service.tool.StaticConfiguration;
import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelReader;
import com.alibaba.excel.read.metadata.ReadSheet;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @ClassName: ExcelController
 * @Description
 * @Author ryan
 * @Date 2022/3/13 11:35
 * @Version 1.0.0-Beta
 **/
@RestController
public class ExcelController {
    @Resource
    private StaticConfiguration StaticConfiguration;

    private static final HashMap<String, Class<?>> readSheetAndExcelEntity = new HashMap<>();
    static{
        // 最好先有工号邮箱 否则匹配的teacherId全是0 到时候还要清盘
        readSheetAndExcelEntity.put("工号和邮箱", AccountIdAndEmail.class);
//        sheetAndExcelEntity.put("职称信息表", TitleInfo.class);


//        sheetAndExcelEntity.put("理论", S1CourseTheory.class);
//        sheetAndExcelEntity.put("短学期", S1ShortTerm.class);
//        sheetAndExcelEntity.put("实验", S1CourseExperiment.class);
//        sheetAndExcelEntity.put("毕业设计", S1ThesisDesign.class);
//
//        sheetAndExcelEntity.put("研究生理论课工作量", S1PostGraduate.class);
//
//        sheetAndExcelEntity.put("标志性", S1Achievement.class);
//        sheetAndExcelEntity.put("非标志性业绩点", S1Achievement.class);
//        sheetAndExcelEntity.put("双肩挑", S1ShoulderBoth.class);
//        sheetAndExcelEntity.put("学院专项", S1SpecialAssignment.class);

        readSheetAndExcelEntity.put("学评教", S2Evaluation.class);


    }

    @ApiOperation("更新Excel")
    @GetMapping("/updateExcel")
    public Result updateExcel(){
        String url = StaticConfiguration.getExcelReadUrl();
        ExcelReader excelReader = null;
        String chineseRegex = "([\u4e00-\u9fa5]+)";
        // 多年的表 故需要匹配名字 然后执行对应的套路

        try {
            excelReader = EasyExcel.read(url).build();
            List<ReadSheet> sheets = excelReader.excelExecutor().sheetList();
            for (ReadSheet sheet : sheets) {
                // 可以用别的字符做区分 省的sheetName重复 但是中文对就行！
                Matcher matcher = Pattern.compile(chineseRegex).matcher(sheet.getSheetName());
                String matcherResult = null;
                Class<?> excelEntity = null;
                if(matcher.find()) {
                    matcherResult = matcher.group(1);
                    excelEntity = readSheetAndExcelEntity.get(matcherResult);
                }

                if(excelEntity != null){
                    ReadSheet readSheet = EasyExcel.readSheet(matcherResult)
                            .headRowNumber(2) // 其实还可以特别指定哪个表对应headRows Map嘛
                            .head(excelEntity)
                            .registerReadListener(new ExcelService()).build();

                    excelReader.read(readSheet);
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
        return new Result();
    }

    @Resource
    private S1Service s1Service;
    // todo 范围内的年都输出出来 需要检查 没有就不输出 一年一个excel！
    // getAll意味输出所有 不管前面的参数了！
    @ApiOperation("获取生成的Excel")
    @GetMapping("/getExcel")
    public Result getExcel(List<Integer> years, boolean isGetAll) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        String url = StaticConfiguration.getExcelWriteUrl();
        List<Integer> availableYears = new ArrayList<>(years);
        availableYears.retainAll(s1Service.getAllYears());// 底层就是for+contain
        // 大规模数据并集采用stream 但是这种撑死20条的 for足够了 stream太耗性能

        ArrayList<SDetailPO> sDetailPOS;
        // 一般每年的数据是256以内 没那么多老师。。
        ArrayList<S1Workload> s1Workloads = new ArrayList<>(256);
        ArrayList<S1234> s1234s = new ArrayList<>(256);

        for(Integer i : availableYears){
            sDetailPOS = s1Service.getS1ByYear(i);

            for (SDetailPO s1: sDetailPOS) {
                s1Workloads.add(new S1Workload(s1));
                s1234s.add(new S1234(s1));
            }
            EasyExcel.write(url+i+".xlsx", S1Workload.class)
                    .sheet("工作量")
                    .doWrite(s1Workloads);
            EasyExcel.write(url+i+".xlsx", S1234.class)
                    .sheet("汇总表")
                    .doWrite(s1234s);

            sDetailPOS.clear();
            s1Workloads.clear();
            s1234s.clear();
        }
        return new Result();
    }
}
