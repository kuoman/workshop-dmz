package Workshop.Original.Service;

import Workshop.Original.entity.Account;
import Workshop.Original.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.regex.Pattern;

@Service
public class AccountService {

    @Autowired
    private AccountRepository accountRepository;

    private static final Pattern EMAIL_PATTERN = Pattern.compile(
        "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$"
    );

    public Account createAccount(Account account) {
        validateAccount(account);
        return accountRepository.save(account);
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