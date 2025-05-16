package by.tima_zaytsev.MarketplaceParser.infrastracture;

import by.tima_zaytsev.MarketplaceParser.infrastracture.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findByName(String name);
}
