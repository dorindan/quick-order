import {Component, OnInit} from '@angular/core';
import {MenuService} from '../../services/menu.service';
import {MenuItemType} from '../../models/MenuItemType';
import {MatTableDataSource} from '@angular/material';
import {MenuItem} from '../../models/MenuItem';

@Component({
  selector: 'app-menu',
  templateUrl: './menu.component.html',
  styleUrls: ['./menu.component.scss']
})
export class MenuComponent implements OnInit {

  public menuItemType: MenuItemType[];
  public menuItems: MenuItem[];

  constructor(private menuService: MenuService) {
  }

  ngOnInit() {
    this.menuService.getMenuItemType().subscribe(response => {
      this.menuItemType = response;
    });

    this.menuService.getMenuItems().subscribe(response => {
      this.menuItems = response;
    });

  }
}
