package Workshop.WithDmz.dmz;

import Workshop.WithDmz.businessLogic.AccountService;
import Workshop.WithDmz.entity.Account;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.util.regex.Pattern;

public class EndpointFacade implements IEndpointFacade {
    private AccountService accountService;
    private static final Pattern EMAIL_PATTERN = Pattern.compile("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$");

    public EndpointFacade() {
        this(new AccountService());
    }

    public EndpointFacade(AccountService accountService) {
        this.accountService = accountService;
    }

    public ResponseEntity<?> handleAccountCreation(Account account) {
        try {
            // insert all business logic here...
            validateAccount(account);

            return accountService.createAccount(account);

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
