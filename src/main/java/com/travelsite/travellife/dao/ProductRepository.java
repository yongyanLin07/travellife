package com.travelsite.travellife.dao;

import com.travelsite.travellife.po.PRODUCT;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;




public interface ProductRepository extends JpaRepository<PRODUCT,Long> {
    PRODUCT findByName(String name);
}
