import { Component, OnInit } from '@angular/core';
import {MenuItem} from '../../models/MenuItem';
import {Observable} from 'rxjs';
import {Ingredient} from '../../models/Ingredient';
import {MatSnackBar, MatTableDataSource} from '@angular/material';
import {MenuService} from '../../services/menu.service';
import {Table} from '../../models/Table';
import {TableService} from '../../services/table.service';

@Component({
  selector: 'app-table',
  templateUrl: './table.component.html',
  styleUrls: ['./table.component.scss']
})
export class TableComponent implements OnInit {

  tables: Table[];
  displayedColumns: string[] = ['tableNr', 'seats', 'windowView', 'floor', 'edit'];
  dataSource = new MatTableDataSource<Table>(this.tables);

  tableNrRight = true;
  seatsRight = true;
  floorRight = true;

  tableNr = 0;
  seats = 0;
  windowView = false;
  floor = 0;

  constructor(private tableService: TableService, private snackBar: MatSnackBar) {
  }

  ngOnInit() {
    this.updateTable();
  }
  showSnackbar(message: string) {
    this.snackBar.open(message, '', {
      duration: 3000,
      verticalPosition: 'top',
      panelClass: ['snackbar']
    });
  }

  updateTable() {
    this.tableService.getAllTables().subscribe(rez => {
      this.tables = rez;
      this.dataSource = new MatTableDataSource<Table>(this.tables);
    });
  }

  setUpdate(table: Table): void {
    this.tableNr = table.tableNr;
    this.seats = table.seats;
    this.windowView = table.windowView;
    this.floor = table.floor;
  }

  setDelete(table: Table): void {
    this.tableNr = table.tableNr;
  }

  add(): void {
    if (this.validation()) {
      let newTable: Table;
      newTable = new Table(this.tableNr, this.seats, this.windowView, this.floor);
      this.tableService.addTable(newTable).subscribe(rez => {
        window.location.reload();
      }, error => {
        if (error.status === 400) { // bad request
          this.showSnackbar('The introduced data is not valid!, please try again!');
        } else {
          this.showSnackbar('The introduced data is not valid!, please try again!');
        }
      });
    } else {
      this.showSnackbar('The introduced data is not valid!, please try again!');
    }
  }

  update(): void {
    if (this.validation()) {
      let newTable: Table;
      newTable = new Table(this.tableNr, this.seats, this.windowView, this.floor);
      this.tableService.editTable(newTable).subscribe(rez => {
        window.location.reload();
      }, error => {
        if (error.status === 404) { // not found exception
          this.showSnackbar('The introduced data is not valid!, please try again!');
        } else {
          this.showSnackbar('The introduced data is not valid!, please try again!');
        }
      });
    } else {
      this.showSnackbar('The introduced data is not valid!, please try again!');
    }
  }

  delete(): void {
    this.tableService.deleteTable(this.tableNr).subscribe(rez => {
      window.location.reload();
    }, error => {
      if (error.status === 404) { // not found exception
        this.showSnackbar('The item could not be deleted!, pleas try again!');
      } else {
        this.showSnackbar('The item could not be deleted!, pleas try again!');
      }
    });
  }

  clear(): void {
    this.tableNr = 0;
    this.seats = 0;
    this.windowView = false;
    this.floor = 0;
  }

  validation(): boolean {
    if (this.tableNr > 0) {
      this.tableNrRight = true;
    } else {
      this.tableNrRight = false;
    }
    if (this.seats < 0) {
      this.seatsRight = false;
    } else {
      this.seatsRight = true;
    }
    if (this.floor < -1) {
      this.floorRight = false;
    } else {
      this.floorRight = true;
    }
    if (!this.seatsRight || !this.tableNrRight || !this.floorRight) {
      return false;
    } else {
      return true;
    }
  }

}
