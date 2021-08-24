package com.travelsite.travellife.service;

import com.travelsite.travellife.po.USER;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;



public interface UserService {
    USER getUser(USER user);
    USER getUserById(Long id);
    USER getUserByName(String name);
    Page<USER> listUser(Pageable pageable);
    USER saveUser(USER user);
    USER updateUser(USER user,Long id);
    void deleteUser(Long id);
}
