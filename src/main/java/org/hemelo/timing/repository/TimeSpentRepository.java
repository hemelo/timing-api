package org.hemelo.timing.repository;

import org.hemelo.timing.model.TimeSpent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TimeSpentRepository extends JpaRepository<TimeSpent, Long>, PagingAndSortingRepository<TimeSpent, Long> {
}
