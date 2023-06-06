package com.trainme.treainmeapp.security;


/**
 * It contains the constants that will be used in the security configuration
 */
public class SecurityConstants {
    /**
     * A constant that will be used in the security configuration.
     * /** is defined for common URL
     */
    public static final String SING_UP_URLS = "/api/auth/**"; //** означает любой урл

    // A secret key that will be used to sign the JWT token.
    public static final String SECRET = "poliskyr";

    // A prefix that will be added to the JWT token in the Authorization header.
    public static final String TOKEN_PREFIX = "Bearer ";

    // A constant that will be used in the security configuration.
    public static final String HEADER_STRING = "Authorization";

    // A constant that will be used in the security configuration.
    public static final String CONTENT_TYPE = "application/json";

    // Log out time expiration, automatic log out, 10 min
    public static final long EXPIRATION_TIME = 600_000_000_000L;

}

