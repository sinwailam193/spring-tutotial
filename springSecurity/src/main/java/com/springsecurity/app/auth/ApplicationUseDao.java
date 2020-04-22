package com.springsecurity.app.auth;

import java.util.Optional;

public interface ApplicationUseDao {
    public Optional<ApplicationUser> selecApplicationUserByUsername(String username);
}