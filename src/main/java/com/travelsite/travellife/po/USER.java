package com.travelsite.travellife.po;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.travelsite.travellife.aspect.CryptField;

import javax.persistence.*;
import java.util.*;

@Entity
@Table(name = "user")
public class USER {

    @Id
    @GeneratedValue
    private Long id;
    private String name;

    private String sex;

    private String password;

    //盐值，用于加密
    private String salt;

    @CryptField
    private String email;

    private Integer status;

    @Column(name = "retry_time", length = 0)
    private Date retryTime;

    //维护权交由CART类
    @OneToMany(mappedBy = "user")
    @JsonIgnore
    private List<CART> carts = new ArrayList<CART>();


    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "sys_user_role",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    @JsonIgnore
    private Set<Role> roles = new HashSet<>(0);


    public USER() {
    }

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

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public List<CART> getCarts() {
        return carts;
    }

    public void setCarts(List<CART> carts) {
        this.carts = carts;
    }

    public String getSalt() {
        return salt;
    }

    public USER setSalt(String salt) {
        this.salt = salt;
        return this;
    }

    public Date getRetryTime() {
        return retryTime;
    }

    public void setRetryTime(Date retryTime) {
        this.retryTime = retryTime;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    @Override
    public String toString() {
        return "USER{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", sex='" + sex + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                ", status=" + status +
                '}';
    }
}
