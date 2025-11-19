package Workshop.WithDmzAi.controller;


import Workshop.WithDmzAi.entity.Account;
import Workshop.WithDmzAi.dmz.IEndpointFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class Controller {

    @Autowired
    private IEndpointFacade accountEndpointFacade;

    @PostMapping("/accounts")
    public ResponseEntity<?> createAccount(@RequestBody Account account) {

        // entrypoint area...here is where we hand-off to our DMZ no code gets written here.

        return accountEndpointFacade.handleAccountCreation(account);
    }
}