package org.example.systemserver.mapper;

import org.example.systemserver.domain.TProduct;

import java.util.List;

/**
* @author 炫
* @description 针对表【t_product(产品表)】的数据库操作Mapper
* @createDate 2024-07-05 03:54:15
* @Entity org.example.systemserver.domain.TProduct
*/
public interface TProductMapper {

    int deleteByPrimaryKey(Long id);

    int insert(TProduct record);

    int insertSelective(TProduct record);

    TProduct selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(TProduct record);

    int updateByPrimaryKey(TProduct record);

    List<TProduct> selectAllOnSaleProduct();
}
