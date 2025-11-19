package Workshop.WithDmz.dmz;

import Workshop.WithDmzAi.entity.Account;
import Workshop.WithDmzAi.repository.IRepository;

public class DatabaseFacade implements IDatabaseFacade{

    private IRepository accountRepository;

    public boolean saveAccount(Account account) {
        try {
            accountRepository.save(account);
            return true;

        } catch (Exception e) {
            return false;
        }
    }
}
