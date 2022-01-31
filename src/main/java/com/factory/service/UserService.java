package com.factory.service;

import com.factory.model.request.UserRequest;
import com.factory.model.response.UserResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.apache.catalina.User;

import java.util.List;

public interface UserService {

    UserResponse create(UserRequest request);
    UserResponse update(String id ,UserRequest request) ;
    UserResponse findById(String id);
    List<UserResponse> getAllUsers();
}
