package com.pazarfy.ws.feed;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;

import com.pazarfy.ws.user.Users;

public interface IFeedRepository extends JpaRepository<Feed, Long>{

	Page<Feed> findByUser(Users user, Pageable page);

	Page<Feed> findByIdGreaterThan(long id, Pageable page);

	Page<Feed> findByIdLessThan(long id, Pageable page);

	Page<Feed> findByIdLessThanAndUser(long id, Users user, Pageable pageBefore);

	Page<Feed> findByIdGreaterThanAndUser(long id, Users user, Pageable pageAfter);

	int countByIdGreaterThanAndUser(long id, Users user);

	int countByIdGreaterThan(long id);
	

}
