package com.travelsite.travellife.service;

import com.travelsite.travellife.NotFoundException;
import com.travelsite.travellife.dao.CartRepository;
import com.travelsite.travellife.po.CART;
import com.travelsite.travellife.po.PRODUCT;
import com.travelsite.travellife.po.USER;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
@Service
public class CartServiceImpl implements CartService {
    @Autowired
    private CartRepository cartRepository;
    //    @Transactional
    @Override
    public CART saveCart(CART cart) {
        return cartRepository.save(cart);
    }

    //    @Transactional
    @Override
    public CART getCart(Long id) {
        return cartRepository.findById(id).get();
    }

    //    @Transactional
    @Override
    public List<CART> listCart() {
        return cartRepository.findAll();
    }

    //    @Transactional
    @Override
    public Page<CART> listCart(Pageable pageable) {
        return cartRepository.findAll(pageable);
    }

    //    @Transactional
    @Override
    public CART updateCart(Long id, CART cart) {
        CART cart1 = cartRepository.findById(id).get();
        if(cart1 == null){
            throw new NotFoundException("不存在此类型");
        }
        BeanUtils.copyProperties(cart,cart1);
        return cartRepository.save(cart1);
    }

    //    @Transactional
    @Override
    public void deleteCart(Long id) {
        cartRepository.deleteById(id);
    }

    //    @Transactional
    @Override
    public CART getByUserProduct(USER user, PRODUCT product) {
        return cartRepository.getByUserAndProduct(user,product);
    }

    @Override
    public List<CART> getByUser(USER user) {
        List<CART> list = cartRepository.findByUser(user);
        return list;
    }

    @Override
    public void deleteByUser(USER user) {
        List<CART> list = cartRepository.findByUser(user);
        for (int i = 0;i<list.size();i++){
            cartRepository.deleteById(list.get(i).getId());
        }
    }

}
