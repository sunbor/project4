import { Component, OnInit } from '@angular/core';

import { QuestionDisplay, Answer, NewQuestionService } from '../new-question.service';


@Component({
  selector: 'app-view-questions',
  templateUrl: './view-questions.component.html',
  styleUrls: ['./view-questions.component.css']
})
export class ViewQuestionsComponent implements OnInit {

  questions: QuestionDisplay[];
  quizName: string;
  quizSuccess: boolean;

  constructor(private newQuestionService: NewQuestionService) { }

  ngOnInit() {
    this.newQuestionService.getQuestions().subscribe(respQuestions => this.questions = respQuestions);
  }

  sendQuiz(){

    let questionIds: number[] = [];
    for(let question of this.questions){
      if(question.inQuiz){
        questionIds.push(question.id);
      }
    }
    console.log(questionIds);

    this.newQuestionService.sendQuiz(this.quizName, questionIds)
      .subscribe(respSuccess => this.quizSuccess = respSuccess);
    
  }

}

