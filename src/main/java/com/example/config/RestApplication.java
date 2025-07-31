package com.example.config;

import jakarta.ws.rs.ApplicationPath;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Application;

@ApplicationPath("/")
@Path("health-check")
public class RestApplication extends Application {

    @GET
    public String healthCheck() {
        return "OK";
    }

}