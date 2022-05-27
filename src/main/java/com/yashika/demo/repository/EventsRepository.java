package com.yashika.demo.repository;

import com.yashika.demo.entity.Events;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EventsRepository extends JpaRepository<Events, Integer> {
}
