package com.example.gatewayapi.controller;

import com.example.gatewayapi.utils.RouteValidator;
import lombok.RequiredArgsConstructor;
import org.example.module.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.example.gatewayapi.utils.Endpoint;

import java.util.List;


@RestController
@RequestMapping(value = "/gateway")
@RequiredArgsConstructor
public class RegistrationController {

    private final RouteValidator routeValidator;

    @PostMapping
    public ResponseEntity<Response> register(@RequestBody List<Endpoint> endpoints){
        routeValidator.addEndpoints(endpoints);
        return ResponseEntity.ok(new Response("Successfully register new endpoints"));
    }
}

