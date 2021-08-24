package com.travelsite.travellife.po;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "product")
public class PRODUCT {
    @Id
    @GeneratedValue
    private Long id;
    private String name;
    private String description;
    private int price;
    private int stock;
    private String img;
    private int recommend;
    private int sale;
    @ManyToOne
    private TYPE type;
    @OneToMany(mappedBy = "product")
    private List<CART> carts = new ArrayList<CART>();


    public PRODUCT() {
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public int getRecommend() {
        return recommend;
    }

    public void setRecommend(int recommend) {
        this.recommend = recommend;
    }

    public int getSale() {
        return sale;
    }

    public void setSale(int sale) {
        this.sale = sale;
    }

    public TYPE getType() {
        return type;
    }

    public void setType(TYPE type) {
        this.type = type;
    }

    public List<CART> getCarts() {
        return carts;
    }

    public void setCarts(List<CART> carts) {
        this.carts = carts;
    }

    @Override
    public String toString() {
        return "PRODUCT{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", price=" + price +
                ", stock=" + stock +
                ", img='" + img + '\'' +
                ", recommend=" + recommend +
                ", sale=" + sale +
                ", type=" + type +
                ", carts=" + carts +
                '}';
    }
}
