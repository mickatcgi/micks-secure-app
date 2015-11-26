class UrlMappings {

    static mappings = {

        // Implements the get/put/post/delete scaffolding for the TodoRestController
        "/api/todos" (resources: "todoRest")
        "/api/todes" (resources: "todeRest")

        "/$controller/$action?/$id?(.$format)?"{
            constraints {
                // apply constraints here
            }
        }

        "/"(view:"/index")
        "500"(view:'/error')
        "404"(view:'/notFound')
    }
}
