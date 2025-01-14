package com.cthtc.office.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cthtc.office.entity.AccountEntity;
import com.cthtc.office.model.Role;

@Repository
public interface AccountRepository extends JpaRepository<AccountEntity, Long>{
	
	Optional<AccountEntity> findUserById(Long id);
	
	Optional<AccountEntity> findUserByUsername(String username);

	Optional<AccountEntity> findUserByRole(Role role);
}
