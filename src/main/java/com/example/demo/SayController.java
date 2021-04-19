package com.example.demo;

import javax.annotation.PostConstruct;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author IT讲坛
 * Created on 2021-04-19
 */
@RestController
@RequestMapping("/say")
public class SayController {

    @GetMapping("/hi")
    public String sayHi(@RequestParam("name") String name){
        return "hello" + name;
    }

}
