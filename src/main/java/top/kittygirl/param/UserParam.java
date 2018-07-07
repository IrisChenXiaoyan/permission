package top.kittygirl.param;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class UserParam {
    private Integer id;
    @NotBlank(message = "用户名不可以为空")
    @Length(min = 1, max = 20, message = "用户名长度在1-20之间")
    private String username;
    @NotBlank(message = "电话号码不可以为空")
    @Length(min = 1, max = 13, message = "电话号码长度在1-13之间")
    private String telephone;
    @NotBlank(message = "邮箱地址不可以为空")
    @Length(min = 5, max = 20, message = "邮箱地址长度在5-20之间")
    private String mail;
    @NotNull(message = "必须指定用户所在部门")
    private Integer deptId;
    @NotNull(message = "必须指定用户状态")
    @Min(value = 0, message = "用户状态不合法")
    @Max(value = 2, message = "用户状态不合法")
    private Integer status;
    @Length(min = 0, max = 200, message = "备注长度需要在200以内")
    private String remark = "";
}
