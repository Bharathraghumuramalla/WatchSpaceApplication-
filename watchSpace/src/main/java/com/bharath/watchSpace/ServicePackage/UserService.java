package com.bharath.watchSpace.ServicePackage;

import com.bharath.watchSpace.DTO.UserLoginDTO;
import com.bharath.watchSpace.DTO.UserRegisteringDTO;
import com.bharath.watchSpace.Entities.Users;
import com.bharath.watchSpace.ExceptionsAndErrors.ErrorResponse;
import com.bharath.watchSpace.ExceptionsAndErrors.ResourceNotFoundException;
import com.bharath.watchSpace.RepositoryPackage.UsersRepository;
import com.bharath.watchSpace.SecurityPackage.JWTUtil;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class UserService
{

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    JWTUtil jwtUtil;

    @Autowired
    UsersRepository userRepository;

    @Autowired
    AuthenticationManager authenticationManager;

    public void registerUser(@Valid UserRegisteringDTO registeringDTO)
    {
        Users user = new Users();

        user.setUsername(registeringDTO.getName());
        user.setPassword(passwordEncoder.encode(registeringDTO.getPassword()));
        user.setEmail(registeringDTO.getEmail());
        user.setRole("PLAYER");

        userRepository.save(user);
    }

    public ResponseEntity<?> login(@Valid UserLoginDTO userLoginDTO)
    {
        try
        {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userLoginDTO.getName(), userLoginDTO.getPassword()));
            return ResponseEntity.ok(jwtUtil.generateToken(userLoginDTO.getName()));
        }
        catch (BadCredentialsException e)
        {
            ErrorResponse errorResponse = new ErrorResponse();

            errorResponse.setMessage("Invalid username or password");
            errorResponse.setTimestamp(LocalDateTime.now());
            errorResponse.setPath("/login");
            errorResponse.setStatus(HttpStatus.UNAUTHORIZED.value());

            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorResponse);
        }
    }

    public void registerHost(UserRegisteringDTO dto)
    {
        Users user = new Users();

        user.setUsername(dto.getName());
        user.setPassword(passwordEncoder.encode(dto.getPassword()));
        user.setEmail(dto.getEmail());
        user.setRole("HOST");

        userRepository.save(user);
    }

    public ResponseEntity<?> hostLogin(@Valid UserLoginDTO dto)
    {
        try
        {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(dto.getName(), dto.getPassword()));

            Users user = userRepository.findByusername(dto.getName())
                    .orElseThrow(() -> new ResourceNotFoundException("User not found"));

            if (!user.getRole().equals("HOST"))
            {
                throw new AccessDeniedException("Only HOST users can use host login");
            }

            return ResponseEntity.ok(jwtUtil.generateToken(dto.getName()));
        }
        catch (BadCredentialsException e)
        {
            ErrorResponse errorResponse = new ErrorResponse();

            errorResponse.setMessage("Invalid username or password");
            errorResponse.setTimestamp(LocalDateTime.now());
            errorResponse.setPath("/hostLogin");
            errorResponse.setStatus(HttpStatus.UNAUTHORIZED.value());

            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorResponse);
        }
    }
}
