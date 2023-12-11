package com.eliasfs06.tinktime.repository;

import com.eliasfs06.tinktime.model.User;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends GenericRepository<User> {
    User findByUsername(String username);
}
