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
	
}
