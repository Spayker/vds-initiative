package com.vds.account.auth.service;

import com.vds.account.auth.domain.User;

public interface UserService {

	User create(User user);

	User saveChanges(User update);

}
