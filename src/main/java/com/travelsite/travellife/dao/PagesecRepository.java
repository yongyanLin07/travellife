package com.travelsite.travellife.dao;

import com.travelsite.travellife.po.PAGESEC;
import org.springframework.data.jpa.repository.JpaRepository;


public interface PagesecRepository extends JpaRepository<PAGESEC,Integer> {
    PAGESEC findByUrl(String url);

}
