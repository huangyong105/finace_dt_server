package com.roncoo.eshop.mapper;



import com.roncoo.eshop.model.InvestmentDetailsDO;
import org.springframework.stereotype.Repository;

@Repository
public interface InvestmentDetailsMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(InvestmentDetailsDO record);

    int insertSelective(InvestmentDetailsDO record);

    InvestmentDetailsDO selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(InvestmentDetailsDO record);

    int updateByPrimaryKey(InvestmentDetailsDO record);
}