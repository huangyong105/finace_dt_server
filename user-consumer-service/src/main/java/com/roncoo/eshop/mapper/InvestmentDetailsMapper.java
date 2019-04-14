package com.roncoo.eshop.mapper;



import com.roncoo.eshop.model.InvestmentDetailsDO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface InvestmentDetailsMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(InvestmentDetailsDO record);

    int insertSelective(InvestmentDetailsDO record);

    @Select("select * from investment_details where investmenter_id=#{userId}")
    List<InvestmentDetailsDO> selectByPrimaryKey(Long userId);

    int updateByPrimaryKeySelective(InvestmentDetailsDO record);

    int updateByPrimaryKey(InvestmentDetailsDO record);
}