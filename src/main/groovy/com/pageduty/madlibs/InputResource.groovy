package com.pagerduty.madlibs

import groovy.xml.*
import javax.ws.rs.GET
import javax.ws.rs.POST
import javax.ws.rs.FormParam
import javax.ws.rs.Path
import javax.ws.rs.Produces
import javax.ws.rs.core.MediaType
import javax.ws.rs.core.Response
import javax.ws.rs.core.Response.Status
import org.slf4j.LoggerFactory

/**
 * Index resource.
 *
 * @author ethorro
 */
@Path("input")
class InputResource {
    private static final log = LoggerFactory.getLogger(InputResource)
    def wordSetDao = new WordSetDao()

    @GET
    @Produces(MediaType.TEXT_HTML)
    Response getInput() {
        log.debug "getInput ... "

        try {
            def writer = new StringWriter()
            
            new MarkupBuilder(writer).with {
               html {
                  head {
                     title("Input")
                  }
                  body {
                     form(id: "inputForm", action: "/show", method: "POST") {
                         table {
                             tr {
                                td("Name of person")
                                td { input(name: "name", type: "text") }
                             }
                             tr {
                                td("Place")
                                td { input(name: "place", typ: "text") }
                             }
                             tr { 
                                td("Past-tense verb")
                                td { input(name: "verb", type: "text") }
                             }
                             tr { 
                                td("Noun")
                                td { input(name: "noun", type: "text") }
                             }
                         }

                         input(name: "Next", type: "submit")
                         
                         hr()

                         a(href: "/", "Home")
                         a(href: "/show", "List")
                     }
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