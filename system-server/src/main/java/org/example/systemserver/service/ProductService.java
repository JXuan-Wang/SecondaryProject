package org.example.systemserver.service;

import org.example.systemserver.domain.TProduct;

import java.util.List;

public interface ProductService {
    List<TProduct> getAllOnSaleProduct();
}
