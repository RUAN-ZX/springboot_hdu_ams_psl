package generator.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Data;

/**
 * 
 * @TableName teacher
 */
@TableName(value ="teacher")
@Data
public class Teacher implements Serializable {
    /**
     * 
     */
    @TableId
    private Integer t_id;

    /**
     * 
     */
    private String t_name;

    /**
     * 
     */
    private String t_mail;

    /**
     * 
     */
    private String t_phone;

    /**
     * 
     */
    private String t_pwd;

    /**
     * 
     */
    private Integer t_ac_id;

    /**
     * 
     */
    private String t_team;

    /**
     * 
     */
    private String t_type;

    /**
     * 
     */
    private String t_title;

    /**
     * 
     */
    private Integer t_title_level;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

    @Override
    public boolean equals(Object that) {
        if (this == that) {
            return true;
        }
        if (that == null) {
            return false;
        }
        if (getClass() != that.getClass()) {
            return false;
        }
        Teacher other = (Teacher) that;
        return (this.getT_id() == null ? other.getT_id() == null : this.getT_id().equals(other.getT_id()))
            && (this.getT_name() == null ? other.getT_name() == null : this.getT_name().equals(other.getT_name()))
            && (this.getT_mail() == null ? other.getT_mail() == null : this.getT_mail().equals(other.getT_mail()))
            && (this.getT_phone() == null ? other.getT_phone() == null : this.getT_phone().equals(other.getT_phone()))
            && (this.getT_pwd() == null ? other.getT_pwd() == null : this.getT_pwd().equals(other.getT_pwd()))
            && (this.getT_ac_id() == null ? other.getT_ac_id() == null : this.getT_ac_id().equals(other.getT_ac_id()))
            && (this.getT_team() == null ? other.getT_team() == null : this.getT_team().equals(other.getT_team()))
            && (this.getT_type() == null ? other.getT_type() == null : this.getT_type().equals(other.getT_type()))
            && (this.getT_title() == null ? other.getT_title() == null : this.getT_title().equals(other.getT_title()))
            && (this.getT_title_level() == null ? other.getT_title_level() == null : this.getT_title_level().equals(other.getT_title_level()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getT_id() == null) ? 0 : getT_id().hashCode());
        result = prime * result + ((getT_name() == null) ? 0 : getT_name().hashCode());
        result = prime * result + ((getT_mail() == null) ? 0 : getT_mail().hashCode());
        result = prime * result + ((getT_phone() == null) ? 0 : getT_phone().hashCode());
        result = prime * result + ((getT_pwd() == null) ? 0 : getT_pwd().hashCode());
        result = prime * result + ((getT_ac_id() == null) ? 0 : getT_ac_id().hashCode());
        result = prime * result + ((getT_team() == null) ? 0 : getT_team().hashCode());
        result = prime * result + ((getT_type() == null) ? 0 : getT_type().hashCode());
        result = prime * result + ((getT_title() == null) ? 0 : getT_title().hashCode());
        result = prime * result + ((getT_title_level() == null) ? 0 : getT_title_level().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", t_id=").append(t_id);
        sb.append(", t_name=").append(t_name);
        sb.append(", t_mail=").append(t_mail);
        sb.append(", t_phone=").append(t_phone);
        sb.append(", t_pwd=").append(t_pwd);
        sb.append(", t_ac_id=").append(t_ac_id);
        sb.append(", t_team=").append(t_team);
        sb.append(", t_type=").append(t_type);
        sb.append(", t_title=").append(t_title);
        sb.append(", t_title_level=").append(t_title_level);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}