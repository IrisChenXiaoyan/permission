package top.kittygirl.service;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Lists;
import com.google.common.collect.Multimap;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;
import top.kittygirl.dao.SysDeptMapper;
import top.kittygirl.dto.DeptLevelDto;
import top.kittygirl.model.SysDept;
import top.kittygirl.util.LevelUtil;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@Service
public class SysTreeService {
    @Resource
    SysDeptMapper sysDeptMapper;

    public List<DeptLevelDto> deptTree() {
        List<SysDept> deptList = sysDeptMapper.getAllDept();

        List<DeptLevelDto> dtoList = Lists.newArrayList();
        for (SysDept dept : deptList) {
            DeptLevelDto dto = DeptLevelDto.adapt(dept);
            dtoList.add(dto);
        }
        return deptListToTree(dtoList);
    }

    public List<DeptLevelDto> deptListToTree(List<DeptLevelDto> deptLevelList) {
        if (CollectionUtils.isEmpty(deptLevelList)) {
            return Lists.newArrayList();
        }
        Multimap<String, DeptLevelDto> levelDeptmap = ArrayListMultimap.create();
        List<DeptLevelDto> rootList = Lists.newArrayList();
        for (DeptLevelDto dto : deptLevelList) {
            levelDeptmap.put(dto.getLevel(), dto);
            if (LevelUtil.ROOT.equals(dto.getLevel()))
                rootList.add(dto);
        }

        Collections.sort(rootList, deptSeqComparator);
        transformDeptTree(rootList, LevelUtil.ROOT, levelDeptmap);
        return rootList;

    }

    public void transformDeptTree(List<DeptLevelDto> curLevelList, String level, Multimap<String, DeptLevelDto> levelDeptmap) {
        for (int i = 0; i < curLevelList.size(); i++) {
            DeptLevelDto deptLevelDto = curLevelList.get(i);
            String nextLevel = LevelUtil.calculateLevel(level, deptLevelDto.getId());
            List<DeptLevelDto> nextLevelList = (List<DeptLevelDto>)levelDeptmap.get(nextLevel);
            if (CollectionUtils.isNotEmpty(nextLevelList)) {
                Collections.sort(nextLevelList, deptSeqComparator);
                deptLevelDto.setDeptList(nextLevelList);
                transformDeptTree(nextLevelList, nextLevel, levelDeptmap);
            }
        }
    }

    public Comparator<DeptLevelDto> deptSeqComparator = new Comparator<DeptLevelDto>() {
        @Override
        public int compare(DeptLevelDto o1, DeptLevelDto o2) {
            return o1.getSeq() - o2.getSeq();
        }
    };

}
