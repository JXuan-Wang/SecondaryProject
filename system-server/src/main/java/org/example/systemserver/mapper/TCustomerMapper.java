package org.example.systemserver.mapper;

import org.example.systemserver.domain.TCustomer;

import java.util.List;

/**
* @author 炫
* @description 针对表【t_customer(客户表)】的数据库操作Mapper
* @createDate 2024-07-05 03:54:15
* @Entity org.example.systemserver.domain.TCustomer
*/
public interface TCustomerMapper {

    int deleteByPrimaryKey(Long id);

    int insert(TCustomer record);

    int insertSelective(TCustomer record);

    TCustomer selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(TCustomer record);

    int updateByPrimaryKey(TCustomer record);

    List<TCustomer> selectCustomerPage();

    List<TCustomer> selectCustomerByExcel(List<String> idList);

    Integer selectByCount();
}
