package com.factory.builder;

import com.factory.model.database.document.UserDocument;
import com.factory.model.database.document.concrete.UserAdmin;
import com.factory.model.database.document.concrete.UserClient;
import com.factory.model.database.document.concrete.UserEditor;
import com.factory.model.request.UserRequest;
import com.factory.model.response.UserResponse;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class UserBuilder {

    public static UserAdmin requestToEntityAdmin(UserRequest request) {
        return UserAdmin.builder()
                .type(request.getType())
                .email(request.getEmail())
                .status(Boolean.TRUE)
                .createDate(LocalDateTime.now())
                .name(request.getName())
                .build();
    }
    public static UserClient requestToEntityClient(UserRequest request) {
        return UserClient.builder()
                .type(request.getType())
                .email(request.getEmail())
                .status(Boolean.TRUE)
                .createDate(LocalDateTime.now())
                .name(request.getName())
                .build();
    }
    public static UserEditor requestToEntityEditor(UserRequest request) {
        return UserEditor.builder()
                .type(request.getType())
                .email(request.getEmail())
                .createDate(LocalDateTime.now())
                .status(Boolean.TRUE)
                .name(request.getName())
                .build();
    }


    public static UserResponse entityToResponseCreate(UserDocument entity) {
        return UserResponse.builder()
                .code(entity.getId())
                .createDate(entity.getCreateDate().toString())
                .status(entity.isStatus())
                .build();
    }


    public static <T extends UserDocument> UserResponse entityToResponse(T entity) {
        if (entity instanceof UserAdmin) {
            var entityCast = (UserAdmin) entity;
            return UserResponse.builder()
                    .code(entity.getId())
                    .status(entity.isStatus())
                    .createDate(entity.getCreateDate().toString())
                    .name(entity.getName())
                    .email(entity.getEmail())
                    .type(entity.getType())
                    .build();
        }else if(entity instanceof UserClient){
            var entityCast = (UserClient) entity;
            return UserResponse.builder()
                    .code(entity.getId())
                    .status(entity.isStatus())
                    .createDate(entity.getCreateDate().toString())
                    .name(entity.getName())
                    .email(entity.getEmail())
                    .type(entity.getType())
                    .build();
        }
        else {
            return UserResponse.builder()
                    .code(entity.getId())
                    .status(entity.isStatus())
                    .createDate(entity.getCreateDate().toString())
                    .name(entity.getName())
                    .email(entity.getEmail())
                    .type(entity.getType())
                    .build();
        }
    }

    public static <T extends UserDocument> List<UserResponse> listEntityToResponse(List<T> users) {
        var listResponse = new ArrayList<UserResponse>();
        users.forEach(item -> listResponse.add(entityToResponse(item)));
        return listResponse;
    }
}
