import { ComponentFixture, TestBed } from '@angular/core/testing';

import { UpdateQuestionsComponent } from './update-questions.component';

describe('UpdateQuestionsComponent', () => {
  let component: UpdateQuestionsComponent;
  let fixture: ComponentFixture<UpdateQuestionsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [UpdateQuestionsComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(UpdateQuestionsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
