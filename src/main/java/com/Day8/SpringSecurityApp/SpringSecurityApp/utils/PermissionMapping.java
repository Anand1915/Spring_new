package com.Day8.SpringSecurityApp.SpringSecurityApp.utils;

import com.Day8.SpringSecurityApp.SpringSecurityApp.entites.enums.Permission;
import com.Day8.SpringSecurityApp.SpringSecurityApp.entites.enums.Role;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import static com.Day8.SpringSecurityApp.SpringSecurityApp.entites.enums.Permission.*;
import static com.Day8.SpringSecurityApp.SpringSecurityApp.entites.enums.Role.*;

public class PermissionMapping {

    private static final Map<Role, Set<Permission>> map = Map.of(

            USER, Set.of(
                    User_View,
                    Post_View
            ),

            CREATOR, Set.of(
                    Post_Create,
                    User_Update,
                    Post_Update
            ),

            ADMIN, Set.of(
                    Post_Create,
                    User_Update,
                    Post_Update,
                    User_Delete,
                    User_Create,
                    Post_Delete
            )
    );

    public static Set<SimpleGrantedAuthority> getAuthoritiesForRole(Role role) {

        return map.get(role).stream()
                .map(permission -> new SimpleGrantedAuthority(permission.name()))
                .collect(Collectors.toSet());
    }
}