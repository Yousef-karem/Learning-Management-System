package net.java.lms_backend.Repositrory;

import net.java.lms_backend.entity.Role;
import net.java.lms_backend.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

@Repository
@Transactional(readOnly = true)
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
    Optional<User> findById(long id);
    Optional<User> findByUsername(String username);

    @Query("SELECT User a FROM User u WHERE u.role=?1")
    List<User> findByRole(Role role);
    @Transactional
    @Modifying
    @Query("UPDATE User a " +
            "SET a.enabled = TRUE WHERE a.email = ?1")
    int enableUser(String email);
    @Transactional
    @Modifying
    @Query("DELETE FROM User u WHERE u.id = ?1")
    void deleteById(Long id);
    @Transactional
    @Modifying
    @Query("Delete From ConfirmationToken c where c.user.id=?1")
    void deleteConfirmationToken(long id);

}
