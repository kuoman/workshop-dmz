package Workshop.WithDmzAi.dmz;

import Workshop.WithDmzAi.entity.Account;
import Workshop.WithDmzAi.repository.IRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

@Component
public class DatabaseFacade implements IDatabaseFacade {

    private static final Logger logger = Logger.getLogger(DatabaseFacade.class.getName());

    @Autowired
    private IRepository accountRepository;

    public DatabaseOperationResult<Account> saveAccount(Account account) {
        try {
            logger.info("Attempting to save account for user: " + account.getUsername());

            validateAccountForSave(account);
            Account savedAccount = performSave(account);

            logger.info("Account saved successfully with ID: " + savedAccount.getId());
            return DatabaseOperationResult.success(savedAccount, "Account saved successfully");

        } catch (DataAccessException e) {
            logger.severe("Database error while saving account: " + e.getMessage());
            return DatabaseOperationResult.databaseError("Failed to save account due to database error");

        } catch (IllegalArgumentException e) {
            logger.warning("Invalid account data: " + e.getMessage());
            return DatabaseOperationResult.validationError(e.getMessage());

        } catch (Exception e) {
            logger.severe("Unexpected error while saving account: " + e.getMessage());
            return DatabaseOperationResult.systemError("Unexpected error during account save");
        }
    }

    public DatabaseOperationResult<Account> findAccountById(Long id) {
        try {
            logger.info("Attempting to find account with ID: " + id);

            Optional<Account> account = accountRepository.findById(id);

            if (account.isPresent()) {
                logger.info("Account found with ID: " + id);
                return DatabaseOperationResult.success(account.get(), "Account found");
            } else {
                logger.info("No account found with ID: " + id);
                return DatabaseOperationResult.notFound("Account not found");
            }

        } catch (DataAccessException e) {
            logger.severe("Database error while finding account: " + e.getMessage());
            return DatabaseOperationResult.databaseError("Failed to find account due to database error");

        } catch (Exception e) {
            logger.severe("Unexpected error while finding account: " + e.getMessage());
            return DatabaseOperationResult.systemError("Unexpected error during account lookup");
        }
    }

    public DatabaseOperationResult<List<Account>> findAllAccounts() {
        try {
            logger.info("Attempting to retrieve all accounts");

            List<Account> accounts = accountRepository.findAll();

            logger.info("Retrieved " + accounts.size() + " accounts");
            return DatabaseOperationResult.success(accounts, "All accounts retrieved successfully");

        } catch (DataAccessException e) {
            logger.severe("Database error while retrieving accounts: " + e.getMessage());
            return DatabaseOperationResult.databaseError("Failed to retrieve accounts due to database error");

        } catch (Exception e) {
            logger.severe("Unexpected error while retrieving accounts: " + e.getMessage());
            return DatabaseOperationResult.systemError("Unexpected error during account retrieval");
        }
    }

    private void validateAccountForSave(Account account) {
        if (account == null) {
            throw new IllegalArgumentException("Account cannot be null");
        }
        if (account.getUsername() == null || account.getUsername().trim().isEmpty()) {
            throw new IllegalArgumentException("Username is required for database save");
        }
    }

    private Account performSave(Account account) {
        return accountRepository.save(account);
    }

    public static class DatabaseOperationResult<T> {
        private final boolean success;
        private final T data;
        private final String message;
        private final ResultType type;

        private DatabaseOperationResult(boolean success, T data, String message, ResultType type) {
            this.success = success;
            this.data = data;
            this.message = message;
            this.type = type;
        }

        public static <T> DatabaseOperationResult<T> success(T data, String message) {
            return new DatabaseOperationResult<>(true, data, message, ResultType.SUCCESS);
        }

        public static <T> DatabaseOperationResult<T> notFound(String message) {
            return new DatabaseOperationResult<>(false, null, message, ResultType.NOT_FOUND);
        }

        public static <T> DatabaseOperationResult<T> validationError(String message) {
            return new DatabaseOperationResult<>(false, null, message, ResultType.VALIDATION_ERROR);
        }

        public static <T> DatabaseOperationResult<T> databaseError(String message) {
            return new DatabaseOperationResult<>(false, null, message, ResultType.DATABASE_ERROR);
        }

        public static <T> DatabaseOperationResult<T> systemError(String message) {
            return new DatabaseOperationResult<>(false, null, message, ResultType.SYSTEM_ERROR);
        }

        public boolean isSuccess() {
            return success;
        }

        public T getData() {
            return data;
        }

        public String getMessage() {
            return message;
        }

        public ResultType getType() {
            return type;
        }

        public enum ResultType {
            SUCCESS, NOT_FOUND, VALIDATION_ERROR, DATABASE_ERROR, SYSTEM_ERROR
        }
    }
}