package com.roncoo.eshop.mapper;


import com.roncoo.eshop.model.AdministratorDO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

@Mapper
public interface AdministratorMapper {

    int deleteByPrimaryKey(Integer id);

    int insert(AdministratorDO record);

    int insertSelective(AdministratorDO record);

    AdministratorDO selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(AdministratorDO record);

    int updateByPrimaryKey(AdministratorDO record);
}