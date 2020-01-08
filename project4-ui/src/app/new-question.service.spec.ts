import { TestBed } from '@angular/core/testing';

import { NewQuestionService } from './new-question.service';

describe('NewQuestionService', () => {
  beforeEach(() => TestBed.configureTestingModule({}));

  it('should be created', () => {
    const service: NewQuestionService = TestBed.get(NewQuestionService);
    expect(service).toBeTruthy();
  });
});
