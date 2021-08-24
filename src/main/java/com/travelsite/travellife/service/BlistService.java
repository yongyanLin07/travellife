package com.travelsite.travellife.service;

import com.travelsite.travellife.po.BLIST;
import com.travelsite.travellife.po.CART;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface BlistService {
    BLIST saveBlist(BLIST blist);
    BLIST getBlist(String ipaddress);
    BLIST getBlistByIpAndType(String ipaddress,int type);
    List<BLIST> getlist();
    Page<BLIST> listBlist(Pageable pageable);
    BLIST updateBlist(String ipaddress,BLIST blist);
    void deleteBlist(String ipaddress);
}
