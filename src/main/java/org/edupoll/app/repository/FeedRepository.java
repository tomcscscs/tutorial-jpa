package org.edupoll.app.repository;

import org.edupoll.app.entity.Feed;
import org.springframework.data.repository.CrudRepository;



public interface FeedRepository extends CrudRepository<Feed, Integer> {
	
}
