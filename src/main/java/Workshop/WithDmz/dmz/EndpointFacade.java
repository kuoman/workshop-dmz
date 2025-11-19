package Workshop.WithDmz.dmz;

import Workshop.WithDmzAi.businessLogic.AccountService;
import Workshop.WithDmzAi.dmz.DatabaseFacade;
import Workshop.WithDmzAi.dmz.IDatabaseFacade;
import Workshop.WithDmzAi.entity.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.util.regex.Pattern;

public class EndpointFacade {
    private AccountService accountService;
    private static final Pattern EMAIL_PATTERN = Pattern.compile("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$");
    private IDatabaseFacade accountDatabaseFacade;

    public ResponseEntity<?> handleAccountCreation(Account account) {
        try {
            validateAccount(account);

            Account savedAccount = accountService.createAccount(account);
            // insert all business logic here...

            // hand-off to DMZ
            DatabaseFacade.DatabaseOperationResult<Account> result = accountDatabaseFacade.saveAccount(account);

            return buildSuccessResponse(savedAccount);

        } catch (IllegalArgumentException e) {
            return buildValidationErrorResponse(e.getMessage());

        } catch (Exception e) {
            return buildSystemErrorResponse();
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

    private ResponseEntity<?> buildSuccessResponse(Account savedAccount) {
        return new ResponseEntity<>(savedAccount, HttpStatus.CREATED);
    }

    private ResponseEntity<?> buildValidationErrorResponse(String message) {
        return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
    }

    private ResponseEntity<?> buildSystemErrorResponse() {
        return new ResponseEntity<>("Internal server error", HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
