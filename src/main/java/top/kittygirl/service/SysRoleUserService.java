package top.kittygirl.service;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import top.kittygirl.common.RequestHolder;
import top.kittygirl.dao.SysRoleUserMapper;
import top.kittygirl.dao.SysUserMapper;
import top.kittygirl.model.SysRoleUser;
import top.kittygirl.model.SysUser;
import top.kittygirl.util.IpUtil;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Service
public class SysRoleUserService {
    @Resource
    private SysRoleUserMapper sysRoleUserMapper;
    @Resource
    private SysUserMapper sysUserMapper;

    public List<SysUser> getUserListByRoleId(int roleId) {
        List<Integer> userIdList = sysRoleUserMapper.getUserIdListByRoleId(roleId);

        if (CollectionUtils.isEmpty(userIdList))
            return Lists.newArrayList();
        return sysUserMapper.getByIdList(userIdList);
    }

    public void changeRoleUsers(Integer roleId, List<Integer> userIdList) {
        List<Integer> originUserIdList = sysRoleUserMapper.getUserIdListByRoleId(roleId);
        if (userIdList.size() == originUserIdList.size()) {
            Set<Integer> userIdSet = Sets.newHashSet(userIdList);
            Set<Integer> originUserIdSet = Sets.newHashSet(originUserIdList);
            originUserIdSet.removeAll(userIdSet);
            if (CollectionUtils.isEmpty(originUserIdSet))
                return;
        }
        updateRoleUsers(roleId, userIdList);
    }

    @Transactional
    public void updateRoleUsers(Integer roleId, List<Integer> userIdList) {
        sysRoleUserMapper.deleteByRoleId(roleId);
        if (CollectionUtils.isEmpty(userIdList))
            return;
        List<SysRoleUser> roleUserList = Lists.newArrayList();
        for (Integer userId : userIdList) {
            SysRoleUser roleUser = SysRoleUser.builder().roleId(roleId).userId(userId)
                    .operator(RequestHolder.getCurrentUser().getUsername())
                    .operateTime(new Date())
                    .operateIp(IpUtil.getRemoteIp(RequestHolder.getCurrentRequest())).build();
            roleUserList.add(roleUser);
        }
        sysRoleUserMapper.batchInsert(roleUserList);
    }
}
