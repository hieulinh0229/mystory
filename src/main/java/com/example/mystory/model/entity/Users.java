package com.example.mystory.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedDate;

import java.util.Date;


@Entity
@Table(name = "USERS")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Users {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Long id;
    @Column(name = "USER_NAME")
    private String userName;
    @Column(name = "PASSWORD")
    private String password;
    @Column(name = "ROLE")
    private String role;
    @Column(name = "BIRTHDAY")
    private String birthday;
    @Column(name = "GENDER")
    private Boolean gender; // true : MALE -false: FEMALE
    @Column(name = "IMAGE")
    private String image;
    @Column(name = "PHONE")
    private String phone;
    @Column(name = "ADDRESS")
    private String address;
    @Column(name = "ACTIVE")
    private Boolean active;
    @Column(name = "DEL_FLAG",columnDefinition = "boolean default false")
    private Boolean deleteFlag;
    @Column(name = "YMD_CREATE")
    @CreationTimestamp
    private Date createTime;
    @Column(name = "YMD_UPDATE")
    @UpdateTimestamp
    private Date updateTime;
}
