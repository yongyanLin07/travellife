package com.travelsite.travellife.dao;


import com.travelsite.travellife.po.BLIST;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BlistRepository extends JpaRepository<BLIST,String> {
    BLIST findByIpaddressAndType(String ipaddress,int type);
    BLIST findByIpaddress(String ipaddress);
    void deleteByIpaddress(String ipaddress);
}
