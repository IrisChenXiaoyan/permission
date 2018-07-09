package top.kittygirl.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import top.kittygirl.common.JsonData;
import top.kittygirl.param.AclModuleParam;
import top.kittygirl.service.SysAclModuleService;
import top.kittygirl.service.SysTreeService;

import javax.annotation.Resource;

@Controller
@RequestMapping("/sys/aclModule")
public class SysAclModuleController {
    @Resource
    SysTreeService sysTreeService;
    @Resource
    SysAclModuleService sysAclModuleService;

    @RequestMapping("/acl.page")
    public ModelAndView page() {
        return new ModelAndView("acl");
    }

    @RequestMapping("/save.json")
    @ResponseBody
    public JsonData saveAclModule(AclModuleParam param) {
        sysAclModuleService.save(param);
        return JsonData.success();
    }

    @RequestMapping("/update.json")
    @ResponseBody
    public JsonData updateAclModule(AclModuleParam param) {
        sysAclModuleService.update(param);
        return JsonData.success();
    }

    @RequestMapping("/tree.json")
    @ResponseBody
    public JsonData tree() {
        return JsonData.success(sysTreeService.aclModuleTree());
    }
}
