package org.amy.poemdj.repository;

import java.util.List;

import org.amy.poemdj.domain.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthorRepository extends JpaRepository<Author, Long> {
	
	public List<Author> findByName(String name);

}
