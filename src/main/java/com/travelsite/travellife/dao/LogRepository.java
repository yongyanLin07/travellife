package com.travelsite.travellife.dao;

import com.travelsite.travellife.po.LogData;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LogRepository extends JpaRepository<LogData,Integer> {

}
