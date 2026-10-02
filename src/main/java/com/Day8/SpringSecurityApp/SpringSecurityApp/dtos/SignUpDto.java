package com.Day8.SpringSecurityApp.SpringSecurityApp.dtos;

import com.Day8.SpringSecurityApp.SpringSecurityApp.entites.enums.Permission;
import com.Day8.SpringSecurityApp.SpringSecurityApp.entites.enums.Role;
import lombok.Data;

import java.util.Set;

@Data
public class SignUpDto {

    private  String email;

    private  String password;

    private  String name;

    private Set<Role> role;

    private  Set<Permission> permissions;
}
