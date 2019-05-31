package com.imooc.sell.utils;

import com.imooc.sell.enums.CodeEnum;

/**
 * @Author: xting
 * @CreateDate: 2019/5/14 15:22
 */
public class EnumUtils {

    public static <T extends CodeEnum> T getByCode(Integer code, Class<T> enumclass){
        for(T each:enumclass.getEnumConstants()){
            if(code.equals(each.getCode())){
                return each;
            }
        }
        return null;
    }
}
