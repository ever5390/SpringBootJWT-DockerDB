package com.rpej.security.User;

import com.rpej.security.Exceptions.domain.EmailExistsException;
import com.rpej.security.Exceptions.domain.EmailNotFoundException;
import com.rpej.security.Exceptions.domain.UserNameExistsException;
import com.rpej.security.Exceptions.domain.UserNameNotFoundException;
import com.rpej.security.dtoAuth.HttpResponse;
import com.rpej.security.dtoAuth.RegisterRequest;

import java.util.List;

public interface UserService {

    List<User> showUsers();

    HttpResponse registerNewUser(RegisterRequest request) throws UserNameExistsException, EmailExistsException;

    HttpResponse updateUser(RegisterRequest request) throws UserNameExistsException, EmailExistsException, EmailNotFoundException, UserNameNotFoundException;

}