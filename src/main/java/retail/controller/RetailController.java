package retail.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import retail.dao.UserDAO;
import retail.data.User;

@RestController
public class RetailController {

	@Autowired private UserDAO userDAO;
	@Autowired private Environment env;
	
	
	@GetMapping("/users")
	public List getUsers() {
		return userDAO.list();
	}

	@GetMapping("/user/{userId}")
	public ResponseEntity getUser(@PathVariable String userId) {

		System.out.println("userId ********"+ userId);
		User user = userDAO.get(userId);
		if (user == null) {
			return new ResponseEntity("No User found for ID " + userId, HttpStatus.NOT_FOUND);
		}

		return new ResponseEntity(user, HttpStatus.OK);
	}

	@PostMapping(value = "/createuser")
	public ResponseEntity createUser(@RequestBody User user) {

		userDAO.create(user);

		return new ResponseEntity(user, HttpStatus.OK);
	}

	@DeleteMapping("/deleteuser/{userId}")
	public ResponseEntity deleteCustomer(@PathVariable String userId) {

		if (null == userDAO.delete(userId)) {
			return new ResponseEntity("No User found for ID " + userId, HttpStatus.NOT_FOUND);
		}

		return new ResponseEntity(userId, HttpStatus.OK);

	}

	@PutMapping("/updateuser/{userId}")
	public ResponseEntity updateCustomer(@PathVariable String userId, @RequestBody User user) {

		String status = userDAO.update(user);

		if (null == user) {
			return new ResponseEntity("No user found for " + user, HttpStatus.NOT_FOUND);
		}

		return new ResponseEntity(status, HttpStatus.OK);
	}
}