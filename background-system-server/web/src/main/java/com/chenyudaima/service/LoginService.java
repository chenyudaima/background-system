package com.chenyudaima.service;

import com.chenyudaima.model.Result;

public interface LoginService {
    Result<?> login(String account,String password);

}
