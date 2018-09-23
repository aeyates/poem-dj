package org.amy.poemdj.repository;

import java.util.List;

import org.amy.poemdj.domain.Line;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LineRepository extends JpaRepository<Line, Long> {

	List<Line> findByLineContaining(String searchTerm);
}
