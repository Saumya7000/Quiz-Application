package com.quizserver.quizserver.entities;

import java.util.List;

import com.quizserver.quizserver.dto.TestDTO;


import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import lombok.Data;

@Entity
@Data
public class Test {
    
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String title;
	
	private String description;
	
	private Long time;
	
	@OneToMany(mappedBy = "test", cascade = CascadeType.ALL)
	private List<Questions> questions;
	
	public TestDTO getDto()
	{
		TestDTO testDTO = new TestDTO();
		testDTO.setId(id);;
		testDTO.setTitle(title);
		testDTO.setDescription(description);
		testDTO.setTime(time);
		
		return testDTO;
	}
	
}
