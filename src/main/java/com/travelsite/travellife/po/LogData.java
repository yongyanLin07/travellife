package com.travelsite.travellife.po;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@Table(name = "sys_log")
public class LogData {
    @Id
    @GeneratedValue
    private int id;
    @OneToOne
    @JoinColumn
    private USER user;
    private String user_action;
    @Temporal(TemporalType.TIMESTAMP)
    private Date time;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public USER getUser() {
        return user;
    }

    public void setUser(USER user) {
        this.user = user;
    }

    public String getUser_action() {
        return user_action;
    }

    public void setUser_action(String user_action) {
        this.user_action = user_action;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    @Override
    public String toString() {
        return "LogData{" +
                "id=" + id +
                ", user=" + user +
                ", user_action='" + user_action + '\'' +
                ", time=" + time +
                '}';
    }
}
