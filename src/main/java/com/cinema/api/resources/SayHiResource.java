package com.cinema.api.resources;

import com.cinema.data.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SayHiResource {

    @Autowired
    private UserRepository userRepository;

    @RequestMapping(path = "/hello", method = RequestMethod.GET, produces = "application/json")
    public String sayHi() {
        return userRepository.findByEmailAndPassword("admin@cinema.com", "12345").get().toString();
        //return "hello 8081";
    }
}
