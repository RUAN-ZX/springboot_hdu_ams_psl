package cn.ryanalexander.alibaba.entity.excel;

import java.util.ArrayList;
import java.util.List;

public interface ExcelEntity {
    public Object transformAndSave(ArrayList<?> list);
}
