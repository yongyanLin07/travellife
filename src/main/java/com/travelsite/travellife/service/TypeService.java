package com.travelsite.travellife.service;


import com.travelsite.travellife.po.TYPE;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.lang.reflect.Type;
import java.util.List;


@Service
public interface TypeService {
    TYPE saveType(TYPE type);
    TYPE getTypeByName(String name);
    TYPE getType(int id);
    TYPE get(TYPE type);
    List<TYPE> listType();
    Page<TYPE> listType(Pageable pageable);
    TYPE updateType(int id,TYPE type);
    void deleteType(int id);
}
