import {Component, OnInit} from '@angular/core';
import {BillService} from "../../bill.service";


@Component({
  selector: 'app-start-page',
  templateUrl: './start-page.component.html',
  styleUrls: ['./start-page.component.scss']
})
export class StartPageComponent implements OnInit {

  private bill: any;

  constructor(private billService: BillService) {
  }

  ngOnInit() {
  }

  public getBill(): void {
    this.billService.getBill(0).subscribe((res) => {
      this.bill = res;
    });
  }

}
