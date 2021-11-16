package com.jw.unittest.entity;

import lombok.Data;

import java.io.Serializable;

@Data
public class User implements Serializable {

    private String id;

    private String account;

    private String password;

    private String email;

    private String mobile;

}
