package com.cinema.api.resources;

import com.cinema.auth.JwtUtil;
import com.cinema.api.model.ErrorResponse;
import com.cinema.api.model.token.TokenRequest;
import com.cinema.api.model.token.TokenResponse;
import com.cinema.data.entity.User;
import com.cinema.data.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

/**
 * TokenResource.
 */
@RestController
public class TokenResource {

    @Autowired
    private UserRepository userRepository;

    @RequestMapping(path = "/public/token", method = RequestMethod.POST, produces = "application/json")
    public ResponseEntity getToken(@RequestBody TokenRequest request) {
        Optional<User> userOptional = userRepository.findByEmailAndPassword(request.getEmail(), request.getPassword());
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            return ResponseEntity.ok(new TokenResponse(JwtUtil.generateJWT(user.getEmail(), user.getRole())));
        }
        return ResponseEntity.badRequest().body(new ErrorResponse("Wrong email or password"));
    }

}
