package com.salesforce.heroku.api;

import com.salesforce.heroku.filter.CORSFilter;
import org.glassfish.jersey.jackson.JacksonFeature;
import org.glassfish.jersey.server.ResourceConfig;

/**
 * Created by gayathri on 11/8/15.
 */
public class Config extends ResourceConfig {

    public Config() {

        packages("com.salesforce.heroku");
        register(JacksonFeature.class);
        register(CORSFilter.class);

    }

}
