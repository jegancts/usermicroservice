package com.userdetail.microservice.controller;
import com.userdetail.microservice.dto.UserDetailsDto;
import com.userdetail.microservice.entity.UserDetails;
import com.userdetail.microservice.service.UserContactDetailsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.SwaggerDefinition;
import io.swagger.annotations.Tag;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


@RestController
@Api(tags = {"UserDetail"})
@SwaggerDefinition(tags = {@Tag(name = "UserDetailController", description = "User Detail REST API")})
@AllArgsConstructor
public class UserContactDetailsController {

    private static final Logger logger = LoggerFactory.getLogger(UserContactDetailsController.class);

    @Autowired
    private UserContactDetailsService userContactDetailsService;


    @PostMapping(value="/user", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity saveUser(@Valid @RequestBody UserDetails userDetails){
        logger.info("User  details - {}", userDetails);
        return userContactDetailsService.saveUserDetails(userDetails);
    }

    @GetMapping(value = "/user/{ids}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity getUsersByIds(@RequestParam ("ids") String ids)  {
        return userContactDetailsService.getUserDetailsByIds(ids);
    }

    @GetMapping(value = "/users", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity getAllUsers() {
        return userContactDetailsService.getAllUserDetails();
    }

    @GetMapping(value="/user/{id}")
    public ResponseEntity getUserById(@PathVariable long id) {
        return userContactDetailsService.getUserDetailsById(id);
    }

    @DeleteMapping (value="/user/{id}")
    public ResponseEntity deleteUserById(@PathVariable long id) {
       return userContactDetailsService.deleteUserDetailsById(id);
    }



   /*@PutMapping("/user")
    private ResponseEntity update(@Valid @RequestBody UserDetails userDetails)
    {
        return userContactDetailsService.saveOrUpdateUserDetails(userDetails);

    }*/

    @PutMapping("/user/{id}")
    public ResponseEntity updateUserById(@Valid @RequestBody UserDetailsDto userDetailsDto , @PathVariable long id)
    {
        return userContactDetailsService.updateUserDetails(id,userDetailsDto);
    }




}
