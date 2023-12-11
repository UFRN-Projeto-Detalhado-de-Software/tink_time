package com.eliasfs06.tinktime.repository;

import com.eliasfs06.tinktime.model.User;

public interface UserRepository extends GenericRepository<User> {
    User findByUsername(String username);
}
