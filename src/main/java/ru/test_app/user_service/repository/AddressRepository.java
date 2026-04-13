package ru.test_app.user_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.test_app.user_service.domain.Address;

import java.util.List;

@Repository
public interface AddressRepository extends JpaRepository<Address, Long> {

    @Query("""
            SELECT a FROM Address a
            WHERE LOWER(a.region) LIKE LOWER(CONCAT(:search, '%'))
               OR LOWER(a.city) LIKE LOWER(CONCAT(:search, '%'))
               OR LOWER(a.street) LIKE LOWER(CONCAT(:search, '%'))
            """)
    List<Address> search(@Param("search") String search);
}