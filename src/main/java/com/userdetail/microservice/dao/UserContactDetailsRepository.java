package com.userdetail.microservice.dao;

import com.userdetail.microservice.entity.UserDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserContactDetailsRepository extends JpaRepository<UserDetails, Long> {


}
