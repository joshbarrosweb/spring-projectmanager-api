package com.joshbarrosweb.projectmanager.repositories;

import com.joshbarrosweb.projectmanager.entities.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

    /**
     * Find users by name containing a given keyword.
     *
     * @param name     The keyword to search for in user names.
     * @param pageable The pagination information.
     * @return A page of users matching the search criteria.
     */
    Page<User> findByNameContaining(String name, Pageable pageable);

    /**
     * Find users by email containing a given keyword.
     *
     * @param email    The keyword to search for in user email addresses.
     * @param pageable The pagination information.
     * @return A page of users matching the search criteria.
     */
    Page<User> findByEmail(String email, Pageable pageable);
}
