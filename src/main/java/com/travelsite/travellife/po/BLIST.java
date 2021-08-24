package com.travelsite.travellife.po;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Date;

@Entity
@Table(name = "blist")
public class BLIST {
    @Id
    @GeneratedValue
    private Long id;
    private String ipaddress;
    @Temporal(TemporalType.TIMESTAMP)
    private Date time;
    private int type;//类型，1 SQL注入类型，2 XSS攻击 3 CSRF攻击

    public Long getId() {
        return id;
    }

    public BLIST setId(Long id) {
        this.id = id;
        return this;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getIpaddress() {
        return ipaddress;
    }

    public void setIpaddress(String ipaddress) {
        this.ipaddress = ipaddress;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

}
