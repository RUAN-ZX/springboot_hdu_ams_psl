package cn.ryanalexander.common.domain.exceptions.code;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * <p><b></b></p>
 *
 * <p>2022/3/24 </p>
 *
 * @author ryan 2022/3/24 12:34
 * @since 1.0.0
 **/
@Data
@NoArgsConstructor
public class ErrorCode {
    private SubjectEnum subject = SubjectEnum.OK;
    private BehaviorEnum behavior = BehaviorEnum.OK;
    private PropertyEnum property = PropertyEnum.OK;
    private ResultEnum result = ResultEnum.OK;

    // todo 后期再实现这么复杂的code
    public static int getOK(){
        return 0;
    }
    public int getCode(){
        return subject.getCode();
//        StringBuilder sb = new StringBuilder();
//        sb.append(this.subject.getCode());
//        sb.append(this.behavior.getCode());
//        sb.append(this.property.getCode());
//        sb.append(this.result.getCode());
    }
    public String getMsg(){
        return subject.getDescription();
    }

    public ErrorCode(SubjectEnum subject){
        this.subject = subject;
    }
}
