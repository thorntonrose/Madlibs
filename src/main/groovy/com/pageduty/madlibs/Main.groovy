package com.pagerduty.madlibs

import javax.ws.rs.core.UriBuilder
import org.glassfish.jersey.jetty.JettyHttpContainerFactory
import org.slf4j.LoggerFactory

class Main {
    private static final log = LoggerFactory.getLogger(Main)

    static void main(String[] args) {
        try {
            def cli = new CliBuilder(usage: "${this.class.simpleName} [options]", header: "options:")
            cli._(longOpt: "env", args: 1, argName: "name", "Environment name (see app.conf)")
            cli._(longOpt: "port", args: 1, argName: "port", "Web app port")
            def opt = cli.parse(args)
            if (! opt) { return }

            if (opt.env) {
                System.setProperty("env", opt.env)
                Config.env // force load
            }

            if (opt.port) {
                Config.props.webapp.port = opt.port as int
            }

            def server = JettyHttpContainerFactory.createServer(
               UriBuilder.fromUri("http://0.0.0.0").port(Config.props.webapp.port).build(),
               new ServerResourceConfig(), false
            )

            server.start()
            server.join()
        } catch(Exception e) {
            log.error e.message, e
            log.info "exit"
            System.exit(1)
        }
    }
}