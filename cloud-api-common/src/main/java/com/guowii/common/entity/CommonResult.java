package com.guowii.common.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommonResult<T> implements Serializable {

    private static final long serialVersionUID = -9165107998205212161L;

    public static final String SUCC_CODE = "00000";
    public static final String FAIL_CODE = "99999";


    // 响应码
    private String code;

    // 响应消息
    private String message;

    // 响应数据
    private T data;

    public CommonResult(String code, String message) {
        this.code = code;
        this.message = message;
    }
}
