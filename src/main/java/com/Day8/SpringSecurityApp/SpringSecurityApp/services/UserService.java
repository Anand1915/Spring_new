package com.Day8.SpringSecurityApp.SpringSecurityApp.services;

import com.Day8.SpringSecurityApp.SpringSecurityApp.Repositories.UserRepository;
import com.Day8.SpringSecurityApp.SpringSecurityApp.dtos.LoginDto;
import com.Day8.SpringSecurityApp.SpringSecurityApp.dtos.SignUpDto;
import com.Day8.SpringSecurityApp.SpringSecurityApp.dtos.UserDto;
import com.Day8.SpringSecurityApp.SpringSecurityApp.entites.User;
import com.Day8.SpringSecurityApp.SpringSecurityApp.exceptions.ResourseNotFound;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;

    private  final ModelMapper modelMapper;

    private  final PasswordEncoder passwordEncoder;



    private JwtService jwtService;

    @Override
    public UserDetails loadUserByUsername(String username)
            throws UsernameNotFoundException {

        return userRepository.findByEmail(username)
                .orElseThrow(() ->
                        new BadCredentialsException(
                                "User with email " + username + " not found"
                        )
                );
    }


    public  User  getUserById(Long userId){

        return userRepository.findById(userId).
                orElseThrow(()->new ResourseNotFound("user not find for this user id"+userId));
    }



    public UserDto signUp(SignUpDto signUpDto) {
      Optional<User> user = userRepository.findByEmail(signUpDto.getEmail());

      if(user.isPresent()){

          throw new BadCredentialsException("user Exists"+ signUpDto.getEmail());
      }

      User toCreateUser = modelMapper.map(signUpDto,User.class);
        toCreateUser.setPassword(passwordEncoder.encode(toCreateUser.getPassword()));

      User saveUser = userRepository.save(toCreateUser);

      return  modelMapper.map(saveUser,UserDto.class);
    }


}
