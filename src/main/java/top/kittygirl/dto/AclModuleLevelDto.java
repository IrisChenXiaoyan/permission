package top.kittygirl.dto;

import com.google.common.collect.Lists;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.beans.BeanUtils;
import top.kittygirl.model.SysAclModule;

import java.util.List;

@Getter
@Setter
@ToString
public class AclModuleLevelDto extends SysAclModule {

    private List<AclModuleLevelDto> aclModuleList = Lists.newArrayList();

    private List<AclDto> aclList = Lists.newArrayList();

    public static AclModuleLevelDto adapt(SysAclModule sysAclModule) {
        AclModuleLevelDto dto = new AclModuleLevelDto();
        BeanUtils.copyProperties(sysAclModule, dto);
        return dto;
    }
}
