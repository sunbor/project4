import { Component, OnInit } from '@angular/core';
import { QuizTakerService, UserInfo, Quiz } from '../quiz-taker.service';

@Component({
  selector: 'app-assign-quiz',
  templateUrl: './assign-quiz.component.html',
  styleUrls: ['./assign-quiz.component.css']
})
export class AssignQuizComponent implements OnInit {

  quizTakers: UserInfo[];
  quizzes: Quiz[];
  assignSuccess: boolean;

  constructor(private quizTaker: QuizTakerService) { }

  ngOnInit() {
    this.quizTaker.getUsers().subscribe(respUsers => this.quizTakers = respUsers);
    this.quizTaker.getQuizzes().subscribe(respQuizzes => this.quizzes = respQuizzes);
  }

  assignQuizzes(){
    let userIds: number[] = [];
    for(let user of this.quizTakers){
      if(user.selected){
        userIds.push(user.id);
      }
    }
    let quizIds: number[] = [];
    for(let quiz of this.quizzes){
      if(quiz.selected){
        quizIds.push(quiz.id);
      }
    }
    
    this.quizTaker.postAssignments(userIds, quizIds).subscribe(respSuccess => this.assignSuccess = respSuccess);
  }

}
