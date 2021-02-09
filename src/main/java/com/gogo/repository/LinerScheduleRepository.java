package com.gogo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.gogo.model.LinerSchedule;

@Repository("LinerScheduleRepository")
public interface LinerScheduleRepository extends JpaRepository<LinerSchedule, Long>{
	LinerSchedule findByLinercodeAndVesselname(String linercode,String vesselname);
}
