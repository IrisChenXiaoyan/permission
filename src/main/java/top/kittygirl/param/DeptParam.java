package top.kittygirl.param;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@ToString
public class DeptParam {

    private Integer id;

    @NotBlank(message = "部门名称不能为空")
    @Length(max = 15, min = 2, message = "部门名称长度在2-15之间")
    private String name;

    private Integer parentId = 0;

    @NotNull(message = "部门顺序不能为空")
    private Integer seq;

    @Length(max = 150, message = "备注长度在150以内")
    private String remark;
}
