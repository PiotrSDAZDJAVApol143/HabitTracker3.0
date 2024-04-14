package com.example.gatewayapi.utils;

import com.example.gatewayapi.service.Carousel;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.core.io.buffer.DefaultDataBufferFactory;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import reactor.core.publisher.Flux;
import org.reactivestreams.Publisher;
import reactor.core.publisher.Mono;
import lombok.extern.slf4j.Slf4j;

import java.sql.Timestamp;
import java.util.List;

@Component
@Slf4j
public class AuthenticationFilter extends AbstractGatewayFilterFactory<AuthenticationFilter.Config> {

    private final RouteValidator validator;
    private final RestTemplate template;
    private final JwtUtil jwtUtil;
    @Value("${spring.profiles.active}")
    private String activeProfile;
    private Carousel carousel;

    public AuthenticationFilter(JwtUtil jwtUtil, RestTemplate restTemplate, RouteValidator validator,Carousel carousel) {
        super(Config.class); // zawsze musi być pierwsza super  Config klasa.
        this.carousel = carousel;
        this.jwtUtil = jwtUtil;
        this.template = restTemplate;
        this.validator = validator;
    }

    @Override
    public GatewayFilter apply(Config config) {
        return ((exchange, chain) -> {
            if (validator.isSecure.test(exchange.getRequest())) {
                if (!exchange.getRequest().getCookies().containsKey(HttpHeaders.AUTHORIZATION) && !exchange.getRequest().getCookies().containsKey("refresh")) {
                    exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
                    exchange.getResponse().getHeaders().setContentType(MediaType.APPLICATION_JSON);

                    StringBuilder stringBuilder = new StringBuilder("{\n")
                            .append("\"timestamp\": \"")
                            .append(new Timestamp(System.currentTimeMillis()))
                            .append("\",\n")
                            .append("\"message\": \"Specified token is empty or invalid :/\",\n")
                            .append("\"code\": \"A3\"\n")
                            .append("}");

                    return exchange.getResponse().writeWith(Mono.just(exchange.getResponse()
                            .bufferFactory()
                            .wrap((stringBuilder.toString()).getBytes())));
                }

                HttpCookie authCookie = exchange.getRequest().getCookies().get(HttpHeaders.AUTHORIZATION).get(0);
                HttpCookie refreshCookie = exchange.getRequest().getCookies().get("refresh").get(0);
                try {
                    if (activeProfile.equals("test")){
                        jwtUtil.validateToken(authCookie.getValue());
                    }else{
                        String cookies = new StringBuilder()
                                .append(authCookie.getName())
                                .append("=")
                                .append(authCookie.getValue())
                                .append(";")
                                .append(refreshCookie.getName())
                                .append("=")
                                .append(refreshCookie.getValue()).toString();

                        HttpHeaders httpHeaders = new HttpHeaders();
                        httpHeaders.add("Cookie",cookies);
                        HttpEntity<Object> entity = new HttpEntity<>(httpHeaders);
                        ResponseEntity<String> response = template.exchange("http://"+carousel.getUriAuth()+"/auth/validate", HttpMethod.GET,entity, String.class);

                        if (response.getStatusCode() == HttpStatus.OK){ // jeśli Header jest OK
                            List<String> cookiesList = response.getHeaders().get(HttpHeaders.SET_COOKIE); // pobierzemy wszystkie ciasteczka
                            if (cookiesList != null) { //jeśli lista ciastek nie jest nullem
                                List<java.net.HttpCookie> httpCookie = java.net.HttpCookie.parse(cookiesList.get(0)); // parsujemy istniejące ciastka
                                for (java.net.HttpCookie cookie: httpCookie){ // zmieniamy kolejno każde ciastko za pomocą Excange get Cookies a potem add
                                    exchange.getResponse().getCookies().add(cookie.getName(),
                                            ResponseCookie.from(cookie.getName(),cookie.getValue())
                                                    .domain(cookie.getDomain())
                                                    .path(cookie.getPath())
                                                    .maxAge(cookie.getMaxAge())
                                                    .secure(cookie.getSecure())
                                                    .httpOnly(cookie.isHttpOnly())
                                                    .build());
                                }
                            }
                        }
                    }
                } catch (Exception e) {
                    exchange.getResponse().writeWith(Flux.just(new DefaultDataBufferFactory().wrap(e.getMessage().getBytes())));
                }
            }
            return chain.filter(exchange);
        });
    }


    public static class Config {

    }
}
