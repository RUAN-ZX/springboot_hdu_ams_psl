package cn.ryanalexander.psl.domain.bo.excel;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


/**
 * <p><b>Excel 用于匹配各列记录的实体类
 * 借鉴了好莱坞模式 各类负责自己的自检、转换以及存储操作
 * 因为每个excel实体类 存储操作 所需要的mapper service都不一定一样 操作也是</b></p>
 *
 * <p>2022-03-23 去掉ExcelEntity<T, E> E transform();
 * 因为没必要转换为真正数据库实体类 浪费性能 无端增大binlog 且不优雅</p>
 * <p>2022-03-23 设计isValidated 各个实体类负责自己的自检 防止excel读取null数据造成NPE</p>
 * @since 1.0.0
 * @author RyanAlexander 2022-03-23 11:09
 */
public interface ExcelEntity<T> {
    // 这两个必须实现！
    boolean isValidated();

    void transformAndSave(ArrayList<T> list, int size);


    default boolean multiContinue(){return false;}

    // 如果没有batchNotEnd变为true 这个方法压根就不会被调用！
    default ExcelEntity copyFromMasterMask(ExcelEntity data){return new S1CourseTheory();}

    // 默认空实现 省的麻烦
    default void fieldStandardized(){}

    // 默认不是特别指定的结果
    default boolean isSpecified(){return false;}

    // 默认不判定为多人课
    default boolean multiStart(){return false;}

    // 有些时候需要对头进行标准化计算 而且需要一些环境信息补全！
    default void stdCalculator(List<Map<Integer, String>> headInfoMap){}

    // 多个多人的情况下 是要累积mask 所以要把之前的拉进来
    default boolean prevIsMultiHeadOperation(ExcelEntity mask){
        return false;
    }

}
