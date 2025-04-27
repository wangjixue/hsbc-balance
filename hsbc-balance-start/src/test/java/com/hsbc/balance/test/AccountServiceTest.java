package com.hsbc.balance.test;

import com.hsbc.balance.domain.accountcontext.entity.AccountBO;
import com.hsbc.balance.domain.accountcontext.gateway.AccountGateway;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.UUID;

/**
 * AccountServiceTest class.
 *
 * @author jixueWang
 * @version 2025/4/27
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class AccountServiceTest {
    @Autowired
    AccountGateway accountGateway;

    static Random random = new Random();


    /**
     * 创建账号
     */
    @Test
    public void createAccountTest() {
        try {
            // 创建账户的逻辑
            for (int i = 0; i < 50; i++) {
                AccountBO  accountBO = new AccountBO();
                accountBO.setAccountId(UUID.randomUUID().toString());
                accountBO.setAccountName("我是一个测试账号-" + i);
                accountBO.setBalance(new BigDecimal("10000.00").add(new BigDecimal(random.nextDouble(5000D))));
                accountBO.setModified(new Date());
                accountBO.setCreated(new Date());
                accountGateway.save(accountBO);
            }
        } catch (Exception e) {

        }
    }
}
