import { Component } from '@angular/core';
import { NzNotificationService } from 'ng-zorro-antd/notification';
import { AdminService } from '../../services/admin.service';
import { SharedModule } from '../../../shared/shared.module';
import { Router } from '@angular/router';

@Component({
  selector: 'app-dashboard',
  imports: [SharedModule],
  templateUrl: './dashboard.component.html',
  styleUrl: './dashboard.component.css'
})
export class DashboardComponent {

  tests = [];

  constructor(private notification: NzNotificationService,
    private testService:AdminService,
    private router: Router
  ){}

  editTest(id: number) {
  this.router.navigate(['/admin/update-test', id]); // ✅ Route and ID passed
}

  ngOnInit(){
    this.getAllTests();
  }

  getAllTests(){
    this.testService.getAllTest().subscribe(res=>{
      this.tests = res;
    }, error=>{
      this.notification.error('ERROR', 'Somthing Went Wronf. Try Again', { nzDuration: 5000 })
    })
  }

  getFormattedTime(time): string{
    const minutes = Math.floor(time/60);
    const seconds = time % 60;
    return `${minutes} minutes ${seconds} seconds`; 
  }
}
