package com.travelsite.travellife.po;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "cart")
public class CART {
    @Id
    @GeneratedValue
    private Long id;
    private int num;
    private int price;
    //是否付款
    public int cartValid;
    @ManyToOne
    private USER user;

    @ManyToOne
    private PRODUCT product;

    public CART() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getCartValid() {
        return cartValid;
    }

    public void setCartValid(int cartValid) {
        this.cartValid = cartValid;
    }

    public USER getUser() {
        return user;
    }

    public void setUser(USER user) {
        this.user = user;
    }

    public PRODUCT getProduct() {
        return product;
    }

    public void setProduct(PRODUCT product) {
        this.product = product;
    }
}
