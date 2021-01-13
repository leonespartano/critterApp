package com.udacity.jdnd.course3.critter.repository;

import com.udacity.jdnd.course3.critter.schedule.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ScheduleRepository extends JpaRepository<Schedule, Long> {
    @Query("select s from Schedule s join s.employees e where e.id = :id")
    List<Schedule> findAllSchedulesByEmployee(@Param("id") Long id);

    @Query("select s from Schedule s join s.pets p where p.id = :id order by p.id desc")
    List<Schedule> findAllSchedulesByPet(@Param("id") Long id);

}
