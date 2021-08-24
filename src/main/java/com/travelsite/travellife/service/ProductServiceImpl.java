package com.travelsite.travellife.service;

import com.travelsite.travellife.NotFoundException;
import com.travelsite.travellife.dao.ProductRepository;
import com.travelsite.travellife.po.PRODUCT;
import com.travelsite.travellife.po.TYPE;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;

    //    @Transactional
    @Override
    public PRODUCT getProduct(Long id) {
        return productRepository.findById(id).get();
    }

    //    @Transactional
    @Override
    public Page<PRODUCT> listProduct(Pageable pageable) {
        return productRepository.findAll(pageable);
    }
    //    @Transactional
    @Override
    public PRODUCT saveProduct(PRODUCT product) {
        return productRepository.save(product);
    }
    //    @Transactional
    @Override
    public PRODUCT updateProduct(Long id, PRODUCT product) {
        PRODUCT p = productRepository.findById(id).get();
        if(p == null){
            throw new NotFoundException("该产品不存在");
        }
        BeanUtils.copyProperties(product,p);
        return productRepository.save(p);
    }

    @Override
    public PRODUCT getProductByName(String name) {
        return productRepository.findByName(name);
    }

    //    @Transactional
    @Override
    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }

    //    @Transactional
    @Override
    public List<PRODUCT> list() {
        return productRepository.findAll();
    }



}
