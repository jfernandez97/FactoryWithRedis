package com.factory.service.impl;

import com.factory.builder.UserBuilder;
import com.factory.cache.CacheClient;
import com.factory.model.UserFactory;
import com.factory.model.database.document.UserDocument;
import com.factory.model.request.UserRequest;
import com.factory.model.response.UserResponse;
import com.factory.repository.UserRepository;
import com.factory.service.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository repository;
    private final UserFactory userFactory = new UserFactory();
    private final CacheClient<UserDocument> cache;

    @Override
    public UserResponse create(UserRequest request) {
        UserResponse response=null;
        try {
            var entity = userFactory.createUser(request.getType(), request);
            var entitySaved = repository.save(entity);
            saveUserInCache(entity);
            response=UserBuilder.entityToResponseCreate(entitySaved);
        }catch (Exception e){
            e.printStackTrace();
        }

        return response;
    }
    @Override
    public UserResponse findById(String id) {
        UserResponse response = null;
        try {
            var dataFromCache = cache.recover(id.toString(), UserDocument.class);
            if (!Objects.isNull(dataFromCache)) {
                return UserBuilder.entityToResponse(dataFromCache);
            }
            Optional<UserDocument> optional = repository.findById(id);
            if (optional.isPresent()) {
                response = UserBuilder.entityToResponse(optional.get());
                saveUserInCache(optional.get());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return response;
    }
    @Override
    public UserResponse update(String id, UserRequest request){
        UserResponse response=null;
        try {
            if(findById(id)!=null){
                var entity=userFactory.createUser(request.getType(),request);
                entity.setId(id);
                entity.setUpdateDate(LocalDateTime.now());
                var entitySaved =repository.save(entity);
                saveUserInCache(entity);
                response = UserBuilder.entityToResponseCreate(entitySaved);
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return response;
    }

    @Override
    public List<UserResponse> getAllUsers() {
        return UserBuilder.listEntityToResponse(repository.findAll());
    }
    private UserResponse saveUserInCache(UserDocument user)throws JsonProcessingException{
        var userInCache=cache.save(user.getId().toString(),user);
        return UserBuilder.entityToResponseCreate(userInCache);
    }

}
