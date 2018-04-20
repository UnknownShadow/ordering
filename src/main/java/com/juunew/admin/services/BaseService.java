package com.juunew.admin.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;

/**
 * Created by guange on 17/07/2017.
 */
public abstract class BaseService {
    protected Logger logger = LoggerFactory.getLogger(getClass());

    @Value("${server.token}")
    protected  String token;

    @Value("${server.url}")
    protected String server;

}
