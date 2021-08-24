package com.travelsite.travellife.service;

import com.travelsite.travellife.NotFoundException;
import com.travelsite.travellife.aspect.DecryptMethod;
import com.travelsite.travellife.aspect.EncryptMethod;
import com.travelsite.travellife.dao.TypeRepository;
import com.travelsite.travellife.po.TYPE;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.lang.reflect.Type;
import java.util.List;

@Service
public class TypeServiceImpl implements TypeService {



    @Autowired
    private TypeRepository typeRepository;
    //    @Transactional
    @Override
    public TYPE getTypeByName(String name) {
        return typeRepository.findByName(name);
    }
    //    @Transactional

    //@EncryptMethod
    @Override
    public TYPE saveType(TYPE type) {
       return typeRepository.save(type);
    }

    //    @Transactional
    @Override
    public TYPE getType(int id) {
        return typeRepository.findById(id).get();
    }

    @EncryptMethod
    @Override
    public TYPE get(TYPE type) {
        return type;
    }


    //    @Transactional
    @Override
    public List<TYPE> listType() {
        return typeRepository.findAll();
    }

    //    @Transactional
    @Override
    public Page<TYPE> listType(Pageable pageable) {
        return typeRepository.findAll(pageable);
    }


    //    @Transactional
    @Override
    public TYPE updateType(int id, TYPE type) {
        TYPE t = typeRepository.findById(id).get();
        if(t == null){
            throw new NotFoundException("不存在该类型");
        }
        //type值复制到t中
        BeanUtils.copyProperties(type,t);
        return typeRepository.save(t);
    }

    //    @Transactional
    @Override
    public void deleteType(int id) {
        typeRepository.deleteById(id);

    }
}
