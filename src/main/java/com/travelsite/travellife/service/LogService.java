package com.travelsite.travellife.service;

import com.travelsite.travellife.po.LogData;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public interface LogService {
    LogData save(LogData log);
    Page<LogData> listLogData(Pageable pageable);
    void delete(int id);
}
