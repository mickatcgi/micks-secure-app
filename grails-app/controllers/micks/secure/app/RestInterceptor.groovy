package micks.secure.app

import grails.util.Environment


class RestInterceptor {

    public RestInterceptor() {
        match controller: ~/(todoRest|todeRest)/
    }

    boolean before() {

        log.info("Testing in --> in ${Environment.current} environment")
        log.info("Processing URL = ${request.forwardURI}")
        log.info("Testing format: ${request.format} and method: ${request.method} (by Mick)")

        // Test the request format
        if (!(request.format in ["json", "xml", "all"]) && !(request.method in ["DELETE", "GET", "HEAD"])) {
            log.info("Sorry... status: 415, text: Unrecognized request format: ${request.format} or method: ${request.method} (by Mick)")
            render status: 415, text: "Request has an unrecognized content type (by Mick)"
            return false
        }

        // Test the response format
        if (!(response.format in ["json", "xml", "all"])) {
            log.info("Sorry... status: 406, text: Response format: ${response.format} not supported (by Mick)")
            render status: 406, text: "Response format: ${response.format} not supported (by Mick)"
            return false
        }

        return true
    }

    boolean after() { true }

    void afterView() {
        // no-op
    }
}
