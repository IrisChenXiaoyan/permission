package top.kittygirl.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import top.kittygirl.common.JsonData;
import top.kittygirl.dto.DeptLevelDto;
import top.kittygirl.param.DeptParam;
import top.kittygirl.service.SysDeptService;
import top.kittygirl.service.SysTreeService;

import javax.annotation.Resource;
import java.util.List;

@Controller
@Slf4j
@RequestMapping("/sys/dept")
public class SysDeptController {
    @Resource
    SysDeptService sysDeptService;
    @Resource
    SysTreeService sysTreeService;

    @RequestMapping("/save.json")
    @ResponseBody
    public JsonData saveDept(DeptParam param) {
        sysDeptService.save(param);
        return JsonData.success();
    }

    @RequestMapping("/tree.json")
    @ResponseBody
    public JsonData tree() {
        List<DeptLevelDto> dtoList = sysTreeService.deptTree();
        return JsonData.success(dtoList);
    }

    @RequestMapping("/update.json")
    @ResponseBody
    public JsonData updateDept(DeptParam param) {
        sysDeptService.update(param);
        return JsonData.success();
    }
}
