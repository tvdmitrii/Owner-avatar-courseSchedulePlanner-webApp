package com.turygin.utility;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Properties;

/**
 * Singleton class that provides WebApp configuration properties.
 */
public class Config {
    private static final Logger LOG = LogManager.getLogger(Config.class);

    /** WebApp properties resource path. */
    private static final String CONFIG_RESOURCE = "/webapp.properties";

    /** Properties instance. */
    private static Properties properties = null;

    /** Private empty constructor to prevent instance creation. */
    private Config() {}

    /**
     * Returns properties or loads them from file when accessed for the first time.
     * @return WebApp configuration properties
     */
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
