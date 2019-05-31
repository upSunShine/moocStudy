package com.imooc.sell.handler;

import com.imooc.sell.VO.ResultVO;
import com.imooc.sell.exceptions.ResponseBankException;
import com.imooc.sell.exceptions.SellException;
import com.imooc.sell.utils.ResultVOUtil;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 *  统一异常处理
 * @Author: xting
 * @CreateDate: 2019/5/16 15:45
 */
@ControllerAdvice
public class SellExceptioonHandler {




    @ExceptionHandler(value= SellException.class)
    @ResponseBody
    public ResultVO handlerSellerException(SellException e){
        return ResultVOUtil.error(String.valueOf(e.getCode()),e.getMessage());
    }

    @ExceptionHandler(value = ResponseBankException.class)
    @ResponseStatus(value = HttpStatus.FORBIDDEN)
    public void handlerResponseBankException(){

    }
}
