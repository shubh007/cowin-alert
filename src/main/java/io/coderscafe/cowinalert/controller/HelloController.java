package io.coderscafe.cowinalert.controller;

import io.coderscafe.cowinalert.config.Config;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("healthcheck")
public class HelloController {

    @Autowired
    Config config;

    @GetMapping("/hello")
    public String sayHello(@RequestParam(name = "name") String name){
        return "Hey, "+name+" I am alive, and checking for vaccine available slot for pin-code : 231304 for age group : 18-44" +
                " \n \n will give alerts to : "+config.getToEmails() + " once slots are available";
    }
}
