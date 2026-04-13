package ru.test_app.user_service.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.test_app.user_service.domain.User;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    @EntityGraph(attributePaths = "address")
    @Query("SELECT u FROM User u")
    List<User> findAllWithAddress();

    @EntityGraph(attributePaths = "address")
    @Query("""
            SELECT u FROM User u
            WHERE LOWER(u.firstName) LIKE LOWER(CONCAT(:search, '%'))
               OR LOWER(u.lastName) LIKE LOWER(CONCAT(:search, '%'))
               OR LOWER(u.middleName) LIKE LOWER(CONCAT(:search, '%'))
            """)
    List<User> search(@Param("search") String search);
}