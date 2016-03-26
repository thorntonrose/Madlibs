package com.pagerduty.madlibs

import org.glassfish.jersey.server.ResourceConfig

/**
 * Server resource config.
 *
 * @author ethorro
 */
class ServerResourceConfig extends ResourceConfig {
    ServerResourceConfig() {
        packages("com.pagerduty.madlibs")
    }
}