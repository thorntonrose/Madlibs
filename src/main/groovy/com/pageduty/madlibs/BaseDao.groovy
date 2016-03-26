package com.pagerduty.madlibs

import com.mongodb.MongoClient
import com.mongodb.ServerAddress
import java.util.Map
import org.bson.Document
import org.slf4j.LoggerFactory

/**
 * Base data access object.
 *
 * @author ethorro
 */
abstract class BaseDao {
    private static final log = LoggerFactory.getLogger(BaseDao)
    static mongo
    static db

    static {
        try {
            mongo = new MongoClient(new ServerAddress(Config.props.db.host, Config.props.db.port))
            db = mongo.getDatabase(Config.props.db.name)
        } catch(Exception e) {
           log.error e.message, e
           throw e
        }
    }

    def collectionName
    def collection

    BaseDao(collectionName) {
        this.collectionName = collectionName
        this.collection = db.getCollection(collectionName)
    }

    def newId() {
        (System.currentTimeMillis() + new Object().hashCode()) as String
    }

    def newEntity(Map doc = [:]) {
        [:] + doc
    }

    def insert(Map doc) {
        log.debug "insert ..."
        collection.insertOne(doc as Document)
    }

    def update(Map doc, Map filter) {
        log.debug "update ..."
        collection.replaceOne(filter as Document, doc as Document)
    }

    def delete(Map filter) {
        log.debug "delete ..."
        collection.deleteOne(filter as Document)
    }

    def find(Map filter = [:]) {
        log.debug "find: filter: {} ...", filter
        collection.find(filter as Document).collect()
    }

    def findFirst(Map filter = [:]) {
        log.debug "findFirst ..."
        def list = find(filter)
        list ? list.first() : null
    }

    def count(Map filter = [:]) {
        log.debug "count ..."
        collection.count(filter as Document)
    }

    def drop() {
        log.debug "drop ..."
        collection.drop()
    }

    def truncate() {
        log.debug "truncate ..."
        collection.deleteMany([] as Document)
    }
}