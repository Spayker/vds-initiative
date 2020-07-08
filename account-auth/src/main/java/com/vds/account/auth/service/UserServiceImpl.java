package com.vds.account.auth.service;

import com.vds.account.auth.domain.User;
import com.vds.account.auth.exception.UserException;
import com.vds.account.auth.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

	private final Logger log = LoggerFactory.getLogger(getClass());

	private static final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

	@Autowired
	private UserRepository repository;

	@Override
	public User create(User user) {

		Optional<User> existing = repository.findById(user.getId());
		existing.ifPresent(it-> {throw new IllegalArgumentException("user already exists: " + it.getUsername());});

		String hash = encoder.encode(user.getPassword());
		user.setPassword(hash);

		User savedUser = repository.saveAndFlush(user);
		log.info("new user has been created: {}", savedUser.getId());
		return savedUser;
	}

	@Override
	public User saveChanges(User update) {
		Optional<User> username = repository.findById(update.getId());
		if (username.isPresent()) {
			update.setLastLogin(new Date());
			log.debug("user {} changes have been saved", update.getUsername());
			return repository.saveAndFlush(update);
		} else {
			throw new UserException("can't find user with id " + update.getId());
		}
	}
}
