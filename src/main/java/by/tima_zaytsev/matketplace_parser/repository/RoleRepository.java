package by.tima_zaytsev.matketplace_parser.repository;

import by.tima_zaytsev.matketplace_parser.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findByName(String name);
}
