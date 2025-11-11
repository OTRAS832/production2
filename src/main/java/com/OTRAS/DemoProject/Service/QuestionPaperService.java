package com.OTRAS.DemoProject.Service;
 
import java.util.List;

import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;
 
import com.OTRAS.DemoProject.Entity.*;

import com.OTRAS.DemoProject.DTO.*;

import com.OTRAS.DemoProject.Repository.*;
 
@Service

public class QuestionPaperService {
 
    @Autowired

    private QuestionPaperRepository questionPaperRepository;
 
    @Autowired

    private QuestionSetRepository questionSetRepository;
 
    @Autowired

    private QuestionRepository questionRepository;
 
    @Autowired

    private JobPostRepository jobPostRepository;
 

    public QuestionPaperDTO createQuestionPaper(Long jobPostId, QuestionPaper questionPaper) {

        JobPost jobPost = jobPostRepository.findById(jobPostId)

                .orElseThrow(() -> new RuntimeException("JobPost not found with ID: " + jobPostId));
 
        questionPaper.setJobPost(jobPost);
 
        if (questionPaper.getSets() != null) {

            questionPaper.getSets().forEach(set -> {

                set.setQuestionPaper(questionPaper);

                if (set.getQuestions() != null) {

                    set.getQuestions().forEach(q -> q.setQuestionSet(set));

                }

            });

        }
 
        QuestionPaper saved = questionPaperRepository.save(questionPaper);

        return convertToDTO(saved);

    }
 

    public List<QuestionPaperDTO> getPapersByJobPost(Long jobPostId) {

        return questionPaperRepository.findByJobPostId(jobPostId)

                .stream()

                .map(this::convertToDTO)

                .collect(Collectors.toList());

    }
 

    public List<QuestionSetDTO> getSetsByPaper(Long paperId) {

        return questionSetRepository.findByQuestionPaperId(paperId)

                .stream()

                .map(this::convertSetToDTO)

                .collect(Collectors.toList());

    }
 

    public List<QuestionDTO> getQuestionsBySet(Long setId) {

        return questionRepository.findByQuestionSetId(setId)

                .stream()

                .map(this::convertQuestionToDTO)

                .collect(Collectors.toList());

    }
 

    public String deletePaper(Long paperId) {

        questionPaperRepository.deleteById(paperId);

        return "Question Paper deleted successfully";

    }
 

    private QuestionPaperDTO convertToDTO(QuestionPaper paper) {

        return QuestionPaperDTO.builder()

                .id(paper.getId())

                .paperName(paper.getPaperName())

                .jobPostId(paper.getJobPost() != null ? paper.getJobPost().getId() : null)

                .jobPostTitle(paper.getJobPost() != null ? paper.getJobPost().getJobTitle() : null)

                .sets(paper.getSets() != null

                        ? paper.getSets().stream().map(this::convertSetToDTO).collect(Collectors.toList())

                        : null)

                .build();

    }
 
    private QuestionSetDTO convertSetToDTO(QuestionSet set) {

        return QuestionSetDTO.builder()

                .id(set.getId())

                .setName(set.getSetName())

                .questions(set.getQuestions() != null

                        ? set.getQuestions().stream().map(this::convertQuestionToDTO).collect(Collectors.toList())

                        : null)

                .build();

    }
 
    private QuestionDTO convertQuestionToDTO(Question q) {

        return QuestionDTO.builder()

                .id(q.getId())

                .questionText(q.getQuestionText())

                .optionA(q.getOptionA())

                .optionB(q.getOptionB())

                .optionC(q.getOptionC())

                .optionD(q.getOptionD())

                .correctAnswer(q.getCorrectAnswer())

                .build();

    }

}

 