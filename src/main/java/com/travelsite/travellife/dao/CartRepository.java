package com.travelsite.travellife.dao;

import com.travelsite.travellife.po.CART;
import com.travelsite.travellife.po.PRODUCT;
import com.travelsite.travellife.po.USER;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CartRepository extends JpaRepository<CART,Long> {
    CART getByUserAndProduct(USER user, PRODUCT product);
    List<CART> findByUser(USER user);


}
