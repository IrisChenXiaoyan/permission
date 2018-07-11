package top.kittygirl.service;

import com.google.common.collect.Lists;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;
import top.kittygirl.common.RequestHolder;
import top.kittygirl.dao.SysAclMapper;
import top.kittygirl.dao.SysRoleAclMapper;
import top.kittygirl.dao.SysRoleUserMapper;
import top.kittygirl.model.SysAcl;

import javax.annotation.Resource;
import java.util.List;

@Service
public class SysCoreService {
    @Resource
    private SysAclMapper sysAclMapper;
    @Resource
    private SysRoleUserMapper sysRoleUserMapper;
    @Resource
    private SysRoleAclMapper sysRoleAclMapper;

    public List<SysAcl> getCurrentUserAclList() {
        int userId = RequestHolder.getCurrentUser().getId();
        return getUserAclList(userId);
    }

    public List<SysAcl> getRoleAclList(int roleId) {
        List<Integer> aclIdList = sysRoleAclMapper.getAclIdListByRoleIdList(Lists.<Integer>newArrayList(roleId));
        if (CollectionUtils.isEmpty(aclIdList))
            return Lists.newArrayList();
        return sysAclMapper.getByIdList(aclIdList);
    }

    public List<SysAcl> getUserAclList(int userId) {
        if (isSuperAdmin())
            return sysAclMapper.getAll();
        List<Integer> userRoleIdList = sysRoleUserMapper.getRoleIdListByUserId(userId);
        if (CollectionUtils.isEmpty(userRoleIdList))
            return Lists.newArrayList();
        List<Integer> userAclIdList = sysRoleAclMapper.getAclIdListByRoleIdList(userRoleIdList);
        if (CollectionUtils.isEmpty(userAclIdList))
            return Lists.newArrayList();
        return sysAclMapper.getByIdList(userAclIdList);
    }

    public boolean isSuperAdmin() {
        return true;
    }
}
