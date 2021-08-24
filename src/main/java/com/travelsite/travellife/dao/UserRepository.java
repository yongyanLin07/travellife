package com.travelsite.travellife.dao;


import com.travelsite.travellife.po.USER;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.relational.core.sql.In;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.List;


public interface UserRepository extends JpaRepository<USER,Long> {
    USER findByNameAndPassword(String name,String password);
    USER findByName(String name);
    @Modifying
    @Query(value = "DELETE FROM sys_user_role WHERE user_id = ?1", nativeQuery = true)
    Integer cancelRoleJoin(Long id);
}
