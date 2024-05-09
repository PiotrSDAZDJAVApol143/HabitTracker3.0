package com.example.gatewayapi.service;

import com.netflix.appinfo.InstanceInfo;
import com.netflix.discovery.EurekaClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class Carousel {

    private final EurekaClient eurekaClient;
    List<InstanceInfo> instances = new ArrayList<>();
    int currentIndex = 0;

    public Carousel(EurekaClient eurekaClient){
        this.eurekaClient = eurekaClient;
        try{
            initAuthCarousel();
        }catch (NullPointerException e){
        }
        events();
    }

    public String getUriAuth(){
        StringBuilder stringBuilder = new StringBuilder();
        InstanceInfo instance = instances.get(currentIndex);
        stringBuilder.append(instance.getIPAddr()).append(":").append(instance.getPort());
        if (instances.size()-1 == currentIndex){
            currentIndex = 0;
        }else {
            currentIndex++;
        }
        return stringBuilder.toString();
    }

    private void events(){
        eurekaClient.registerEventListener(eurekaEvent -> {
            initAuthCarousel();
        });
        eurekaClient.unregisterEventListener(eurekaEvent -> {
            try{

                initAuthCarousel();
            }catch (NullPointerException e){

            }

        });
    }

    private void initAuthCarousel() throws NullPointerException {
        instances = eurekaClient.getApplication("Authorization-Api").getInstances();
    }
}

