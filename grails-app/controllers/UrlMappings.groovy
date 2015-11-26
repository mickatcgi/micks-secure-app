class UrlMappings {

    static mappings = {

        // Implements the get/put/post/delete scaffolding for the TodoRestController
        "/api/todos" (resources: "todoRest")
        "/api/todos2" (resources: "todoRest2")

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
