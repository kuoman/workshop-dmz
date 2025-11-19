package Workshop.WithDmzAi.dmz;

import Workshop.WithDmzAi.entity.Account;
import org.springframework.http.ResponseEntity;

public interface IEndpointFacade {
    ResponseEntity<?> handleAccountCreation(Account account);
}