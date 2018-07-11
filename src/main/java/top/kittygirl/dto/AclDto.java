package top.kittygirl.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.beans.BeanUtils;
import top.kittygirl.model.SysAcl;

@Getter
@Setter
@ToString
public class AclDto extends SysAcl{
    private boolean checked = false;//该权限点是否默认选中

    private boolean hasAcl = false;//该用户是否有该权限

    public static AclDto adapt(SysAcl acl) {
        AclDto dto = new AclDto();
        BeanUtils.copyProperties(acl, dto);
        return dto;
    }
}
