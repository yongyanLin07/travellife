package com.travelsite.travellife.service;

import com.travelsite.travellife.NotFoundException;
import com.travelsite.travellife.dao.PagesecRepository;
import com.travelsite.travellife.po.PAGESEC;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PagesecServiceImpl implements PagesecService {
    @Autowired
    private PagesecRepository pageRepository;
    //    @Transactional
    @Override
    public PAGESEC getPageByUrl(String url) {
        return pageRepository.findByUrl(url);
    }
    //    @Transactional
    @Override
    public PAGESEC savePage(PAGESEC page) {
       return pageRepository.save(page);
    }

    //    @Transactional
    @Override
    public PAGESEC getPage(int id) {
        return pageRepository.findById(id).get();
    }

    //    @Transactional
    @Override
    public List<PAGESEC> listPage() {
        return pageRepository.findAll();
    }

    //    @Transactional
    @Override
    public Page<PAGESEC> listPage(Pageable pageable) {
        return pageRepository.findAll(pageable);
    }


    //    @Transactional
    @Override
    public PAGESEC updatePage(int id, PAGESEC page) {
        PAGESEC t = pageRepository.findById(id).get();
        if(t == null){
            throw new NotFoundException("不存在该页面信息");
        }
        //page值复制到t中
        BeanUtils.copyProperties(page,t);
        return pageRepository.save(t);
    }

    //    @Transactional
    @Override
    public void deletePage(int id) {
        pageRepository.deleteById(id);

    }
}
