package com.hsbc.balance.web;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * IndexController class.
 *
 * @author jixueWang
 * @version 2025/4/26
 */
@RestController
public class IndexController {
    @GetMapping(value = {"/", "/index"})
    public String helloWorld(){
        return "Hello, welcome to COLA world!";
    }

}
