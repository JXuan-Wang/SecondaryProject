package org.example.systemserver.mapper;

import org.example.systemserver.domain.TCustomerRemark;

/**
* @author 炫
* @description 针对表【t_customer_remark(客户跟踪记录表)】的数据库操作Mapper
* @createDate 2024-07-05 03:54:15
* @Entity org.example.systemserver.domain.TCustomerRemark
*/
public interface TCustomerRemarkMapper {

    int deleteByPrimaryKey(Long id);

    int insert(TCustomerRemark record);

    int insertSelective(TCustomerRemark record);

    TCustomerRemark selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(TCustomerRemark record);

    int updateByPrimaryKey(TCustomerRemark record);

}
