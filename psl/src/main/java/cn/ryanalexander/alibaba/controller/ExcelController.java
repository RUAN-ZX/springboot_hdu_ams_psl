package cn.ryanalexander.alibaba.controller;

import cn.ryanalexander.alibaba.domain.bo.excel.*;
import cn.ryanalexander.alibaba.domain.dto.Result;
import cn.ryanalexander.alibaba.domain.exceptions.AppException;
import cn.ryanalexander.alibaba.domain.exceptions.code.ErrorCodeEnum;
import cn.ryanalexander.alibaba.service.excel.ExcelService;
import cn.ryanalexander.alibaba.service.tool.StaticConfiguration;
import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelReader;
import com.alibaba.excel.read.metadata.ReadSheet;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
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

    private static final HashMap<String, Class<?>> sheetAndExcelEntity = new HashMap<>();
    static{
        // todo 这里 顺序问题需要保证 说白了 教师个人信息最先登记才对 之后别的信息再登
//        sheetAndExcelEntity.put("工号和邮箱", AccountIdAndEmail.class);
        sheetAndExcelEntity.put("理论", CourseTheory.class);
//        sheetAndExcelEntity.put("短学期", CourseShortTerm.class);
//        sheetAndExcelEntity.put("毕业设计", CourseThesisDesign.class);
    }
    @ApiOperation("更新Excel")
    @GetMapping("/updateExcel")
    public Result updateExcel(){
        String url = StaticConfiguration.getExcelUrl();
        ExcelReader excelReader = null;
        String chineseRegex = "([\u4e00-\u9fa5]+)";
        // 多年的表 故需要匹配名字 然后执行对应的套路

        try {
            excelReader = EasyExcel.read(url).build();
            List<ReadSheet> sheets = excelReader.excelExecutor().sheetList();
            for (ReadSheet sheet : sheets) {
                Matcher matcher = Pattern.compile(chineseRegex).matcher(sheet.getSheetName());
                String matcherResult = null;
                Class<?> excelEntity = null;
                if(matcher.find()) {
                    matcherResult = matcher.group(1);
                    excelEntity = sheetAndExcelEntity.get(matcherResult);
                }

                if(excelEntity != null){
                    ReadSheet readSheet = EasyExcel.readSheet(matcherResult)
                            .headRowNumber(2)
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
}
