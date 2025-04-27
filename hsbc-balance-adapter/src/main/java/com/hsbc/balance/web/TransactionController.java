package com.hsbc.balance.web;

import com.alibaba.cola.dto.Response;
import com.hsbc.balance.api.TransactionService;
import com.hsbc.balance.dto.TransactionCmd;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * TransactionController
 *
 * @author jixueWang
 * @version 2025/4/26
 * 
 **/
@RestController
@RequestMapping("/transactions")
public class TransactionController {

    /**
     * 用于处理交易相关操作的服务对象。
     */

    @Autowired
    private TransactionService transactionService;

    /**
     * 执行交易操作
     * @param command 交易命令对象，包含交易相关信息
     * @return 如果交易成功，返回 HTTP 状态码 200 和消息 "交易已提交"；否则返回 HTTP 状态码 500 和错误消息
     */
    @PostMapping("/transfer")
    public ResponseEntity<String> transfer(@RequestBody TransactionCmd command) {
        Response response = transactionService.transfer(command);
        if(response.isSuccess()){
            return ResponseEntity.ok("交易已提交");
        }else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("交易失败：" + response.getErrMessage());
        }
    }
}

