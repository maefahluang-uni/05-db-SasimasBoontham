package th.mfu.boot;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserController {

    
    @Autowired
    public UserRepository repo;

    
    @PostMapping("/users")
    public ResponseEntity<String> registerUser(@RequestBody User user) {
        // Check if user with same username exists
        if (repo.findByUsername(user.getUsername()) != null) {
            return new ResponseEntity<>("Username already exists", HttpStatus.CONFLICT);
        }

        // Save the new user
        repo.save(user);

        // Return 201 Created
        return new ResponseEntity<>("User registered", HttpStatus.CREATED);
    }

    
    @GetMapping("/users")
    public ResponseEntity<List<User>> list() {
        List<User> users = repo.findAll();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    
    @DeleteMapping("/users/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable Long id) {
        // Check if user exists
        if (!repo.existsById(id)) {
            return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);
        }

        // Delete user
        repo.deleteById(id);
        return new ResponseEntity<>("User deleted", HttpStatus.OK);
    }
}
