import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { QuestionDisplay, Question, Answer } from './new-question.service';

@Injectable({
  providedIn: 'root'
})
export class QuizTakerService {

  constructor(private http: HttpClient) { }

  httpOptions = {
    headers: new HttpHeaders({ 'Content-Type': 'application/json' })
  };

  currentQuiz: number;

  private getQuizzesUrl = "http://localhost:8080/Project4/api/get-quizzes";
  private getUsersUrl = "http://localhost:8080/Project4/api/quiz-takers";
  private postAssignmentsUrl = "http://localhost:8080/Project4/api/post-assignments";
  private getQuizzesByUserUrl = "http://localhost:8080/Project4/api/get-quiz-by-user?id=";
  private getQuestionsByQuizUrl = "http://localhost:8080/Project4/api/get-question-by-quiz?id=";
  private postQuizAnswersUrl = "http://localhost:8080/Project4/api/post-quiz-answers";

  getQuizzes(): Observable<Quiz[]> {
    return this.http.get<Quiz[]>(this.getQuizzesUrl);
  }

  getUsers(): Observable<UserInfo[]> {
    return this.http.get<UserInfo[]>(this.getUsersUrl);
  }

  postAssignments(userIds: number[], quizIds: number[]): Observable<boolean> {
    return this.http.post<boolean>(this.postAssignmentsUrl, {userIds: userIds, quizIds: quizIds}, this.httpOptions);
  }

  getQuizzesByUser(userId: number): Observable<UserQuiz[]> {
    console.log(userId);
    return this.http.get<UserQuiz[]>(this.getQuizzesByUserUrl + userId);
  }

  getQuestionsByQuiz(): Observable<QuestionDisplay[]> {
    console.log("currentQuiz in service: " + this.currentQuiz);
    console.log(this.getQuizzesByUserUrl + this.currentQuiz);
    return this.http.get<QuestionDisplay[]>(this.getQuestionsByQuizUrl + this.currentQuiz);
  }

  postQuizAnswers(answers: number[], questions: number[], size: number): Observable<boolean>{
    return this.http.post<boolean>(this.postQuizAnswersUrl, 
      {answers: answers, questions: questions, size: size, quizId: this.currentQuiz}, this.httpOptions);
  }
}

export interface Quiz {
  id: number;
  title: string;
  selected: boolean;
};

export interface UserInfo {
  id: number;
  username: string;
  role: number;
  group: number;
  selected: boolean;
};

export interface UserQuiz {
  id: number;
  title: string;
  score: number;
}
