package com.factory.model;

import com.factory.builder.UserBuilder;
import com.factory.model.database.document.UserDocument;
import com.factory.model.request.UserRequest;
import lombok.Data;

@Data
public class UserFactory {

    public UserDocument createUser(String type, UserRequest request) {
        switch (type) {
            case "ADMIN":
                return UserBuilder.requestToEntityAdmin(request);
            case "CLIENT":
                return UserBuilder.requestToEntityClient(request);
            case "EDITOR":
                return UserBuilder.requestToEntityEditor(request);
            default:
                return null;
        }
    }

}
