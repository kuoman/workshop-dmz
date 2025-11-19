package Workshop.WithDmzAi.businessLogic;

import Workshop.WithDmzAi.entity.Account;
import Workshop.WithDmzAi.dmz.IDatabaseFacade;
import Workshop.WithDmzAi.dmz.DatabaseFacade.DatabaseOperationResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.regex.Pattern;

@Service
public class AccountService {

    @Autowired
    private IDatabaseFacade accountDatabaseFacade;

    private static final Pattern EMAIL_PATTERN = Pattern.compile(
        "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$"
    );

    public Account createAccount(Account account) {
        validateAccount(account);

        // insert all business logic here...

        // hand-off to DMZ
        DatabaseOperationResult<Account> result = accountDatabaseFacade.saveAccount(account);

        // come back from DMZ here

        if (result.isSuccess()) {
            return result.getData();
        } else {
            throw new RuntimeException("Failed to save account: " + result.getMessage());
        }
    }

    private void validateAccount(Account account) {
        if (account.getUsername() == null || account.getUsername().trim().isEmpty()) {
            throw new IllegalArgumentException("Username is required");
        }

        if (account.getEmail() == null || account.getEmail().trim().isEmpty()) {
            throw new IllegalArgumentException("Email is required");
        }

        if (!EMAIL_PATTERN.matcher(account.getEmail()).matches()) {
            throw new IllegalArgumentException("Email should be valid");
        }

        if (account.getBirthDate() == null) {
            throw new IllegalArgumentException("Birth date is required");
        }

        if (account.getBirthDate().isAfter(LocalDate.now())) {
            throw new IllegalArgumentException("Birth date must be in the past");
        }

        if (account.getAddress() == null || account.getAddress().trim().isEmpty()) {
            throw new IllegalArgumentException("Address is required");
        }
    }
}