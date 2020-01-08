import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class NewQuestionService {

  constructor(
    private http: HttpClient
  ) { }

  httpOptions = {
    headers: new HttpHeaders({ 'Content-Type': 'application/json' })
  };

  private newQuestionUrl = "http://localhost:8080/Project4/api/new-question";
  private newAnswerUrl = "http://localhost:8080/Project4/api/new-answer";
  private getQuestionsUrl = "http://localhost:8080/Project4/api/get-question";
  private addQuestionsUrl = "http://localhost:8080/Project4/api/new-quiz";

  sendQuestion(question: Question): Observable<number> {
    console.log("data received below");
    console.log(question);
    return this.http.post<number>(this.newQuestionUrl, question, this.httpOptions);
  }

  sendAnswers(answers: Answer[]): Observable<boolean> {
    return this.http.post<boolean>(this.newAnswerUrl, answers, this.httpOptions);
  }

  getQuestions(): Observable<QuestionDisplay[]> {
    return this.http.get<QuestionDisplay[]>(this.getQuestionsUrl);
  }

  sendQuiz(quizName: string, questionIds: number[]): Observable<boolean>{
    return this.http.post<boolean>(this.addQuestionsUrl, {title: quizName, questionIds: questionIds}, this.httpOptions);
  }
}

export interface Question {
  id: number;
  question: string;
}

export interface Answer {
  id: number;
  answer: string;
  correct: number;
  question: number;
}

export interface QuestionDisplay {
  id: number;
  question: string;
  answerList: Answer[];
  inQuiz: boolean;
}