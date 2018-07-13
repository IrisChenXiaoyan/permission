package top.kittygirl.service;

import com.google.common.collect.Lists;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.codehaus.jackson.type.TypeReference;
import org.springframework.stereotype.Service;
import top.kittygirl.beans.CacheKeyConstants;
import top.kittygirl.common.RequestHolder;
import top.kittygirl.dao.SysAclMapper;
import top.kittygirl.dao.SysRoleAclMapper;
import top.kittygirl.dao.SysRoleUserMapper;
import top.kittygirl.model.SysAcl;
import top.kittygirl.model.SysUser;
import top.kittygirl.util.JsonMapper;

import javax.annotation.Resource;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class SysCoreService {
    @Resource
    private SysAclMapper sysAclMapper;
    @Resource
    private SysRoleUserMapper sysRoleUserMapper;
    @Resource
    private SysRoleAclMapper sysRoleAclMapper;
    @Resource
    private SysCacheService sysCacheService;

    public List<SysAcl> getCurrentUserAclList() {
        int userId = RequestHolder.getCurrentUser().getId();
        return getUserAclList(userId);
    }

    public List<SysAcl> getCurrentUserAclListFromCache() {
        int userId = RequestHolder.getCurrentUser().getId();
        String cacheValue = sysCacheService.getFromCache(CacheKeyConstants.USER_ACLS,
                String.valueOf(userId));
        if (StringUtils.isBlank(cacheValue)) {
            List<SysAcl> aclList = getCurrentUserAclList();
            if (CollectionUtils.isNotEmpty(aclList)) {
                sysCacheService.saveCache(JsonMapper.obj2String(aclList), 600, CacheKeyConstants.USER_ACLS,
                        String.valueOf(userId));
            }
            return aclList;
        }
        return JsonMapper.String2Obj(cacheValue, new TypeReference<List<SysAcl>>() {
        });
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
        SysUser sysUser = RequestHolder.getCurrentUser();
        if (sysUser.getUsername().equals("Admin")){
            return true;
        }
        return false;
    }

    public boolean hasUrlAcl(String url) {
        if (isSuperAdmin())
            return true;
        List<SysAcl> aclList = sysAclMapper.getByUrl(url);
        if (CollectionUtils.isEmpty(aclList))
            return true;

        List<SysAcl> userAclList = getCurrentUserAclListFromCache();
        Set<Integer> userAclIdSet = userAclList.stream().map(acl -> acl.getId()).collect(Collectors.toSet());
        //规则：只要该用户拥有其中一个权限点，就认为有访问权限
        boolean hasValidAcl = false;
        for (SysAcl acl :aclList) {
            if (acl == null || acl.getStatus() != 1) {
                continue;
            }
            hasValidAcl = true;
            if (userAclIdSet.contains(acl.getId()))
                return true;
        }
        if (!hasValidAcl)
            return true;
        return false;
    }
}
