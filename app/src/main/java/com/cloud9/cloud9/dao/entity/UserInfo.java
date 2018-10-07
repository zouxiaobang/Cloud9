package com.cloud9.cloud9.dao.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * author: xb.Zou
 * date: 2018/9/8 0008
 **/
@Entity
public class UserInfo {
    @Id(autoincrement = true)
    private Long id;
    private String name;
    private int ago;
    private int sex;
    private String phone;
    private String email;
    @Generated(hash = 2121989419)
    public UserInfo(Long id, String name, int ago, int sex, String phone,
            String email) {
        this.id = id;
        this.name = name;
        this.ago = ago;
        this.sex = sex;
        this.phone = phone;
        this.email = email;
    }
    @Generated(hash = 1279772520)
    public UserInfo() {
    }
    public Long getId() {
        return this.id;
    }
    public String getName() {
        return this.name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public int getAgo() {
        return this.ago;
    }
    public void setAgo(int ago) {
        this.ago = ago;
    }
    public int getSex() {
        return this.sex;
    }
    public void setSex(int sex) {
        this.sex = sex;
    }
    public String getPhone() {
        return this.phone;
    }
    public void setPhone(String phone) {
        this.phone = phone;
    }
    public String getEmail() {
        return this.email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public void setId(Long id) {
        this.id = id;
    }
}
