package com.example.gatewayapi.utils;

import org.example.module.HttpMethod;
import org.example.module.Role;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Predicate;

@Component
public class RouteValidator {
    public Set<Endpoint> openApiEndpoints = new HashSet<>(List.of(
            new Endpoint("/auth/logout", HttpMethod.GET, Role.USER),
            new Endpoint("/auth/register",HttpMethod.POST,Role.USER),
            new Endpoint("/auth/login",HttpMethod.POST,Role.USER),
            new Endpoint("/auth/validate",HttpMethod.GET,Role.USER),
            new Endpoint("/auth/activate",HttpMethod.GET,Role.USER),
            new Endpoint("/auth/authorize",HttpMethod.GET,Role.USER),
            new Endpoint("/auth/reset-password",HttpMethod.PATCH,Role.USER),
            new Endpoint("/auth/reset-password",HttpMethod.POST,Role.USER),
            new Endpoint("/gateway",HttpMethod.POST,Role.USER),
            new Endpoint("/auto-login",HttpMethod.GET,Role.USER),
            new Endpoint("/logged-in",HttpMethod.GET,Role.USER)
    )
    );

    private Set<Endpoint> adminEndpoints = new HashSet<>();

    public void addEndpoints(List<Endpoint> endpointList){
        for (Endpoint endpoint: endpointList){
            if (endpoint.getRole().name().equals(Role.ADMIN.name())) {
                adminEndpoints.add(endpoint);
            }
            if (endpoint.getRole().name().equals(Role.USER.name())) {
                openApiEndpoints.add(endpoint);
            }
        }
    }

    public Predicate<ServerHttpRequest> isAdmin =
            request -> adminEndpoints
                    .stream()
                    .anyMatch(value -> request.getURI()
                            .getPath()
                            .contains(value.getUrl())
                            && request.getMethod().name().equals(value.getHttpMethod().name()));

    public Predicate<ServerHttpRequest> isSecure =
            request -> openApiEndpoints
                    .stream()
                    .noneMatch(value -> request.getURI()
                            .getPath()
                            .contains(value.getUrl())
                            && request.getMethod().name().equals(value.getHttpMethod().name()));
}