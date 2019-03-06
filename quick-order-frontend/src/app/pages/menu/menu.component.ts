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

  ingrediente(menuID: number, menuItemID: number): string{
    let ingredientele: string = "gsp";
    this.menu.forEach((menu) => {
      if (menu.id === menuID)
      {
        //console.log("I'm in menu" + menu.id);
        menu.menuItems.forEach((menuItem) => {
          if (menuItem.id === menuItemID)
          {
            //console.log("I'm in menuItem" + menuItem.id);
            menuItem.ingredients.forEach((ingredient) => {
              //console.log("I have ingredient" + ingredient.name);
              ingredientele.concat(ingredient.name);
              ingredientele.concat(',');
            })
          }
        })
      }
    })
    ingredientele = ingredientele.substring(0, ingredientele.length - 1)
    return ingredientele;
  }

}
