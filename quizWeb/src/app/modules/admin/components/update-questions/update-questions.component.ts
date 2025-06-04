import { Component, OnInit } from '@angular/core';
import { SharedModule } from '../../../shared/shared.module';
import { AdminService } from '../../services/admin.service';
import { NzNotificationService } from 'ng-zorro-antd/notification';
import { TestService } from '../../../user/services/test.service';
import { ActivatedRoute, Router } from '@angular/router';
import { FormGroup } from '@angular/forms';

@Component({
  selector: 'app-update-questions',
  imports: [SharedModule],
  templateUrl: './update-questions.component.html',
  styleUrl: './update-questions.component.css'
})

export class UpdateQuestionsComponent implements OnInit {
  questionId: number;
  question: any = null;

  id: number | null;
  questionForm: FormGroup;
  questions: any[] = [];
  testId: any;

  constructor(
    private route: ActivatedRoute,
    private adminService: AdminService,
    private notification: NzNotificationService,
    private router: Router
  ) {}



  ngOnInit(){
    this.route.paramMap.subscribe(params =>{
      this.testId = +params.get('id');

      this.adminService.getTestQuestions(this.testId).subscribe(res=>{
        this.questions = res.questions;
        console.log(this.questions);
        this.questionId = +this.route.snapshot.paramMap.get('id');
    this.loadQuestion();
      })
    })
  }

  loadQuestion() {
    this.adminService.getQuestionById(this.questionId).subscribe(data => {
      this.question = data;
    });
  }

  updateQuestion() {
    this.adminService.updateQuestion(this.questionId, this.question).subscribe(res => {
      this.notification.success('Success', 'Question updated successfully!');
    }, err => {
      this.notification.error('Error', 'Failed to update question');
    });
  }

    submitForm(){
    const questionDto = this.questionForm.value;
    questionDto.id = this.id;

    this.adminService.addQuestionInTest(questionDto).subscribe(res=>{
      this.notification
      .success(
        'SUCCESS', 
        'Question Created Successfully.', 
        { nzDuration:5000 }
      );
      this.router.navigateByUrl("/admin/dashboard");
    }, error=>{
      this.notification
      .error(
        'ERROR', 
        '${error.error}', 
        { nzDuration: 5000 }
      );
    })
  }
}