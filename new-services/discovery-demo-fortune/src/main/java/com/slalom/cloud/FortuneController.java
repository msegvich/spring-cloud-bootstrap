package com.slalom.cloud;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class FortuneController {
    @Value("#{'${phrases}'.split(',')}")
    List<String> phrases;

    @RequestMapping("/")
    public @ResponseBody
    String getFortune() {
        int i = (int)Math.round(Math.random() * (phrases.size() - 1));
        return phrases.get(i);
    }
}
