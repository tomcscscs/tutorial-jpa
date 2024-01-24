package org.edupoll.app.repository;

import java.util.List;

import org.edupoll.app.entity.Feed;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FeedRepository extends JpaRepository<Feed, Integer> {
//	CrudRepository<Feed, Integer>, PagingAndSortingRepository<Feed, Integer> {

	public long countByTitleContains(String keyword);
	public List<Feed> findByTitleContains(String keyword, Pageable pageable);
	// 제목에 어떤 특정 단어를 포함하고 있는걸 find하는 기능을 만들려 함
	// public List<Feed> findByTitleContainsOrBodyContains(String data);
// 	public List<Feed> findByTitleContains(String keyword);

	// public List<Feed> findByBodyContains(String word);

	// public void deleteByContent(String writer);

}
