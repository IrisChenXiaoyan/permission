package top.kittygirl.service;

import com.google.common.base.Preconditions;
import org.springframework.stereotype.Service;
import top.kittygirl.beans.PageQuery;
import top.kittygirl.beans.PageResult;
import top.kittygirl.dao.SysUserMapper;
import top.kittygirl.exception.ParamException;
import top.kittygirl.model.SysUser;
import top.kittygirl.param.UserParam;
import top.kittygirl.util.BeanValidator;
import top.kittygirl.util.MD5Util;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@Service
public class SysUserService {
    @Resource
    private SysUserMapper sysUserMapper;

    public void save(UserParam param) {
        BeanValidator.check(param);
        if (checkEmailExist(param.getMail(), param.getId()))
            throw new ParamException("邮箱地址已被占用");
        if (checkTelephoneExist(param.getTelephone(), param.getId()))
            throw new ParamException("电话号码已被占用");
        String password = "123456";//TODO:
        String encryptedPassword = MD5Util.encrypt(password);
        SysUser user = SysUser.builder().username(param.getUsername())
                .mail(param.getMail()).telephone(param.getTelephone())
                .deptId(param.getDeptId()).status(param.getStatus())
                .password(encryptedPassword).remark(param.getRemark()).build();
        user.setOperator("system");//TODO:
        user.setOperateIp("127.0.0.1");//TODO:
        user.setOperateTime(new Date());
        //TODO:send password email
        sysUserMapper.insertSelective(user);
    }

    public void update(UserParam param) {
        BeanValidator.check(param);
        if (checkEmailExist(param.getMail(), param.getId()))
            throw new ParamException("邮箱地址已被占用");
        if (checkTelephoneExist(param.getTelephone(), param.getId()))
            throw new ParamException("电话号码已被占用");
        SysUser before = sysUserMapper.selectByPrimaryKey(param.getId());
        Preconditions.checkNotNull(before, "待更新的用户不存在");
        SysUser after = SysUser.builder().id(param.getId()).username(param.getUsername())
                .mail(param.getMail()).telephone(param.getTelephone())
                .deptId(param.getDeptId()).status(param.getStatus())
                .remark(param.getRemark()).build();
        sysUserMapper.updateByPrimaryKeySelective(after);
    }

    public boolean checkEmailExist(String email, Integer userId) {
        return sysUserMapper.countByMail(email, userId) > 0;
    }
    public boolean checkTelephoneExist(String telephone, Integer userId) {
        return sysUserMapper.countByTelephone(telephone, userId) > 0;
    }

    public SysUser findByKeyWord(String keyword) {
        return sysUserMapper.findByKeyWord(keyword);
    }

    public PageResult<SysUser> getPageByDeptId(int deptId, PageQuery page) {
        BeanValidator.check(page);
        int count = sysUserMapper.countByDeptId(deptId);
        if (count > 0) {
            List<SysUser> list = sysUserMapper.getPageByDeptId(deptId, page);
            return PageResult.<SysUser>builder().data(list).total(count).build();
        }
        return PageResult.<SysUser>builder().build();
    }

}
