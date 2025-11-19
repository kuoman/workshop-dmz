package Workshop.WithDmz.controller;


import Workshop.WithDmz.dmz.EndpointFacade;
import Workshop.WithDmz.dmz.IEndpointFacade;
import Workshop.WithDmz.entity.Account;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class Controller {
    IEndpointFacade endpointFacade;

    public Controller() {
        this(new EndpointFacade());
    }

    public Controller(IEndpointFacade endpointFacade) {
        this.endpointFacade = endpointFacade;
    }

    @PostMapping("/accounts")
    public ResponseEntity<?> createAccount(@RequestBody Account account) {
        return endpointFacade.handleAccountCreation(account);
    }
}