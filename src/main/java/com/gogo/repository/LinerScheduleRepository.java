package com.gogo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.gogo.model.LinerSchedule;

@Repository
public interface LinerScheduleRepository extends JpaRepository<LinerSchedule, Integer>{
}
