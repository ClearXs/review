package com.jw.springbootprovider2.service;

import com.zzht.patrol.screw.common.exception.ConnectionException;

import java.util.Map;

public interface UserService {

    Map<String, Object> getUserInfo() throws ConnectionException, InterruptedException;
}
