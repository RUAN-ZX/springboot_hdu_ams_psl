package cn.ryanalexander.alibaba.controller;

import cn.ryanalexander.alibaba.domain.bo.excel.*;
import cn.ryanalexander.alibaba.domain.bo.excel.out.S1234;
import cn.ryanalexander.alibaba.domain.bo.excel.out.S1Workload;
import cn.ryanalexander.alibaba.domain.bo.excel.out.SFinal;
import cn.ryanalexander.alibaba.domain.dto.Result;
import cn.ryanalexander.alibaba.domain.po.SDetailPO;
import cn.ryanalexander.alibaba.service.S1Service;
import cn.ryanalexander.alibaba.service.excel.ExcelService;
import cn.ryanalexander.alibaba.service.excel.ModalDataListener;
import cn.ryanalexander.alibaba.service.excel.NoModelDataListener;
import cn.ryanalexander.alibaba.service.tool.StaticConfiguration;
import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelReader;
import com.alibaba.excel.read.metadata.ReadSheet;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
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
@RequestMapping("/excel")
public class ExcelController {
    @Resource
    private StaticConfiguration StaticConfiguration;

    @Resource
    private ExcelService excelService;


    @ApiOperation("更新Excel")
    @GetMapping("/update")
    public Result updateExcel(){
        String url = StaticConfiguration.getExcelReadUrl();
        excelService.modelRead(url);
        excelService.noModelRead(url);
        return new Result();
    }

    @Resource
    private S1Service s1Service;
    // todo 范围内的年都输出出来 需要检查 没有就不输出 一年一个excel！
    // getAll意味输出所有 不管前面的参数了！
    @ApiOperation("获取生成的Excel")
    @GetMapping("/get")
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
                // todo 两个数据怎么合并 打算多表 以职称表为准 搞hashMap 其他表往里边填充！
                // 这里目前放着 逻辑有点复杂
            }
            EasyExcel.write(url+i+".xlsx", S1Workload.class)
                    .sheet("工作量")
                    .doWrite(s1Workloads);
            EasyExcel.write(url+i+".xlsx", S1234.class)
                    .sheet("汇总表")
                    .doWrite(s1234s);
            // todo 这里不能使用实体类方式写了 只能是根据数组数据写出来
            sDetailPOS.clear();
            s1Workloads.clear();
            s1234s.clear();
        }
        return new Result();
    }
}
