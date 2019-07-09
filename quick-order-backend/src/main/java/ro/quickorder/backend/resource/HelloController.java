package ro.quickorder.backend.resource;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    @GetMapping("/hello")
    public String sayHello() {
        return "{\"message\": \"Hello, Woooorld!\"}";
    }

}
