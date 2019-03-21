import { Component, OnInit } from '@angular/core';
import {Table} from '../../models/Table';
import {Observable} from 'rxjs';
import {TableService} from '../../services/table.service';

@Component({
  selector: 'app-table-view',
  templateUrl: './table-view.component.html',
  styleUrls: ['./table-view.component.scss']
})
export class TableViewComponent implements OnInit {

  tables: Table[];
  tablesGet: Observable<Table[]>;

  constructor(private tableService: TableService) { }

  ngOnInit() {
    this.tablesGet = this.tableService.getAllTables();
    this.tables = [];
    this.tablesGet.forEach(table => table.forEach(t => this.tables.push(t)));

  }

}
