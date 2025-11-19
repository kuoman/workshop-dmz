package Workshop.WithDmz.dmz;

import Workshop.WithDmz.entity.Account;

public interface IDatabaseFacade {
    boolean saveAccount(Account account);
}
