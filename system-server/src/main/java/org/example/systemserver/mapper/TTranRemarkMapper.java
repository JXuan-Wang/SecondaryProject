package org.example.systemserver.mapper;

import org.example.systemserver.domain.TTranRemark;

/**
* @author 炫
* @description 针对表【t_tran_remark(交易跟踪记录表)】的数据库操作Mapper
* @createDate 2024-07-05 03:54:15
* @Entity org.example.systemserver.domain.TTranRemark
*/
public interface TTranRemarkMapper {

    int deleteByPrimaryKey(Long id);

    int insert(TTranRemark record);

    int insertSelective(TTranRemark record);

    TTranRemark selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(TTranRemark record);

    int updateByPrimaryKey(TTranRemark record);

}
