package top.kittygirl.controller;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.MapUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import top.kittygirl.common.ApplicationContextHelper;
import top.kittygirl.common.JsonData;
import top.kittygirl.dao.SysAclModuleMapper;
import top.kittygirl.exception.ParamException;
import top.kittygirl.exception.PermissionException;
import top.kittygirl.model.SysAclModule;
import top.kittygirl.param.TestVo;
import top.kittygirl.util.BeanValidator;
import top.kittygirl.util.JsonMapper;

import java.util.Map;


@Controller
@RequestMapping("/test")
@Slf4j
public class TestController {
    @RequestMapping("/hello.json")
    @ResponseBody
    public JsonData hello() {
        log.info("hello");
        //throw new PermissionException("text Exception");
        throw new RuntimeException();
        //return JsonData.success("Hello Permission!");
    }

    @RequestMapping("/validate.json")
    @ResponseBody
    public JsonData validate(TestVo vo) throws ParamException {
        log.info("validate");
        SysAclModuleMapper moduleMapper = ApplicationContextHelper.popBean(SysAclModuleMapper.class);
        SysAclModule module = moduleMapper.selectByPrimaryKey(1);
        log.info(JsonMapper.obj2String(module));
        BeanValidator.check(vo);
        return JsonData.success("test validate");

    }
}
