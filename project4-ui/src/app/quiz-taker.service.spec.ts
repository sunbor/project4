import { TestBed } from '@angular/core/testing';

import { QuizTakerService } from './quiz-taker.service';

describe('QuizTakerService', () => {
  beforeEach(() => TestBed.configureTestingModule({}));

  it('should be created', () => {
    const service: QuizTakerService = TestBed.get(QuizTakerService);
    expect(service).toBeTruthy();
  });
});
