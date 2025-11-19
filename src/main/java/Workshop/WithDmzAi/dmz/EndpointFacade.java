package Workshop.WithDmzAi.dmz;

import Workshop.WithDmzAi.entity.Account;
import Workshop.WithDmzAi.businessLogic.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.logging.Logger;

@Component
public class EndpointFacade implements IEndpointFacade {

    private static final Logger logger = Logger.getLogger(EndpointFacade.class.getName());

    @Autowired
    private AccountService accountService;

    public ResponseEntity<?> handleAccountCreation(Account account) {
        logger.info("Processing POST request for account creation through endpoint facade");

        try {
            validatePostRequest(account);
            Account savedAccount = delegateToService(account);
            return buildSuccessResponse(savedAccount);

        } catch (IllegalArgumentException e) {
            logger.warning("POST request validation failed: " + e.getMessage());
            return buildValidationErrorResponse(e.getMessage());

        } catch (Exception e) {
            logger.severe("POST request processing failed: " + e.getMessage());
            return buildSystemErrorResponse();
        }
    }

    private void validatePostRequest(Account account) {
        if (account == null) {
            throw new IllegalArgumentException("Request body cannot be null");
        }

        logger.info("POST request validation passed for user: " + account.getUsername());
    }

    private Account delegateToService(Account account) {
        logger.info("Delegating account creation to service layer");
        return accountService.createAccount(account);
    }

    private ResponseEntity<?> buildSuccessResponse(Account savedAccount) {
        logger.info("POST request completed successfully for account ID: " + savedAccount.getId());
        return new ResponseEntity<>(savedAccount, HttpStatus.CREATED);
    }

    private ResponseEntity<?> buildValidationErrorResponse(String message) {
        return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
    }

    private ResponseEntity<?> buildSystemErrorResponse() {
        return new ResponseEntity<>("Internal server error", HttpStatus.INTERNAL_SERVER_ERROR);
    }
}