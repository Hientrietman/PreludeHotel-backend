package com.prelude.preludehotelbackend.service.impl;

import com.prelude.preludehotelbackend.dto.LoginRequest;
import com.prelude.preludehotelbackend.dto.Response;
import com.prelude.preludehotelbackend.dto.UserResponse;
import com.prelude.preludehotelbackend.exception.GenericException;
import com.prelude.preludehotelbackend.model.User;
import com.prelude.preludehotelbackend.repository.UserRepository;
import com.prelude.preludehotelbackend.service.JwtService;
import com.prelude.preludehotelbackend.service.interfaces.IUserService;
import com.prelude.preludehotelbackend.utils.Utils;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService implements IUserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    @Override
    public Response register(User user) {
        Response response = new Response();
        try {
            if(user.getRole()==null||user.getRole()==""){
                user.setRole("USER");
            }
            if(userRepository.existsByEmail(user.getEmail())){
                throw new GenericException("User already exists");
            }
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            User savedUser = userRepository.save(user);
            UserResponse userResponse = Utils.mapUserEntityToUserDTO(savedUser);
            response.setStatusCode(201);
            response.setUser(userResponse);

        }catch(GenericException e) {
            response.setMessage(e.getMessage());
            response.setStatusCode(400);
        }
        catch(Exception e){
            response.setMessage(e.getMessage());
            response.setStatusCode(500);
        }
        return response;
    }

    @Override
    public Response login(LoginRequest loginRequest) {
        Response response = new Response();
        try {
          authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));
          var user = userRepository.findByEmail(loginRequest.getEmail()).orElseThrow(()-> new GenericException("User not found"));
          var token = jwtService.generateToken(user);
          response.setStatusCode(200);
          response.setToken(token);
        }catch(GenericException e) {
            response.setMessage(e.getMessage());
            response.setStatusCode(400);
        }
        catch(Exception e){
            response.setMessage(e.getMessage());
            response.setStatusCode(500);
        }
        return response;
    }



    @Override
    public Response getAllUsers() {

        Response response = new Response();
        try {
            List<User> userList = userRepository.findAll();
            List<UserResponse> userDTOList = Utils.mapUserListEntityToUserListDTO(userList);
            response.setStatusCode(200);
            response.setMessage("successful");
            response.setUserList(userDTOList);

        } catch (Exception e) {
            response.setStatusCode(500);
            response.setMessage("Error getting all users " + e.getMessage());
        }
        return response;
    }

    @Override
    public Response getUserBookingHistory(String userId) {

        Response response = new Response();


        try {
            User user = userRepository.findById(Long.valueOf(userId)).orElseThrow(() -> new GenericException("User Not Found"));
            UserResponse userDTO = Utils.mapUserEntityToUserDTOPlusUserBookingsAndRoom(user);
            response.setStatusCode(200);
            response.setMessage("successful");
            response.setUser(userDTO);

        } catch (GenericException e) {
            response.setStatusCode(404);
            response.setMessage(e.getMessage());

        } catch (Exception e) {

            response.setStatusCode(500);
            response.setMessage("Error getting all users " + e.getMessage());
        }
        return response;
    }

    @Override
    public Response deleteUser(String userId) {

        Response response = new Response();

        try {
            userRepository.findById(Long.valueOf(userId)).orElseThrow(() -> new GenericException("User Not Found"));
            userRepository.deleteById(Long.valueOf(userId));
            response.setStatusCode(200);
            response.setMessage("successful");

        } catch (GenericException e) {
            response.setStatusCode(404);
            response.setMessage(e.getMessage());

        } catch (Exception e) {

            response.setStatusCode(500);
            response.setMessage("Error getting all users " + e.getMessage());
        }
        return response;
    }

    @Override
    public Response getUserById(String userId) {

        Response response = new Response();

        try {
            User user = userRepository.findById(Long.valueOf(userId)).orElseThrow(() -> new GenericException("User Not Found"));
            UserResponse userDTO = Utils.mapUserEntityToUserDTO(user);
            response.setStatusCode(200);
            response.setMessage("successful");
            response.setUser(userDTO);

        } catch (GenericException e) {
            response.setStatusCode(404);
            response.setMessage(e.getMessage());

        } catch (Exception e) {

            response.setStatusCode(500);
            response.setMessage("Error getting all users " + e.getMessage());
        }
        return response;
    }

    @Override
    public Response getMyInfo(String email) {

        Response response = new Response();

        try {
            User user = userRepository.findByEmail(email).orElseThrow(() -> new GenericException("User Not Found"));
            UserResponse userDTO = Utils.mapUserEntityToUserDTO(user);
            response.setStatusCode(200);
            response.setMessage("successful");
            response.setUser(userDTO);

        } catch (GenericException e) {
            response.setStatusCode(404);
            response.setMessage(e.getMessage());

        } catch (Exception e) {

            response.setStatusCode(500);
            response.setMessage("Error getting all users " + e.getMessage());
        }
        return response;
    }
}
