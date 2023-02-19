package telran.spring.data.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import telran.spring.data.proj.*;
import telran.spring.data.service.CollegeService;

@RestController
@RequestMapping("college")
public class CollegeController {

	@Autowired
	CollegeService collegeService;
	
	@GetMapping("marks")
	List<MarkProj> getMarksByNameAndSubject(@RequestParam(name = "subject") String subject,
			@RequestParam(name = "name") String name) {
		return collegeService.getMarksByNameAndSubject(name, subject);
	}
	
	@GetMapping("marks/subjects")
	List<StudentSubjectMark> getMarksByName(@RequestParam(name = "name") String name) {
		return collegeService.getMarksByName(name);
	}
	
	@GetMapping("marks/avg")
	List<StudentAvgMark> studentsAvgMark() {
		return collegeService.getStudentsAvgMark();
	}
	
	@GetMapping("marks/best")
	List<StudentName> best3Students(@RequestParam(name = "quantity", defaultValue = "0") int quantity,
			@RequestParam(name = "subject", defaultValue = "all") String subject) {
		if(quantity == 0 && subject.equals("all")) {
			return collegeService.getBestStudent();
		}
		if(quantity != 0 && subject.equals("all")) {
			return collegeService.getTopBestStudents(quantity);
		} else {
			return collegeService.getTopBestStudentsSubject(quantity, subject);
		}		
	}
	
	@GetMapping("marks/worst")
	List<StudentSubjectMark> worstStudents(@RequestParam(name = "quantity") int quantity) {
		return collegeService.getMarksOfWorstStudents(quantity);
	}
	
	@GetMapping("marks/distribution")
	List<IntervalMarksCount> marksDistribution(@RequestParam(name = "interval", defaultValue = "1") int interval) {
		return collegeService.marksDistribution(interval);
	}
}
