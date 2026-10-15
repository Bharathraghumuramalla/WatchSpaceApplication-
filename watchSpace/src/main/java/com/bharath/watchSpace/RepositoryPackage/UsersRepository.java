package com.bharath.watchSpace.RepositoryPackage;

import com.bharath.watchSpace.Entities.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsersRepository extends JpaRepository<Users, Integer>
{
    Optional<Users> findByusername(String username);
}
