package com.travelsite.travellife.service;


import com.travelsite.travellife.po.PAGESEC;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public interface PagesecService {
    PAGESEC savePage(PAGESEC page);
    PAGESEC getPageByUrl(String url);
    PAGESEC getPage(int id);
    List<PAGESEC> listPage();
    Page<PAGESEC> listPage(Pageable pageable);
    PAGESEC updatePage(int id, PAGESEC page);
    void deletePage(int id);
}
