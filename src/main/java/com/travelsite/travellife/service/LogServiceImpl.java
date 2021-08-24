package com.travelsite.travellife.service;


import com.travelsite.travellife.dao.LogRepository;
import com.travelsite.travellife.po.LogData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;



@Service
public class LogServiceImpl implements LogService{
    @Autowired
    private LogRepository logRepository;


    @Override
    public LogData save(LogData log) {
        LogData log1 = logRepository.save(log);
        return log1;
    }

    @Override
    public Page<LogData> listLogData(Pageable pageable) {
        return logRepository.findAll(pageable);
    }


    @Override
    public void delete(int id) {
        logRepository.deleteById(id);
    }
}
