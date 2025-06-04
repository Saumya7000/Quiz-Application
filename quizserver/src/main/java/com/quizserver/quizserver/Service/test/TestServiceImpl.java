package com.quizserver.quizserver.Service.test;

import org.springframework.stereotype.Service;

import com.quizserver.quizserver.dto.QuestionDTO;
import com.quizserver.quizserver.dto.QuestionResponse;
import com.quizserver.quizserver.dto.SubmitTestDTO;
import com.quizserver.quizserver.dto.TestDTO;
import com.quizserver.quizserver.dto.TestDetailsDTO;
import com.quizserver.quizserver.dto.TestResultDTO;
import com.quizserver.quizserver.entities.Questions;
import com.quizserver.quizserver.entities.Test;
import com.quizserver.quizserver.entities.TestResult;
import com.quizserver.quizserver.entities.User;
import com.quizserver.quizserver.repository.QuestionRepository;
import com.quizserver.quizserver.repository.TestRepository;
import com.quizserver.quizserver.repository.TestResultRepository;
import com.quizserver.quizserver.repository.UserRepository;

import jakarta.persistence.EntityNotFoundException;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;

@Service
public class TestServiceImpl implements TestService{

	@Autowired
	private TestRepository testRepository;
	
	@Autowired
	private QuestionRepository questionRepository;
	
	@Autowired
	private TestResultRepository testResultRepository;
	
	@Autowired 
	private UserRepository userRepository;
	
	public TestDTO createTest(TestDTO dto)
	{
		Test test = new Test();
		test.setTitle(dto.getTitle());
		test.setDescription(dto.getDescription());
		test.setTime(dto.getTime());
		
		return testRepository.save(test).getDto();
	}
	
	public QuestionDTO addQuestionInTest(QuestionDTO dto) {
		Optional<Test> optionalTest = testRepository.findById(dto.getId());
		if(optionalTest.isPresent()) {
			
			Questions question = new Questions();
			
			question.setTest(optionalTest.get());
			question.setQuestionText(dto.getQuestionText());
			question.setOptionA(dto.getOptionA());
			question.setOptionB(dto.getOptionB());
			question.setOptionC(dto.getOptionC());
			question.setOptionD(dto.getOptionD());
			question.setCorrectOption(dto.getCorrectOption());
			
			return questionRepository.save(question).getDto();
		}
		throw new EntityNotFoundException("Test  Not Found");
	}
	
	public List<TestDTO> getAllTests(){

		return testRepository.findAll().stream().peek(
				test -> test.setTime(test.getQuestions().size() * test.getTime())).collect(Collectors.toList()).
				stream().map(Test::getDto).collect(Collectors.toList());
	}
	
	
	public TestDetailsDTO getAllQuestionsByTest(Long Id) {
		Optional<Test> optionalTest = testRepository.findById(Id);
		TestDetailsDTO testDetailsDTO = new TestDetailsDTO();
		if(optionalTest.isPresent()) {
			TestDTO testDTO = optionalTest.get().getDto();
			testDTO.setTime(optionalTest.get().getTime() * optionalTest.get().getQuestions().size());
			
			testDetailsDTO.setTestDTO(testDTO);
			testDetailsDTO.setQuestions(optionalTest.get().getQuestions().stream().map(Questions::getDto).toList());
			return testDetailsDTO;
		}
		return testDetailsDTO;
	}
	
	public TestResultDTO submitTest(SubmitTestDTO request)
	{
		Test test = testRepository.findById(request.getTestId()).orElseThrow(() -> new EntityNotFoundException("Test not found"));
		
		User user = userRepository.findById(request.getUserId()).orElseThrow(() -> new EntityNotFoundException("User not found"));
		
		int correctAnswers = 0;
		
		for(QuestionResponse response: request.getResponse()) {
			Questions question = questionRepository.findById(response.getQuestionId())
					.orElseThrow(() -> new EntityNotFoundException("Question not found"));
			
			if(question.getCorrectOption().equals(response.getSelectedOption())) {
				correctAnswers++;
			}
		}
		
		int totalQuestions = test.getQuestions().size();
		double percentage = ((double) correctAnswers/totalQuestions)*100;
		
		TestResult testResult = new TestResult();
		testResult.setTest(test);
		testResult.setUser(user);
		testResult.setTotalQuestions(totalQuestions);
		testResult.setCorrectAnswers(correctAnswers);
		testResult.setPercentage(percentage);
		
		return testResultRepository.save(testResult).getDto();		
	}
	
	public List<TestResultDTO> getAllTestResults(){
		return testResultRepository.findAll().stream().map(TestResult::getDto).collect(Collectors.toList());
	}
	
	public List<TestResultDTO> getAllTestResultsOfUser(Long userId){
		return testResultRepository.findAllByUserId(userId).stream().map(TestResult::getDto).collect(Collectors.toList());
	}
	
	public Test updateTest(Long id, Test updatedTest) {
        return testRepository.findById(id).map(test -> {
            test.setTitle(updatedTest.getTitle());
            test.setTime(updatedTest.getTime());
            return testRepository.save(test);
        }).orElseThrow(() -> new RuntimeException("Test not found with id " + id));
    }

    public void deleteTest(Long id) {
        if (!testRepository.existsById(id)) {
            throw new RuntimeException("Test not found with id " + id);
        }
        testRepository.deleteById(id);
    }
}
