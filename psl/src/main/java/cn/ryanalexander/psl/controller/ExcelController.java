package cn.ryanalexander.psl.controller;

import cn.ryanalexander.psl.domain.bo.excel.out.S1234;
import cn.ryanalexander.psl.domain.bo.excel.out.S1Workload;
import cn.ryanalexander.psl.domain.dto.Result;
import cn.ryanalexander.psl.domain.po.SDetailPO;
import cn.ryanalexander.psl.service.S1DetailService;
import cn.ryanalexander.psl.service.excel.ExcelService;
import cn.ryanalexander.psl.service.tool.StaticConfiguration;
import com.alibaba.excel.EasyExcel;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.text.SimpleDateFormat;
import java.util.*;

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
    private StaticConfiguration staticConfiguration;

    @Resource
    private ExcelService excelService;

    @Resource
    private S1DetailService s1DetailService;
    // todo 范围内的年都输出出来 需要检查 没有就不输出 一年一个excel！
    // getAll意味输出所有 不管前面的参数了！


//    @Require(RoleEnum.MANAGER)
    @ApiOperation("上传Excel并更新数据")
    @PostMapping (value = "/update",
            headers = "content-type=multipart/form-data",
            produces = "application/json;charset=utf-8")
    public Result update(
            @RequestPart("uploadFile") MultipartFile[] multipartFiles
    ) throws IOException {
        for(MultipartFile file : multipartFiles){
            excelService.modelRead(file.getInputStream());
        }

//        excelService.noModelRead(url);
        return new Result();
    }

    @ApiOperation("用现有的excel表")
    @GetMapping (value = "/update")
    public Result update(int start, int end) throws IOException {
        excelService.noModelRead(start, end);
        return new Result();
    }

//    @Require(RoleEnum.MANAGER)
    @ApiOperation("获取生成的Excel")
    @GetMapping("/get")
    public Result getExcel(List<Integer> years, boolean isGetAll) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        String url = staticConfiguration.getExcelWriteUrl();
        List<Integer> availableYears = new ArrayList<>(years);
        availableYears.retainAll(s1DetailService.getAllYears());// 底层就是for+contain
        // 大规模数据并集采用stream 但是这种撑死20条的 for足够了 stream太耗性能

        ArrayList<SDetailPO> sDetailPOS;
        // 一般每年的数据是256以内 没那么多老师。。
        ArrayList<S1Workload> s1Workloads = new ArrayList<>(256);
        ArrayList<S1234> s1234s = new ArrayList<>(256);

        for(Integer i : availableYears){
//            sDetailPOS = s1DetailService.getS1DetailByYear(i);
//
//            for (SDetailPO s1: sDetailPOS) {
//                s1Workloads.add(new S1Workload(s1));
//                s1234s.add(new S1234(s1));
//                // todo 两个数据怎么合并 打算多表 以职称表为准 搞hashMap 其他表往里边填充！
//                // 这里目前放着 逻辑有点复杂
//            }
            EasyExcel.write(url+i+".xlsx", S1Workload.class)
                    .sheet("工作量")
                    .doWrite(s1Workloads);
            EasyExcel.write(url+i+".xlsx", S1234.class)
                    .sheet("汇总表")
                    .doWrite(s1234s);
            // todo 这里不能使用实体类方式写了 只能是根据数组数据写出来
//            sDetailPOS.clear();
            s1Workloads.clear();
            s1234s.clear();
        }
        return new Result();
    }


    //@RequestParam指向前端input file的name,加入HttpServletRequest请求
    @ApiOperation("上传excel并保存")
    @PostMapping(value = "/upload",
            headers = "content-type=multipart/form-data",
            produces = "application/json;charset=utf-8")
    public String upload(@RequestParam("uploadFile") MultipartFile[] multipartFiles, HttpServletRequest request) throws IOException {
        //设置当前日期
        String uploadDate= new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        //设置文件上传保存文件路径：保存在项目运行目录下的uploadFile文件夹+当前日期
        String savePath = staticConfiguration.getExcelWriteUrl()+"/"+uploadDate;
        //创建文件夹,当文件夹不存在时，创建文件夹
        File folder = new File(savePath);
        if(!folder.isDirectory()) folder.mkdir();

        //建立一个listmap的返回参数
        List<Map<String,Object>> listMap =new ArrayList<>();
        //建立一个循环分别接收多文件
        for(MultipartFile file : multipartFiles){
            //重命名上传的文件,为避免重复,我们使用UUID对文件分别进行命名
            String originalFilename=file.getOriginalFilename();//getOriginalFilename()获取文件名带后缀
            //UUID去掉中间的"-",并将原文件后缀名加入新文件
            String newName= UUID.randomUUID().toString().replace("-","")
                    +originalFilename;
            //建立每一个文件上传的返回参数
            Map<String,Object> map=new HashMap<>();
            //文件保存操作
            file.transferTo(new File(folder,newName));
            //建立新文件路径,在前端可以直接访问如http://localhost:8080/uploadFile/2021-07-16/新文件名(带后缀)
            String filepath=request.getScheme()+"://"+request.getServerName()+":"+
                    request.getServerPort()+"/uploadFile/"+uploadDate+"/"+newName;
            //写入返回参数
            map.put("[oldname]",originalFilename);
            map.put("[newname]",newName);
            map.put("[filepath]",filepath);
            map.put("[result]","成功!");
            listMap.add(map);
        }
        //将执行结果返回
        return listMap.toString();
    }

}
