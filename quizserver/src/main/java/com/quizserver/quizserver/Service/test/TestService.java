package com.quizserver.quizserver.Service.test;

import java.util.List;

import com.quizserver.quizserver.dto.QuestionDTO;
import com.quizserver.quizserver.dto.SubmitTestDTO;
import com.quizserver.quizserver.dto.TestDTO;
import com.quizserver.quizserver.dto.TestDetailsDTO;
import com.quizserver.quizserver.dto.TestResultDTO;
import com.quizserver.quizserver.entities.Test;

public interface TestService {

	TestDTO createTest(TestDTO dto);
	
	QuestionDTO addQuestionInTest(QuestionDTO dto); 
	
	List<TestDTO> getAllTests();
	
	TestDetailsDTO getAllQuestionsByTest(Long Id);
	
	TestResultDTO submitTest(SubmitTestDTO request);
	
	List<TestResultDTO> getAllTestResults();
	
	List<TestResultDTO> getAllTestResultsOfUser(Long userId);
	
	Test updateTest(Long id, Test updatedTest);
	
	void deleteTest(Long id);
}
