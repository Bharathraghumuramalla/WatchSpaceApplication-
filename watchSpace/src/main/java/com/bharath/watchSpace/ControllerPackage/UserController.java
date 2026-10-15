package com.bharath.watchSpace.ControllerPackage;

import com.bharath.watchSpace.DTO.UserLoginDTO;
import com.bharath.watchSpace.DTO.UserRegisteringDTO;
import com.bharath.watchSpace.ServicePackage.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Tag(name = "Authentication", description = "User and Host authentication APIs")
public class UserController
{
    @Autowired
    UserService userService;


    @Operation(
            summary = "Register Player",
            description = "Creates a new PLAYER account")
    @PostMapping("/register")
    public String registerUser(@Valid @RequestBody UserRegisteringDTO registeringDTO)
    {
        userService.registerUser(registeringDTO);
        return "User Registered Successfully";
    }


    @Operation(
            summary = "Player Login",
            description = "Authenticates a PLAYER and returns a JWT token")
    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody UserLoginDTO userLoginDTO)
    {
        return userService.login(userLoginDTO);
    }


    @Operation(
            summary = "Register Host",
            description = "Creates a new HOST account")
    @PostMapping("host/Register")
    public void registerHost(@Valid @RequestBody UserRegisteringDTO registeringDTO)
    {
        userService.registerHost(registeringDTO);
    }

    @Operation(
            summary = "Host Login",
            description = "Authenticates a HOST and returns a JWT token")
    @PostMapping("host/Login")
    public ResponseEntity<?> hostLogin(@Valid @RequestBody UserLoginDTO userLoginDTO)
    {
        return userService.hostLogin(userLoginDTO);
    }
}
