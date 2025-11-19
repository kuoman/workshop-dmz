package Workshop.WithDmz.repository;


import Workshop.WithDmz.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IRepository extends JpaRepository<Account, Long> {
}