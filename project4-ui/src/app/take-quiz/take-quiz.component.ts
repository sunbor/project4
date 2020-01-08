import { Component, OnInit } from '@angular/core';
import { QuizTakerService } from '../quiz-taker.service';
import { QuestionDisplay, Answer } from '../new-question.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-take-quiz',
  templateUrl: './take-quiz.component.html',
  styleUrls: ['./take-quiz.component.css']
})
export class TakeQuizComponent implements OnInit {

  questions: QuestionDisplay[];
  answers: number[] = [];

  constructor(
    private quizTaker: QuizTakerService,
    private router: Router
  ) { }

  currentQuiz: number = this.quizTaker.currentQuiz;

  ngOnInit() {
    this.quizTaker.getQuestionsByQuiz().subscribe(respQuestions => this.questions = respQuestions);
  }

  sendAnswers(){
    console.log(this.answers);
    let questionIds: number[] = [];
    for(let question of this.questions){
      questionIds.push(question.id);
    }
    console.log(questionIds);

    this.quizTaker.postQuizAnswers(this.answers, questionIds, questionIds.length).subscribe(respSuccess => {
      this.quizTaker.currentQuiz = undefined;
      this.router.navigateByUrl("/user-quizzes");
    });

  }


}