package com.chenyudaima.service;

import com.chenyudaima.model.Result;

import java.util.Map;

/**
 * @author 沉鱼代码
 * @date 2023/1/3
 */
public interface HomeService {

    Result<?> userInfo();

    Result<?> logout(String token);

    Result<?> menu();
}
