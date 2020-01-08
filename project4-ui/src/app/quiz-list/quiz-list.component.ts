import { Component, OnInit } from '@angular/core';
import { QuizTakerService, UserQuiz } from '../quiz-taker.service';
import { Observable } from 'rxjs';
import { UserInfo, AuthService } from '../auth.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-quiz-list',
  templateUrl: './quiz-list.component.html',
  styleUrls: ['./quiz-list.component.css']
})
export class QuizListComponent implements OnInit {

  currentUserId: number;
  currentUser$: Observable<UserInfo> = this.authService.getCurrentUser();

  quizList: UserQuiz[];

  constructor(
    private quizTaker: QuizTakerService, 
    private authService: AuthService,
    private router: Router
    ) { }

  ngOnInit() {
    this.currentUser$.subscribe(user => {
      this.quizTaker.getQuizzesByUser(user.id).subscribe(respQuizzes => this.quizList = respQuizzes);
    }
      );
  }

  goToQuiz(quizId: number){
    this.quizTaker.currentQuiz = quizId;
    console.log(quizId);
    this.router.navigateByUrl("/take-quiz");
  }

}
