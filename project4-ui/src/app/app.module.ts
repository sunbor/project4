import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { HttpClientModule } from '@angular/common/http';

import { MatRadioModule } from '@angular/material/radio';
import { Platform } from '@angular/cdk/platform';
import { FocusOptions, FocusableOption, FocusOrigin } from '@angular/cdk/a11y';
import { FocusMonitor } from '@angular/cdk/a11y';
import { UniqueSelectionDispatcher } from '@angular/cdk/collections';
import { MatCheckboxModule } from '@angular/material/checkbox'; 

import { AppComponent } from './app.component';
import { LoginComponent } from './login/login.component';
import { MessagesComponent } from './messages/messages.component';
import { AppRoutingModule } from './app-routing.module';
import { ManagerHomeComponent } from './manager-home/manager-home.component';
import { NewQuestionComponent } from './new-question/new-question.component';
import { ViewQuestionsComponent } from './view-questions/view-questions.component';
import { AssignQuizComponent } from './assign-quiz/assign-quiz.component';
import { NavbarComponent } from './navbar/navbar.component';
import { QuizListComponent } from './quiz-list/quiz-list.component';
import { TakeQuizComponent } from './take-quiz/take-quiz.component';

@NgModule({
  declarations: [
    AppComponent,
    LoginComponent,
    MessagesComponent,
    ManagerHomeComponent,
    NewQuestionComponent,
    ViewQuestionsComponent,
    AssignQuizComponent,
    NavbarComponent,
    QuizListComponent,
    TakeQuizComponent
  ],
  imports: [
    BrowserModule,
    FormsModule,
    HttpClientModule,
    AppRoutingModule,
    MatRadioModule,
    MatCheckboxModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
