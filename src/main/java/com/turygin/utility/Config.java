package com.turygin.utility;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Properties;

public class Config {
    private static final Logger LOG = LogManager.getLogger(Config.class);
    private static final String CONFIG_RESOURCE = "/webapp.properties";
    private static Properties properties = null;

    private Config() {}

    public static Properties getProperties() {
        if (properties == null) {

            properties = new Properties();
            try {
                properties.load(Config.class.getResourceAsStream(CONFIG_RESOURCE));
            } catch (Exception e) {
                LogManager.getLogger().fatal("Unable to load WebApp configuration from {}", CONFIG_RESOURCE);
            }
        }

        return properties;
    }
}
