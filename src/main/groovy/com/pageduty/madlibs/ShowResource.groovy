package com.pagerduty.madlibs

import javax.ws.rs.GET
import javax.ws.rs.POST
import javax.ws.rs.Path
import javax.ws.rs.Produces
import javax.ws.rs.FormParam
import javax.ws.rs.PathParam
import javax.ws.rs.QueryParam
import javax.ws.rs.core.MediaType
import javax.ws.rs.core.Response
import javax.ws.rs.core.Response.Status
import groovy.xml.MarkupBuilder
import org.bson.types.ObjectId
import org.slf4j.LoggerFactory

/**
 * Show resource.
 *
 * @author ethorro
 */
@Path("show")
class ShowResource {
    private static final log = LoggerFactory.getLogger(ShowResource)
    def wordSetDao = new WordSetDao()

    @POST
    @Produces(MediaType.TEXT_HTML)
    Response saveAndShow(@FormParam("name") String name, @FormParam("place") String place, @FormParam("verb") String verb,
            @FormParam("noun") String noun) {
        log.debug "saveAndShow: $name, $place, $verb, $noun ... "

        try {
            def wordSet = wordSetDao.newEntity(name: name, place: place, verb: verb, noun: noun)
            log.debug "save: wordSet: $wordSet"
            if (!wordSetDao.findFirst(wordSet)) { wordSetDao.insert(wordSet) }
        } catch(Exception e) {
            log.error e.message, e
            throw e
        }

        show()
    }

    @GET
    @Produces(MediaType.TEXT_HTML)
    Response show() {
        log.debug "show ... "

        try {
            def wordSets = wordSetDao.find()
            log.debug "show: wordSets.size: ${wordSets.size()}"
            def writer = new StringWriter()

            new MarkupBuilder(writer).with {
               html {
                  head {
                     title("Show")
                  }
                  body {
                      table(border: "1") {
                         tr {
                            th("Name")
                            th("Place")
                            th("Past-tense Verb")
                            th("Noun")
                            th { mkp.yieldUnescaped("&nbsp;") }
                         }

                         wordSets.each { wordSet->
                             tr {
                                 td(wordSet.name)
                                 td(wordSet.place)
                                 td(wordSet.verb)
                                 td(wordSet.noun)
                                 td { a(href: "/show/${wordSet._id}", "View") }
                             }
                         }
                      }
                      
                      hr()

                      a(href: "/", "Home")
                      a(href: "/input", "Input")
                  }
               }
            }

            Response.ok().entity(writer.toString()).build()
        } catch(Exception e) {
            log.error e.message, e
            throw e
        }
    }

    @GET
    @Produces(MediaType.TEXT_HTML)
    @Path("/{id}")
    Response show(@PathParam("id") String id) {
        log.debug "showMadlib: id: $id ..."

        try {
            def status = Status.OK
            def writer = new StringWriter()
            def wordSet = wordSetDao.findFirst(_id: new ObjectId(id))

            new MarkupBuilder(writer).with {
               html {
                  head {
                     title("Madlib")
                  }
                  body {
                     if (wordSet) {
                        p("One day, ${wordSet.name} went to ${wordSet.place} and ${wordSet.verb} the ${wordSet.noun}.")
                     } else {
                        p("Not found: id: $id")
                        status = Status.NOT_FOUND
                     }
                     
                     hr()

                     a(href: "/", "Home")
                     a(href: "/input", "Input")
                     a(href: "/show", "List")
                  }
               }
            }

            Response.status(status).entity(writer.toString()).build()
        } catch(Exception e) {
            log.error e.message, e
            throw e
        }
    }
}