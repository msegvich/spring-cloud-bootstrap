package com.efleets.cloud;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.List;

/**
 * Created by matt.segvich on 3/20/17.
 */
@RestController
public class FortuneCookieController {


    @RequestMapping("/fortune_cookie")
    public @ResponseBody
    String getSentence() {
        return
                getFortune("FORTUNE") + " Your lucky number is: "
                        + getNumber("LUCKY-NUMBER")
                ;
    }

    @Autowired
    DiscoveryClient fortune_client;
    public String getFortune(String service) {
        List<ServiceInstance> list = fortune_client.getInstances(service);
        if (list != null && list.size() > 0 ) {
            URI uri = list.get(0).getUri();
            if (uri !=null ) {
                return (new RestTemplate()).getForObject(uri,String.class);
            }
        }
        return null;
    }


    @Autowired
    DiscoveryClient number_client;
    public String getNumber(String service) {
        List<ServiceInstance> list = number_client.getInstances(service);
        if (list != null && list.size() > 0 ) {
            URI uri = list.get(0).getUri();
            if (uri !=null ) {
                return (new RestTemplate()).getForObject(uri,String.class);
            }
        }
        return null;
    }
}
