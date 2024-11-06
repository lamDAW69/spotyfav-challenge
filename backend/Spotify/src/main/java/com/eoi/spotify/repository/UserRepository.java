package com.eoi.spotify.repository;

import com.eoi.spotify.entity.User;
import com.eoi.spotify.projection.UserProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    @Query("SELECT u.id as id, u.name as name, u.mail as mail FROM User u")
    List<UserProjection> findAllUsersProjected();
}
