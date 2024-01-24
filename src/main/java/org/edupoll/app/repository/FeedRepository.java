package org.edupoll.app.repository;

import org.edupoll.app.entity.Feed;
import org.springframework.data.jpa.repository.JpaRepository;



public interface FeedRepository extends JpaRepository<Feed, Integer>  {
//	CrudRepository<Feed, Integer>, PagingAndSortingRepository<Feed, Integer> {
	
}
