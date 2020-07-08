package com.vds.account.repository;

import com.vds.account.domain.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {

	List<Account> findByName(String name);

	Account findByEmail(String email);

	List<Account> findByCreatedDate(Date createDate);

	List<Account> findByModifiedDate(Date modifiedDate);

	List<Account> findByAge(int age);

	List<Account> findByGender(int gender);

	List<Account> findByWeight(int weight);

	List<Account> findByHeight(int height);

}
