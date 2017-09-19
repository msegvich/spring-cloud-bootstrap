package com.slalom.cloud;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Random;

@RestController
public class NumberController {
    @Value("${bounds}")
    int bounds;
    Random random_generator = new Random();

    @RequestMapping("/")
    public @ResponseBody
    String getNumber() {
        int lucky_number = random_generator.nextInt(bounds);
        return Integer.toString(lucky_number);
    }
}
