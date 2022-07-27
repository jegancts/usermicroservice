package com.userdetail.microservice.service;

import com.userdetail.microservice.dto.UserDetailsDto;
import com.userdetail.microservice.entity.UserDetails;
import org.springframework.http.ResponseEntity;

public interface UserContactDetailsService {
    ResponseEntity saveUserDetails(UserDetails userDetails);
    ResponseEntity getUserDetailsById(long id);
    ResponseEntity deleteUserDetailsById(long id);
    ResponseEntity getUserDetailsByIds(String ids);
    ResponseEntity getAllUserDetails();
    //ResponseEntity saveOrUpdateUserDetails(UserDetails userDetails);
    ResponseEntity updateUserDetails(long id, UserDetailsDto userDetailsDto);
}
