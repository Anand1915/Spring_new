package com.Day8.SpringSecurityApp.SpringSecurityApp.entites;

import com.Day8.SpringSecurityApp.SpringSecurityApp.entites.enums.Role;
import com.Day8.SpringSecurityApp.SpringSecurityApp.utils.PermissionMapping;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
@Setter
@RequiredArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@Builder
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String email;

    private String password;

    private String name;

    @ElementCollection(fetch = FetchType.EAGER)
    @Enumerated(EnumType.STRING)
    private Set<Role> role;


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

        // Add ROLE_USER, ROLE_ADMIN, etc.
        Set<SimpleGrantedAuthority> authorities = new HashSet<>();

          role.forEach(
                  userRole ->{
                      Set<SimpleGrantedAuthority> permissions = PermissionMapping.getAuthoritiesForRole(userRole);
                      authorities.addAll(permissions);
                      authorities.add(new SimpleGrantedAuthority("ROLE_"+userRole.name()));
                  }

          );

        return authorities;
    }


    @Override
    public String getUsername() {
        return this.email;
    }


    @Override
    public String getPassword() {
        return this.password;
    }
}