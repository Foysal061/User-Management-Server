package com.acterio.assessment;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
    UserEntity findByEmail(String email);

    @Query(value = "SELECT SUBSTRING(email, LOCATE('@', email) + 1, 255) AS domain, COUNT(*) AS count FROM UserEntity GROUP BY SUBSTRING(email, LOCATE('@', email) + 1, 255)")
    List<DomainCountDTO> countDistinctEmailDomain();
}