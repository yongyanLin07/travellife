package com.travelsite.travellife.dao;

import com.travelsite.travellife.utils.db.StatusConst;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

//接口不会被单独创建实例
@NoRepositoryBean
public interface BaseRepository<T, ID> extends JpaRepository<T,ID> {
    /**
     * 批量更新数据状态
     * #{#entityName} 实体类对象
     * @return 更新数量
     */
    @Modifying
    //    @Transactional
    @Query("update #{#entityName} set status = ?1  where id in ?2 and status <> " + StatusConst.DELETE)
    Integer updateStatus(Byte status, List<ID> id);
}
