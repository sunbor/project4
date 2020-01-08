import { Component, OnInit } from '@angular/core';

import { NewQuestionService } from '../new-question.service';
import { Observable } from 'rxjs';
import { Answer } from '../new-question.service';

@Component({
  selector: 'app-new-question',
  templateUrl: './new-question.component.html',
  styleUrls: ['./new-question.component.css']
})
export class NewQuestionComponent implements OnInit {

  question: string;
  answer1: string;
  answer2: string;
  answer3: string;
  answer4: string;
  correctAnswer: number;
  answers: number[] = [1, 2, 3, 4];

  //currentQId$?: Observable<number>;
  currentQId: number;

  constructor(private newQuestionService: NewQuestionService) { }

  ngOnInit() {
  }

  sendQuestion(){
    //send question'
    console.log("sending question");
    //this.currentQId$ = this.newQuestionService.sendQuestion({id: 1, question: "are you aware that"});
    this.newQuestionService.sendQuestion({id: 1, question: this.question})
    .subscribe(currentQId => 
      {
        this.currentQId = currentQId;
        this.sendAnswers(currentQId);
      }
    );
  }

  sendAnswers(respQId: number){
    //send answers
    console.log("sending answers");
    let answerArray: Answer[] = [
      {
        id: 0,
        answer: this.answer1,
        correct: this.correctAnswer === 1 ? 1 : 0,
        question: respQId
      },
      {
        id: 0,
        answer: this.answer2,
        correct: this.correctAnswer === 2 ? 1 : 0,
        question: respQId
      },
      {
        id: 0,
        answer: this.answer3,
        correct: this.correctAnswer === 3 ? 1 : 0,
        question: respQId
      },
      {
        id: 0,
        answer: this.answer4,
        correct: this.correctAnswer === 4 ? 1 : 0,
        question: respQId
      }
    ];
    let answerSuccess: boolean = false;
    this.newQuestionService.sendAnswers(answerArray).subscribe(answerResponse => 
      {
        answerSuccess = answerResponse;
        console.log("response from answer servlet: " + answerSuccess);
      });
  }

}
