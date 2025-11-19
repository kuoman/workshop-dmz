package Workshop.WithDmz.dmz;

import Workshop.WithDmz.entity.Account;
import Workshop.WithDmz.repository.IRepository;

public class DatabaseFacade implements IDatabaseFacade{

    private IRepository accountRepository;

    public DatabaseFacade(IRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    public boolean saveAccount(Account account) {
        try {
            accountRepository.save(account);
            return true;

        } catch (Exception e) {
            return false;
        }
    }

}
