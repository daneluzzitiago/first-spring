package br.com.simple.config;

import java.util.concurrent.TimeUnit;

public class SecurityConstants {
//    Authorization Bearer haueiahs94e812123gfd
    protected static final String SECRET = "SimpleSpringSecret";
    protected static final String TOKEN_PREFIX = "Bearer";
    protected static final String HEADER_STRING = "Authorization";
    protected static final String SIGN_UP_URL = "/users/sign-up";
    protected static final Long EXPIRATION_TIME = 86400000L;
//
//    public static void main(String[] args) {
//        System.out.println(TimeUnit.MILLISECONDS.convert(1,TimeUnit.DAYS));
//    }
}
