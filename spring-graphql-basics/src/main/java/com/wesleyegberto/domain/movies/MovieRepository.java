package com.wesleyegberto.domain.movies;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface MovieRepository extends JpaRepository<Movie, Integer> {
	@Query("SELECT m FROM Movie m ORDER BY m.id DESC LIMIT 5")
	List<Movie> top5Recents();
	List<Movie> findByTitleContainingIgnoreCase(String title, Pageable paging);
	List<Movie> findByCategoryContainingIgnoreCase(String category, Pageable paging);
}
