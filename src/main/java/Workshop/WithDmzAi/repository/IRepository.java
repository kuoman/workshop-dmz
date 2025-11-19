package Workshop.WithDmzAi.repository;

import Workshop.WithDmzAi.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IRepository extends JpaRepository<Account, Long> {
}