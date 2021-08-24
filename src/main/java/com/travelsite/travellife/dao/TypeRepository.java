package com.travelsite.travellife.dao;

import com.travelsite.travellife.po.TYPE;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;



public interface TypeRepository extends JpaRepository<TYPE,Integer> {
    TYPE findByName(String name);

}
