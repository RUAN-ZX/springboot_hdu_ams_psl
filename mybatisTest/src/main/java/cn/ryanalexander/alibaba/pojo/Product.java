package cn.ryanalexander.alibaba.pojo;

import cn.ryanalexander.alibaba.enums.SexEnum;
import com.baomidou.mybatisplus.annotation.Version;
import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.OutputFile;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;
import lombok.Data;
import org.junit.Test;

import java.util.Collections;

/**
 * Date:2022/2/15
 * Author:ybc
 * Description:
 */
@Data
public class Product {
    private Long id;
    private String name;
    private Integer price;
    @Version //标识乐观锁版本号字段
    private Integer version;

    private boolean delete;
}


