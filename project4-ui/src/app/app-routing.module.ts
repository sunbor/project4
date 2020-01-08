import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { LoginComponent } from './login/login.component';
import { ManagerHomeComponent } from './manager-home/manager-home.component';
import { NewQuestionComponent } from './new-question/new-question.component';
import { ViewQuestionsComponent } from './view-questions/view-questions.component';
import { AssignQuizComponent } from './assign-quiz/assign-quiz.component';
import { QuizListComponent } from './quiz-list/quiz-list.component';
import { TakeQuizComponent } from './take-quiz/take-quiz.component';

const routes: Routes = [
  { 
    path: 'login', component: LoginComponent
  },
  {
    path: 'manager-home', component: ManagerHomeComponent
  },
  {
    path: 'new-question', component: NewQuestionComponent
  },
  {
    path: 'view-questions', component: ViewQuestionsComponent
  },
  {
    path: 'assign-quizzes', component: AssignQuizComponent
  },
  {
    path: 'user-quizzes', component: QuizListComponent
  },
  {
    path: 'take-quiz', component: TakeQuizComponent
  },
  {
    path: '', redirectTo: '/login', pathMatch: 'full'
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }