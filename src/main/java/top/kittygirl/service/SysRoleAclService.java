package top.kittygirl.service;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import top.kittygirl.common.RequestHolder;
import top.kittygirl.dao.SysRoleAclMapper;
import top.kittygirl.model.SysRoleAcl;
import top.kittygirl.util.IpUtil;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Service
public class SysRoleAclService {
    @Resource
    private SysRoleAclMapper sysRoleAclMapper;

    public void changeRoleAcls(Integer roleId, List<Integer> aclIdList) {
        List<Integer> originAclIdList = sysRoleAclMapper.getAclIdListByRoleIdList(Lists.newArrayList(roleId));
        if (aclIdList.size() == originAclIdList.size()) {
            Set<Integer> aclIdSet = Sets.newHashSet(aclIdList);
            Set<Integer> originAclIdSet = Sets.newHashSet(originAclIdList);
            originAclIdSet.removeAll(aclIdSet);
            if (CollectionUtils.isEmpty(originAclIdSet))
                return;
        }
        updateRoleAcls(roleId, aclIdList);
    }

    @Transactional
    public void updateRoleAcls(Integer roleId, List<Integer> aclIdList) {
        sysRoleAclMapper.deleteByRoleId(roleId);
        if (CollectionUtils.isEmpty(aclIdList))
            return;
        List<SysRoleAcl> roleAclList = Lists.newArrayList();
        for (Integer aclId : aclIdList) {
            SysRoleAcl roleAcl  = SysRoleAcl.builder().roleId(roleId).aclId(aclId)
                    .operator(RequestHolder.getCurrentUser().getUsername())
                    .operateTime(new Date())
                    .operateIp(IpUtil.getRemoteIp(RequestHolder.getCurrentRequest())).build();
            roleAclList.add(roleAcl);
        }
        sysRoleAclMapper.batchInsert(roleAclList);
    }
}
