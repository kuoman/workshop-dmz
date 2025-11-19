package Workshop.WithDmzAi.dmz;

import Workshop.WithDmzAi.entity.Account;
import Workshop.WithDmzAi.dmz.DatabaseFacade.DatabaseOperationResult;

import java.util.List;

public interface IDatabaseFacade {
    DatabaseOperationResult<Account> saveAccount(Account account);
    DatabaseOperationResult<Account> findAccountById(Long id);
    DatabaseOperationResult<List<Account>> findAllAccounts();
}