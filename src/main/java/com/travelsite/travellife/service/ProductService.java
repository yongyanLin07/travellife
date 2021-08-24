package com.travelsite.travellife.service;

import com.travelsite.travellife.po.PRODUCT;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.relational.core.sql.In;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ProductService {
    PRODUCT getProduct(Long id);
    PRODUCT getProductByName(String name);
    Page<PRODUCT> listProduct(Pageable pageable);
    PRODUCT saveProduct(PRODUCT product);
    PRODUCT updateProduct(Long id,PRODUCT product);
    void deleteProduct(Long id);
    List<PRODUCT> list();

}
