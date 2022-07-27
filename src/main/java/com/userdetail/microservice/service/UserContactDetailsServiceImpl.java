package com.userdetail.microservice.service;

import com.userdetail.microservice.dao.UserContactDetailsRepository;
import com.userdetail.microservice.dto.UserDetailsDto;
import com.userdetail.microservice.entity.UserDetails;
import com.userdetail.microservice.exceptions.UserIdvalidationException;
import com.userdetail.microservice.exceptions.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class UserContactDetailsServiceImpl implements UserContactDetailsService{

    @Autowired
    private UserContactDetailsRepository userContactDetailsRepository;

    @Override
    public ResponseEntity saveUserDetails(UserDetails userDetails) {
        return new ResponseEntity(userContactDetailsRepository.save(userDetails), HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity getUserDetailsById(long id) {

        Optional<UserDetails> userDetails = userContactDetailsRepository.findById(id);
        if(!userDetails.isEmpty())
            return new ResponseEntity(userDetails.get(), HttpStatus.OK);
        throw new UserNotFoundException("user details not found in the database for Id : " + id);
    }

    @Override
    public ResponseEntity getAllUserDetails() {
        List<UserDetails> getAllUsers = userContactDetailsRepository.findAll();
        if (!getAllUsers.isEmpty())
            return new ResponseEntity<>(getAllUsers, HttpStatus.OK);
        throw new UserNotFoundException("no user details found in the database");
    }


    @Override
    public ResponseEntity deleteUserDetailsById(long id) {

        UserDetails userDetails = userContactDetailsRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User not found for this id :: " + id));
        userContactDetailsRepository.deleteById(id);
        return new ResponseEntity<>("User Detail has been deleted for Id : "+id,HttpStatus.OK);

    }

    @Override
    public ResponseEntity getUserDetailsByIds(String ids) {
        Matcher matcher = Pattern.compile("[0-9]+(,[0-9]+)+").matcher(ids);
        if(!matcher.matches())
         throw new UserIdvalidationException("please enter valid ids with comma sepereted eg: 1,2,3");

        List<Long> listIds = Stream.of(ids.split(",")).map(Long::parseLong).collect(Collectors.toList());
        List<UserDetails>  listOfUserDetails = userContactDetailsRepository.findAllById(listIds);
        if(listOfUserDetails.isEmpty())
        throw new NoSuchElementException("user details for the requested id's not present in database : " + listIds);
        else
            return new ResponseEntity<>(listOfUserDetails ,HttpStatus.OK);
    }



    /*public ResponseEntity saveOrUpdateUserDetails(UserDetails userDetails) {
        return new ResponseEntity(userContactDetailsRepository.save(userDetails), HttpStatus.CREATED);


    }*/

    @Override
    public ResponseEntity updateUserDetails(long id,UserDetailsDto userDetailsDto ) {

        Optional<UserDetails> userDetails = userContactDetailsRepository.findById(id);
        if(!userDetails.isEmpty()) {
           UserDetails userDetailsUpdate = userDetails.get();

            userDetailsUpdate.setFirstName(userDetailsDto.getFirstName());
            userDetailsUpdate.setLastName(userDetailsDto.getLastName());
            userDetailsUpdate.getAddress().setDoorNo(userDetailsDto.getAddress().getDoorNo());
            userDetailsUpdate.getAddress().setStreetName(userDetailsDto.getAddress().getStreetName());
            userDetailsUpdate.getAddress().setPostCode(userDetailsDto.getAddress().getPostCode());
            return  new ResponseEntity(userContactDetailsRepository.save(userDetailsUpdate), HttpStatus.OK);
        }
        else {
        throw new UserNotFoundException("user details not found in the database for Id : " + id); }
    }

}
