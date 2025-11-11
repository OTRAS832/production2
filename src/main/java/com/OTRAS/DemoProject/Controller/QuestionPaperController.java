package com.OTRAS.DemoProject.Controller;
 
import java.util.List;
 
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.CrossOrigin;

import org.springframework.web.bind.annotation.DeleteMapping;

import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.PostMapping;

import org.springframework.web.bind.annotation.RequestBody;

import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RequestParam;

import org.springframework.web.bind.annotation.RestController;
 
import com.OTRAS.DemoProject.Service.QuestionPaperService;

import com.OTRAS.DemoProject.DTO.QuestionDTO;

import com.OTRAS.DemoProject.DTO.QuestionPaperDTO;

import com.OTRAS.DemoProject.DTO.QuestionSetDTO;

import com.OTRAS.DemoProject.Entity.QuestionPaper;
 
@RestController

@RequestMapping("/api/question-paper")
public class QuestionPaperController {
 
    @Autowired

//    private QuestionPaperService questionPaperService;

    private QuestionPaperService questionPaperService;
 

    @PostMapping("/create")

    public QuestionPaperDTO createQuestionPaper(

            @RequestParam Long jobPostId,

            @RequestBody QuestionPaper questionPaper) {

        return questionPaperService.createQuestionPaper(jobPostId, questionPaper);

    }
 

    @GetMapping("/getAll")

    public List<QuestionPaperDTO> getAllPapers(@RequestParam Long jobPostId) {

        return questionPaperService.getPapersByJobPost(jobPostId);

    }
 

    @GetMapping("/getSets")

    public List<QuestionSetDTO> getSets(@RequestParam Long paperId) {

        return questionPaperService.getSetsByPaper(paperId);

    }
 

    @GetMapping("/getQuestions")

    public List<QuestionDTO> getQuestions(@RequestParam Long setId) {

        return questionPaperService.getQuestionsBySet(setId);

    }
 

    @DeleteMapping("/delete")

    public String deletePaper(@RequestParam Long paperId) {

        return questionPaperService.deletePaper(paperId);

    }

}

 