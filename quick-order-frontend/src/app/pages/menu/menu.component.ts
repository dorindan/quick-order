import { Component, OnInit } from '@angular/core';
import {MenuService} from "../../services/menu.service";
import {Menu} from "../../models/Menu";
import {Observable} from "rxjs";

@Component({
  selector: 'app-menu',
  templateUrl: './menu.component.html',
  styleUrls: ['./menu.component.scss']
})
export class MenuComponent implements OnInit {

  menu: Menu[];
  title: string = "yay";
  constructor(private menuService:MenuService) { }

  ngOnInit() {
    this.menuService.getMenu().subscribe(response =>{
      console.log(response);
      this.menu = response;
    })

  }

}
