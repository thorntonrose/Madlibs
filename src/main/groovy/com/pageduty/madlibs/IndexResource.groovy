package com.pagerduty.madlibs

import groovy.xml.*
import javax.ws.rs.GET
import javax.ws.rs.Path
import javax.ws.rs.Produces
import javax.ws.rs.core.MediaType
import javax.ws.rs.core.Response
import javax.ws.rs.core.Response.Status
import org.slf4j.LoggerFactory

/**
 * Index resource.
 * 
 * @author Thornton
 */
@Path("")
class IndexResource {
    private static final log = LoggerFactory.getLogger(IndexResource)

    @GET
    @Produces(MediaType.TEXT_HTML)
    Response getIndex() {
        log.debug "getIndex ... "

        try {
            def writer = new StringWriter()
            
            new MarkupBuilder(writer).with {
               html {
                  head {
                     title("Index")
                  }
                  body {
                     a(href: "/input", "Input")
                     a(href: "/show", "List")
                  }
               }
            }

            Response.ok().entity(writer.toString()).build()
        } catch(Exception e) {
            log.error e.message, e
            throw e
        }
    }
}