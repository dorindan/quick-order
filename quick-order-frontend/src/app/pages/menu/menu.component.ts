import {Component, OnInit} from '@angular/core';
import {MenuService} from '../../services/menu.service';
import {Menu} from '../../models/Menu';

@Component({
  selector: 'app-menu',
  templateUrl: './menu.component.html',
  styleUrls: ['./menu.component.scss']
})
export class MenuComponent implements OnInit {

  public menus: Menu[];

  constructor(private menuService: MenuService) {
  }

  ngOnInit() {
    this.menuService.getMenu().subscribe(response => {
      this.menus = response;

      alert(this.menus.length);
      alert(this.menus[0].menuItems.length);
    });
  }
}
