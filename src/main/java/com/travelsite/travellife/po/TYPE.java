package com.travelsite.travellife.po;

import com.travelsite.travellife.aspect.CryptField;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "type")
public class TYPE {
    @Id
    @GeneratedValue
    private int id;

    //@CryptField
    private String name;

    @OneToMany(mappedBy = "type")
    private List<PRODUCT> list = new ArrayList<PRODUCT>();

    public TYPE() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<PRODUCT> getList() {
        return list;
    }

    public void setList(List<PRODUCT> list) {
        this.list = list;
    }

    @Override
    public String toString() {
        return "TYPE{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", list=" + list +
                '}';
    }
}
