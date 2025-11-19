package Workshop.Original.controller;


import Workshop.Original.Service.AccountService;
import Workshop.Original.entity.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class AccountController {

    @Autowired
    private AccountService accountService;

    @PostMapping("/accounts")
    public ResponseEntity<?> createAccount(@RequestBody Account account) {
        try {
            Account savedAccount = accountService.createAccount(account);
            return new ResponseEntity<>(savedAccount, HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>("Internal server error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}