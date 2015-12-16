class UrlMappings {

    static mappings = {

        // Implements the get/put/post/delete scaffolding for the TodoRestController
//        "/api/todos" (resources: "todoRest") {
//
//            "/spaHome1" (controller: "todoRest", action: "spaHome1", method: "GET")
//            "/spaHome2" (controller: "todoRest", action: "spaHome2", method: "GET")
//        }

        "/api/todes" (resources: "todeRest") {

            "/singlePage" (controller: "todeRest", action: "singlePage", method: "GET")
            "/randomTodo" (controller: "todeRest", action: "randomTodo", method: "GET")
            action = [PUT: "save", POST: "save"]
        }

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
