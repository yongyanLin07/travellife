package com.travelsite.travellife.po;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.travelsite.travellife.utils.db.StatusEnum;
import com.travelsite.travellife.utils.db.StatusUtil;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Data
@Entity
@Table(name = "sys_role")
@ToString(exclude = {"users", "menus", "createBy", "updateBy"})
//生成equals和hashCode()
@EqualsAndHashCode(exclude = {"users", "menus", "createBy", "updateBy"})
@EntityListeners(AuditingEntityListener.class)
//定义删除delete语句
@SQLDelete(sql = "update sys_role" + StatusUtil.SLICE_DELETE)
@Where(clause = StatusUtil.NOT_DELETE)
public class Role implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String title;
    private String remark;
    @CreatedDate
    private Date createDate;
    @LastModifiedDate
    private Date updateDate;

    @CreatedBy
    @ManyToOne(fetch = FetchType.LAZY)
    //找不到引用外键属性自动忽略
    @NotFound(action = NotFoundAction.IGNORE)
    @JoinColumn(name = "create_by")
    //忽略返回
    //@JsonIgnore
    private USER createBy;
    @LastModifiedBy
    @ManyToOne(fetch = FetchType.LAZY)
    @NotFound(action = NotFoundAction.IGNORE)
    @JoinColumn(name = "update_by")
   // @JsonIgnore
    private USER updateBy;

    private Byte status = StatusEnum.OK.getCode();

    //级联操作，更新保存级联更新
    @ManyToMany(mappedBy = "roles", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JsonIgnore
    private Set<USER> users = new HashSet<>(0);

    //访问时再加载
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "sys_role_menu",
            joinColumns = @JoinColumn(name = "role_id"),
            inverseJoinColumns = @JoinColumn(name = "menu_id"))
    @JsonIgnore
    private Set<Menu> menus = new HashSet<>(0);


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    public USER getCreateBy() {
        return createBy;
    }

    public void setCreateBy(USER createBy) {
        this.createBy = createBy;
    }

    public USER getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(USER updateBy) {
        this.updateBy = updateBy;
    }

    public Byte getStatus() {
        return status;
    }

    public void setStatus(Byte status) {
        this.status = status;
    }

    public Set<USER> getUsers() {
        return users;
    }

    public void setUsers(Set<USER> users) {
        this.users = users;
    }

    public Set<Menu> getMenus() {
        return menus;
    }

    public void setMenus(Set<Menu> menus) {
        this.menus = menus;
    }
}
