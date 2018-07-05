package top.kittygirl.dto;

import com.google.common.collect.Lists;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.beans.BeanUtils;
import top.kittygirl.model.SysDept;

import java.util.List;

@Getter
@Setter
@ToString
public class DeptLevelDto extends SysDept {
    private List<DeptLevelDto> deptList = Lists.newArrayList();

    public static DeptLevelDto adapt(SysDept dept) {
        DeptLevelDto dto = new DeptLevelDto();
        BeanUtils.copyProperties(dept, dto);
        return dto;
    }

}
