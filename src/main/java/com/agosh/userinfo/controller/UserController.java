package com.agosh.userinfo.controller;

import com.agosh.userinfo.service.IUserService;
import com.agosh.userinfo.model.User;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.MissingRequiredPropertiesException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    IUserService userService;

    @GetMapping(value = "/{email}")
    public ResponseEntity<User> getUserInfo(@PathVariable("email") String email) {
        if(email == null || email.length() == 0)throw new MissingRequiredPropertiesException();
        User user = userService.getUserInfo(email);
        if(user == null) return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        return new ResponseEntity<User>(user, HttpStatus.OK);
    }

    @PostMapping()
    ResponseEntity<User> addUser(@Valid @RequestBody User user) {
        User createdUser = userService.addUser(user);
        return ResponseEntity.ok(createdUser);
    }

    @PutMapping()
    ResponseEntity<User> updateUser(@Valid @RequestBody User user) {
        User createdUser = userService.updateUser(user);
        return ResponseEntity.ok(createdUser);
    }

    @DeleteMapping(value = "/{email}")
    ResponseEntity<User> deleteUser(@PathVariable("email") String email) {
        User deletedUser = userService.deleteUser(email);
        return ResponseEntity.ok(deletedUser);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @org.springframework.web.bind.annotation.ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return errors;
    }


    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @org.springframework.web.bind.annotation.ExceptionHandler(ConstraintViolationException.class)
    public Map<String, String> handleUniqueEmailValidation(ConstraintViolationException ex) {
        Map<String, String> errors = new HashMap<>();
        errors.put("email","Email is already existed");
        return errors;
    }


    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @org.springframework.web.bind.annotation.ExceptionHandler(MissingRequiredPropertiesException.class)
    public Map<String, String> handleUniqueEmailValidation(MissingRequiredPropertiesException ex) {
        Map<String, String> errors = new HashMap<>();
        errors.put("email","Email cannot be null");
        return errors;
    }


}
