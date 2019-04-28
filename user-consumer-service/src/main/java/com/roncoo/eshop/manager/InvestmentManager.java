package com.roncoo.eshop.manager;


import cn.com.taiji.DTO.ProjectManagementDTO;
import cn.com.taiji.page.PageInfo;
import cn.com.taiji.page.PageResult;
import com.github.pagehelper.PageHelper;
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

    public List<ProjectManagementDTO> getInvestmentProjectList(){
        List<ProjectManagementDO> list = projectManagementMapper.selectPrimaryList();
        List<ProjectManagementDTO> projectManagementDTOS = BeanConverter.batchConvert(list, ProjectManagementDTO.class);
        return projectManagementDTOS;
    }

    public ProjectManagementDTO getInvestmentProject(Long id){
        ProjectManagementDO projectManagementDO = projectManagementMapper.selectByPrimaryKey(id);
        ProjectManagementDTO projectManagementDTO = BeanConverter.convert(projectManagementDO, ProjectManagementDTO.class);
        return projectManagementDTO;
    }

    public void saveInvestmentProject(ProjectManagementDTO projectManagementDTO){
        ProjectManagementDO projectManagementDO = BeanConverter.convert(projectManagementDTO, ProjectManagementDO.class);
        projectManagementMapper.insertInvestment(projectManagementDO);
    }

    public PageResult<ProjectManagementDTO> getAllInvestmentProject(Integer currentPage,Integer pageSize){
        PageHelper.startPage(currentPage, pageSize);
        List<ProjectManagementDO> list = projectManagementMapper.selectAllPrimary();
        com.github.pagehelper.PageInfo pageInfo = new com.github.pagehelper.PageInfo(list);
        List<ProjectManagementDTO> projectManagementDTOS = BeanConverter.batchConvert(list, ProjectManagementDTO.class);
        PageInfo pageInfo1 = new PageInfo();
        pageInfo1.setCurrentPage(currentPage);
        pageInfo1.setPageSize(pageSize);
        pageInfo1.setTotalCount(pageInfo.getTotal());
        pageInfo1.setTotalPage(pageInfo.getPages());
        PageResult<ProjectManagementDTO> pageResult = new PageResult<>(projectManagementDTOS, pageInfo1);
        return pageResult;
    }

    public void setLine(ProjectManagementDTO projectManagementDTO){
        ProjectManagementDO projectManagementDO = BeanConverter.convert(projectManagementDTO, ProjectManagementDO.class);
        projectManagementMapper.updateline(projectManagementDO);
    }

    public void updateProject(ProjectManagementDTO projectManagementDTO){
        ProjectManagementDO projectManagementDO = BeanConverter.convert(projectManagementDTO, ProjectManagementDO.class);
        projectManagementMapper.updateProject(projectManagementDO);
    }
}
