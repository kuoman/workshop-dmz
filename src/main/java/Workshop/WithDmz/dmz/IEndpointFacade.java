package Workshop.WithDmz.dmz;

import Workshop.WithDmz.entity.Account;
import org.springframework.http.ResponseEntity;

public interface IEndpointFacade {
    ResponseEntity<?> handleAccountCreation(Account account);
}
