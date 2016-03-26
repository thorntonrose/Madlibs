package com.pagerduty.madlibs

import org.slf4j.LoggerFactory

/**
 * Application configuration.
 *
 * @author ethorro
 */
class Config {
    private static final log = LoggerFactory.getLogger(Config)
    static env = System.properties.env ?: "prod"
    static props = new ConfigSlurper(env).parse(Config.class.classLoader.getResource("app.conf"))

    static {
        try {
            log.info "<clinit>: env: $env"

            if (log.isDebugEnabled()) {
                log.debug "<clinit>: props:\n{}", Json.toJson(props)
            }
        } catch(Exception e) {
           log.error e.message, e
           throw e
        }
    }

    private Config() {}
}