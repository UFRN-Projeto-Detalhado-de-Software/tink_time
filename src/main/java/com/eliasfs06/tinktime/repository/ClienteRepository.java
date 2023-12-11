package com.eliasfs06.tinktime.repository;

import com.eliasfs06.tinktime.model.Client;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ClienteRepository extends GenericRepository<Client> {
}
