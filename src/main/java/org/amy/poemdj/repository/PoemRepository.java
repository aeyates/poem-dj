package org.amy.poemdj.repository;

import java.util.List;

import org.amy.poemdj.domain.Poem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PoemRepository extends JpaRepository<Poem, Long> {
	
	// Search for text in title, author, and lines
	@Query(nativeQuery=true, value="select distinct p.* from poem p join author a on a.id = p.author join line l on l.poem = p.id where p.title like %:exact% or l.line like %:exact% or a.name like %:exact%")
	public List<Poem> findPoemsContaining(@Param("exact") String exact);

}
