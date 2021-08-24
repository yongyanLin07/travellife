package com.travelsite.travellife.service;

import com.travelsite.travellife.NotFoundException;
import com.travelsite.travellife.aspect.EncryptMethod;
import com.travelsite.travellife.aspect.DecryptMethod;
import com.travelsite.travellife.dao.UserRepository;


import com.travelsite.travellife.po.CART;
import com.travelsite.travellife.po.Role;
import com.travelsite.travellife.po.USER;
import com.travelsite.travellife.shiro.ShiroUtil;
import com.travelsite.travellife.utils.MD5Utils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;


@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CartService cartService;

    //解密邮箱信息
    //    @Transactional
    @DecryptMethod
    @Override
    public USER getUser(USER user) {
        //解密
        USER user2 = userRepository.findById(user.getId()).get();
        return user2;
    }

    @Override
    public USER getUserById(Long id) {
        USER user = userRepository.findById(id).get();
        return user;
    }

    //    @Transactional
    @Override
    public USER getUserByName(String name) {
        USER user = userRepository.findByName(name);
        //解密
        return user;
    }
    @DecryptMethod
    @Override
    public Page<USER> listUser(Pageable pageable) {
        Page<USER> all = userRepository.findAll(pageable);
        return all;
    }
    //    @Transactional
    @Override
    @EncryptMethod
    public USER saveUser(USER user) {
        //如果id为空 说明是注册 需要对密码进行加密处理
        if (user.getId() == null) {
            //生成盐值
            String salt = MD5Utils.getRandomString(6);
            user.setSalt(salt);
//            String saltPassword = salt + user.getPassword();
            //设置密码为加密过后的密码
            String encrypt = ShiroUtil.encrypt(user.getPassword(), user.getSalt());
            user.setPassword(encrypt);
            userRepository.save(user);
        }
        userRepository.save(user);
        return user;
    }

    @Transactional
    @Override
    public void deleteUser(Long id) {
        USER user = userRepository.findById(id).get();
        List<CART> list = cartService.getByUser(user);
        if(list.size()>0)
        {
            cartService.deleteByUser(user);
        }
        userRepository.cancelRoleJoin(id);
        userRepository.deleteById(id);
    }


    //    //    @Transactional(value= Transactional.TxType.REQUIRES_NEW)
    @Override
    @EncryptMethod
    public USER updateUser(USER user,Long id) {
        USER u = userRepository.findById(id).get();
        if(u == null){
            throw new NotFoundException("该用户不存在");
        }
        BeanUtils.copyProperties(user, u);
        USER save = userRepository.save(u);

        return save;
    }
}
