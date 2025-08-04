package com.example.controller;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;

@Path("health-check")
public class HealthController {

    @GET
    public String healthCheck() {
        return "OK";
    }

}
