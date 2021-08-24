package com.travelsite.travellife.service;

import com.travelsite.travellife.NotFoundException;
import com.travelsite.travellife.dao.BlistRepository;
import com.travelsite.travellife.po.BLIST;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class BlistServiceImpl implements BlistService {
    @Autowired
    private BlistRepository blistRepository;

    //    @Transactional
    @Override
    public BLIST saveBlist(BLIST blist) {
        return blistRepository.save(blist);
    }

    //    @Transactional
    @Override
    public BLIST getBlist(String ipaddress) {
        return blistRepository.findByIpaddress(ipaddress);
    }

    @Override
    public BLIST getBlistByIpAndType(String ipaddress, int type) {
        return blistRepository.findByIpaddressAndType(ipaddress,type);
    }

    @Transactional
    @Override
    public List<BLIST> getlist() {
        return blistRepository.findAll();
    }

    //    @Transactional
    @Override
    public Page<BLIST> listBlist(Pageable pageable) {
        return blistRepository.findAll(pageable);
    }

    //    @Transactional
    @Override
    public BLIST updateBlist(String ipaddress, BLIST blist) {
        BLIST b = blistRepository.findByIpaddress(ipaddress);
        if(b == null){
            throw new NotFoundException("该产品不存在");
        }
       // b.setUserid(blist.getUserid());
        BeanUtils.copyProperties(blist,b);
        return blistRepository.save(b);
    }

    @Transactional
    @Override
    public void deleteBlist(String ipaddress) {
        blistRepository.deleteByIpaddress(ipaddress);
    }
}
