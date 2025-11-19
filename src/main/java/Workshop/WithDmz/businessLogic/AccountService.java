package Workshop.WithDmz.businessLogic;


import Workshop.WithDmz.dmz.DatabaseFacade;
import Workshop.WithDmz.dmz.IDatabaseFacade;
import Workshop.WithDmz.entity.Account;
import Workshop.WithDmz.repository.AccountRepository;
import Workshop.WithDmz.repository.IRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.regex.Pattern;

@Service
public class AccountService {
    private IDatabaseFacade accountDatabaseFacade;

    private static final Pattern EMAIL_PATTERN = Pattern.compile(
        "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$"
    );

    public AccountService(){
        this(new DatabaseFacade(new AccountRepository()));
    }

    public AccountService(IDatabaseFacade databaseFacade) {
        this.accountDatabaseFacade = accountDatabaseFacade;
    }


    public ResponseEntity<?> createAccount(Account account) {
        // business logic here

        validateAccount(account);

        // hand-off to DMZ
        accountDatabaseFacade.saveAccount(account);

        return new ResponseEntity<>(account, HttpStatus.CREATED);
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