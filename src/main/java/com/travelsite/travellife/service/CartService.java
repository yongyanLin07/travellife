package com.travelsite.travellife.service;

import com.travelsite.travellife.po.CART;
import com.travelsite.travellife.po.PRODUCT;
import com.travelsite.travellife.po.USER;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CartService {
    CART saveCart(CART cart);
    CART getCart(Long id);
    List<CART> listCart();
    Page<CART> listCart(Pageable pageable);
    CART updateCart(Long id,CART cart);
    void deleteCart(Long id);
    CART getByUserProduct(USER user, PRODUCT product);
    List<CART> getByUser(USER user);
    void deleteByUser(USER user);

}
