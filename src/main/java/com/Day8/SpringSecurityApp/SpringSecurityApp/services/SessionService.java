package com.Day8.SpringSecurityApp.SpringSecurityApp.services;

import com.Day8.SpringSecurityApp.SpringSecurityApp.Repositories.SessionRepository;
import com.Day8.SpringSecurityApp.SpringSecurityApp.entites.Session;
import com.Day8.SpringSecurityApp.SpringSecurityApp.entites.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.web.authentication.session.SessionAuthenticationException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;

@RequiredArgsConstructor
@Service
public class SessionService {

    private final SessionRepository sessionRepository;

    private final  int Session_Limit = 2;

    public void generateNewSession(User user , String refreshToken){

        List<Session> UserSessions = sessionRepository.findByUser(user);

        if(UserSessions.size()==Session_Limit){


            UserSessions.sort(Comparator.comparing(Session::getLastUsedAt));

            Session leastRecentlyUsedSession = UserSessions.get(0);

            sessionRepository.delete(leastRecentlyUsedSession);
        }

        Session newSession = Session
                .builder().user(user).refreshToken(refreshToken).build();

        sessionRepository.save(newSession);

    }

    public void validateSession(String refreshToken){

        Session session = sessionRepository.findByRefreshToken(refreshToken) .orElseThrow(()
                ->new SessionAuthenticationException("Session Not found"+refreshToken));
        session.setLastUsedAt(LocalDateTime.now());
        sessionRepository.save(session);
    }


}
