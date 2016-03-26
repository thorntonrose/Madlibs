package com.pagerduty.madlibs

import com.fasterxml.jackson.databind.ObjectMapper
//import com.fasterxml.jackson.databind.DeserializationFeature

/**
 * JSON utility methods.
 *
 * @author ethorro
 */
class Json {
    static final mapper = new ObjectMapper()
    //static final mapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)

    private Json() {}

    static toJson(obj, pretty = true) {
        def writer = pretty ? mapper.writerWithDefaultPrettyPrinter() : mapper.writer()
        writer.writeValueAsString(obj)
    }

    static fromJson(text, objClass = Map) {
        mapper.readValue(text, objClass)
    }

    static convert(obj, objClass = Map) {
        mapper.convertValue(obj, objClass)
    }
}