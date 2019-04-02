package com.roncoo.eshop.mapper;


import com.roncoo.eshop.model.InvestorManagementDO;

public interface InvestorManagementMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(InvestorManagementDO record);

    int insertSelective(InvestorManagementDO record);

    InvestorManagementDO selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(InvestorManagementDO record);

    int updateByPrimaryKey(InvestorManagementDO record);
}