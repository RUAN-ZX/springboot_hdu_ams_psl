package cn.ryanalexander.psl.domain.bo.excel;

import cn.ryanalexander.common.domain.exceptions.AppException;
import cn.ryanalexander.common.domain.exceptions.ExceptionInfo;
import cn.ryanalexander.common.domain.exceptions.code.ErrorCode;
import cn.ryanalexander.common.domain.exceptions.code.SubjectEnum;
import cn.ryanalexander.psl.mapper.AccountMapper;
import cn.ryanalexander.psl.service.tool.DataUtil;
import cn.ryanalexander.psl.service.tool.SpringUtil;
import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.ArrayList;

/**
 * <p><b></b></p>

 * <p>2022-03-23 </p>

 * @since 1.0.0
 * @see com.alibaba.excel.converters.Converter
 * @author RyanAlexander 2022-03-23 11:20
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel("工号和邮箱")
@ToString
@ExcelIgnoreUnannotated
public class AccountIdAndEmail implements ExcelEntity<AccountIdAndEmail> {
    @ExcelProperty(value = "教工号")
    public Integer accountId;

    @ExcelProperty(value = "姓名")
    public String accountName;

    @ExcelProperty(value = "电子邮箱")
    public String accountMail;

    public AccountIdAndEmail(TitleInfo titleInfo){
        accountId = DataUtil.string2integer(titleInfo.getTeacherId());
        accountName = DataUtil.getChineseCharacter(titleInfo.getTeacherName());
    }
    @Override
    public boolean isValidated() {
        return accountId != null && accountMail != null;
    }

    @Override
    public void fieldStandardized() {
        this.accountName = DataUtil.getChineseCharacter(this.accountName);
    }

    @Override
    public void transformAndSave(ArrayList<AccountIdAndEmail> list, int size) {
        AccountMapper accountMapper = (AccountMapper) SpringUtil.getBean("accountMapper");
        try{
            for (AccountIdAndEmail account : list) {
                account.fieldStandardized();
            }
            accountMapper.saveOrUpdateBatchByMail(list);
        }
        catch (AppException e){
            ExceptionInfo exceptionInfo = new ExceptionInfo(
                    "saveOrUpdateBatchByMail", "MySQL disconnected? NPE?",
                    "accountMapper.saveOrUpdateBatchByMail");
            throw new AppException(e, exceptionInfo, new ErrorCode(SubjectEnum.USER));
        }
    }
}
