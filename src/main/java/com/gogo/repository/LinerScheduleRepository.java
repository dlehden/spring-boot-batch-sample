package com.gogo.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Repository;

import com.gogo.model.LinerSchedule;

@Repository("LinerScheduleRepository")
public interface LinerScheduleRepository extends JpaRepository<LinerSchedule, Long>{
	  @Nullable
	 LinerSchedule findByLinercodeAndVesselname(String linercode,String vesselname);
}
