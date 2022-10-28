package com.k7cl.bjypc.covid.pojo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public record Response(boolean success, String msg, Object data) {
    private static final Logger logger = LoggerFactory.getLogger(Response.class);

    public Response(boolean success, String msg, Object data) {
        this.success = success;
        this.msg = msg;
        this.data = data;

        if (!success) {
            logger.error("Error  Msg: " + msg);
        }
    }
}
