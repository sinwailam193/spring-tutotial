package com.springsecurity.app.auth;

import java.util.List;
import java.util.Optional;

import com.google.common.collect.Lists;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;

import static com.springsecurity.app.security.ApplicationUserRole.*;

@Repository("fakeApplicationUserDaoService")
public class FakeApplicationUserDaoService implements ApplicationUseDao {
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public FakeApplicationUserDaoService(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Optional<ApplicationUser> selecApplicationUserByUsername(String username) {
        return getApplicationUsers()
            .stream()
            .filter(applicationUser -> username.equals(applicationUser.getUsername()))
            .findFirst();
    }

    private List<ApplicationUser> getApplicationUsers() {
        List<ApplicationUser> applicationUsers = Lists.newArrayList(
            new ApplicationUser(
                STUDENT.getGrantedAuthorities(),
                passwordEncoder.encode("password"),
                "aaronlam",
                true,
                true,
                true,
                true
            ),
            new ApplicationUser(
                ADMIN.getGrantedAuthorities(),
                passwordEncoder.encode("password"),
                "linda",
                true,
                true,
                true,
                true
            ),
            new ApplicationUser(
                ADMINTRAINEE.getGrantedAuthorities(),
                passwordEncoder.encode("password"),
                "tom",
                true,
                true,
                true,
                true
            )
        );
        return applicationUsers;
    }
}