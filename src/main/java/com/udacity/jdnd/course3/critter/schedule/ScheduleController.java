package com.udacity.jdnd.course3.critter.schedule;

import com.udacity.jdnd.course3.critter.utils.DTOConversionUtils;
import com.udacity.jdnd.course3.critter.service.ScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Handles web requests related to Schedules.
 */
@RestController
@RequestMapping("/schedule")
public class ScheduleController {
    @Autowired
    ScheduleService scheduleService;

    @Autowired
    DTOConversionUtils dtoConversionUtils;

    @PostMapping
    public ScheduleDTO createSchedule(@RequestBody ScheduleDTO scheduleDTO) {
        Schedule schedule = dtoConversionUtils.convertScheduleDTOtoEntity(scheduleDTO);
        Long id = scheduleService.save(schedule);
        ScheduleDTO newScheduleDTO = dtoConversionUtils.convertEntityToScheduleDTO(schedule);
        newScheduleDTO.setId(id);
        return newScheduleDTO;
    }

    @GetMapping
    public List<ScheduleDTO> getAllSchedules() {
        List<Schedule> schedules = scheduleService.getAllSchedules();
        return schedules.stream().map(
                schedule -> dtoConversionUtils.convertEntityToScheduleDTO(schedule)
        )
                .collect(Collectors.toList());
    }

    @GetMapping("/pet/{petId}")
    public List<ScheduleDTO> getScheduleForPet(@PathVariable long petId) {
        List<Schedule> schedules = scheduleService.getAllSchedulesByPet(petId);
        return schedules.stream().map(
                schedule -> dtoConversionUtils.convertEntityToScheduleDTO(schedule)
        ).collect(Collectors.toList());
    }

    @GetMapping("/employee/{employeeId}")
    public List<ScheduleDTO> getScheduleForEmployee(@PathVariable long employeeId) {
        List <Schedule> schedules = scheduleService.getAllSchedulesByEmployee(employeeId);
        return schedules.stream().map(
                schedule -> dtoConversionUtils.convertEntityToScheduleDTO(schedule)
        ).collect(Collectors.toList());
    }

    @GetMapping("/customer/{customerId}")
    public List<ScheduleDTO> getScheduleForCustomer(@PathVariable long customerId) {
        List<Schedule> schedules = order(scheduleService.getAllSchedulesByCustomer(customerId));

        return schedules.stream().map(
                schedule -> dtoConversionUtils.convertEntityToScheduleDTO(schedule)
        ).collect(Collectors.toList());
    }

    private static List<Schedule> order(List<Schedule> list){
        Schedule aux = new Schedule();
        for(int i=0; i < list.size(); i++){
            for(int j=0; j < list.size(); j++){
                if (list.get(i).getId() < list.get(j).getId()) {
                    aux = list.get(j);
                    list.set(j, list.get(i));
                    list.set(i, aux);
                }
            }
        }
        return list;
    }
}
