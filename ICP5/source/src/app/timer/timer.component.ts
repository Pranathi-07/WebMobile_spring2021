import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-timer',
  templateUrl: './timer.component.html',
  styleUrls: ['./timer.component.css']
})
export class TimerComponent implements OnInit {
  public eventsDateRecord: String;
  public showError: boolean = false;
  public eventCountDown: any = ['-','-','-','-'];
  public currentDate = new Date();

  constructor() { }

  ngOnInit(): void {
  }

  seteventCountDown () {
    const [year, month, day ] = this.eventsDateRecord.split('-');
    const dateSelected = new Date(+year, +month - 1, +day);
    if(dateSelected < new Date()) {
      this.showError = true;
      this.eventCountDown = ['-','-','-','-'];
    } else {
      this.showError = false;
      const difference = (dateSelected.getTime() - new Date().getTime())/1000;
      const days = Math.floor(difference / (60 * 60 * 24));
      const hours = Math.floor((difference % (60 * 60 * 24)) / (60 * 60));
      const minutes = Math.floor((difference % (60 * 60)) / (60));
      const seconds = Math.floor((difference % (60)));
      this.eventCountDown = [days,hours,minutes,seconds]
    }
  }

  initializeCounter() {
    setInterval(() => {
      this.seteventCountDown()
    }, 1000)
  }

}
