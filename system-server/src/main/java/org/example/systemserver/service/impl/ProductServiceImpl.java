package org.example.systemserver.service.impl;

import jakarta.annotation.Resource;
import org.example.systemserver.domain.TProduct;
import org.example.systemserver.mapper.TProductMapper;
import org.example.systemserver.service.ProductService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {
    @Resource
    private TProductMapper tProductMapper;

    @Override
    public List<TProduct> getAllOnSaleProduct() {
        return tProductMapper.selectAllOnSaleProduct();
    }
}
