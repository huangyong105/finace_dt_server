package com.roncoo.eshop.manager;


import com.roncoo.eshop.DTO.ProjectManagementDTO;
import com.roncoo.eshop.converter.BeanConverter;
import com.roncoo.eshop.mapper.ProjectManagementMapper;
import com.roncoo.eshop.model.ProjectManagementDO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 投资项目相关
 *
 * @author dongyuan
 * @date 2019-03-22 11:42
 **/
@Service
public class InvestmentManager {
    @Autowired
    ProjectManagementMapper projectManagementMapper;

    public List<ProjectManagementDTO> getAllInvestmentProject(){
        List<ProjectManagementDO> list = projectManagementMapper.selectAllPrimary();
        List<ProjectManagementDTO> projectManagementDTOS = BeanConverter.batchConvert(list, ProjectManagementDTO.class);
        return projectManagementDTOS;
    }

    public ProjectManagementDTO getInvestmentProject(Integer id){
        ProjectManagementDO projectManagementDO = projectManagementMapper.selectByPrimaryKey(id);
        ProjectManagementDTO projectManagementDTO = BeanConverter.convert(projectManagementDO, ProjectManagementDTO.class);
        return projectManagementDTO;
    }
}
