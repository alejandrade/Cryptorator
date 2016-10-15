package com.gigamog.cryptorator;

import javax.ws.rs.ApplicationPath;

import org.glassfish.jersey.server.ResourceConfig;

import com.gigamog.cryptorator.endpoints.TheEndpoints;

@ApplicationPath("/")
public class MyApplication extends ResourceConfig {

    
    public MyApplication() {
        packages(TheEndpoints.class.getPackage().getName());
        register(GsonJerseyProvider.class);

    }
    
}
