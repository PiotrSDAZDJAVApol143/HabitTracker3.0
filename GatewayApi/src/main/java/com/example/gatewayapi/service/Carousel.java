package com.example.gatewayapi.service;

import com.netflix.appinfo.InstanceInfo;
import com.netflix.discovery.EurekaClient;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class Carousel {

    private final EurekaClient eurekaClient;
    List<InstanceInfo> instances = new ArrayList<>();
    int currentIndex = 0;

    public Carousel(EurekaClient eurekaClient){
        this.eurekaClient = eurekaClient;
        initAuthCarousel();
        events();
    }

    public String getUriAuth(){  // Tworzymy adres IP z portem do wykorzystania w RestTemplate.
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

    private void events(){  //dwa EventListenery śledzące czy są dodawane czy usuwane obiekty instancji
        eurekaClient.registerEventListener(eurekaEvent -> {
            initAuthCarousel();
        });
        eurekaClient.unregisterEventListener(eurekaEvent -> {
            initAuthCarousel();
        });
    }

    private void initAuthCarousel() { //pobieramy wszystkie dostępne instancje dla Aplikacji z AuthorizationApi
        instances = eurekaClient.getApplication("AuthorizationApi").getInstances();
    }
}
