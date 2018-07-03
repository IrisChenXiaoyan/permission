package top.kittygirl.param;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class TestVo {
    @NotBlank
    private String msg;
    @NotNull
    @Max(value = 10, message = "最大值为0")
    @Min(0)
    private Integer id;
}
