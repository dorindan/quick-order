import {Component, ElementRef, OnInit, ViewChild} from '@angular/core';
import {MatAutocomplete, MatAutocompleteSelectedEvent, MatChipInputEvent, MatSnackBar} from '@angular/material';
import {UserService} from '../../services/user.service';
import {User} from '../../models/User';
import {Observable} from 'rxjs';
import {FormControl} from '@angular/forms';
import {map, startWith} from 'rxjs/operators';
import {COMMA, ENTER} from '@angular/cdk/keycodes';
import {Authorities} from '../../models/Authorities';
import {RoleService} from '../../services/role.service';


@Component({
  selector: 'app-user-administration',
  templateUrl: './user-administration.component.html',
  styleUrls: ['./user-administration.component.scss']
})
export class UserAdministrationComponent implements OnInit {
  visible = true;
  selectable = true;
  removable = true;
  addOnBlur = true;
  separatorKeysCodes: number[] = [ENTER, COMMA];
  roleCtrl = new FormControl();
  filteredRoles: Observable<string[]>;
  roles: string[] = [];
  allRoles: string[] = [];
  @ViewChild('roleInput') roleInput: ElementRef<HTMLInputElement>;
  @ViewChild('auto') matAutocomplete: MatAutocomplete;
  userToBeChanged: User;
  private usersGet: Observable<User[]>;
  public users: User[];
  private rolesGet: Observable<Authorities>;

  constructor(private userService: UserService,
              private roleService: RoleService,
              private snackBar: MatSnackBar) {
    this.filteredRoles = this.roleCtrl.valueChanges.pipe(
      startWith(null),
      map((role: string | null) => role ? this._filter(role) : this.allRoles.slice()));
  }

  ngOnInit() {
    this.usersGet = this.userService.getAllUsers();
    this.users = [];
    this.usersGet.forEach(user => user.forEach(u => this.users.push(u)));
    this.rolesGet = this.roleService.getAllRoles();
    this.allRoles = [];
    this.rolesGet.forEach(roles => this.allRoles = roles.roles);
  }

  add(event: MatChipInputEvent): void {
    // Add role only when MatAutocomplete is not open
    // To make sure this does not conflict with OptionSelected Event
    if (!this.matAutocomplete.isOpen) {
      const input = event.input;
      const value = event.value;
      // Add our role
      if ((value || '').trim()) {
        this.roles.push(value.trim());
      }

      // Reset the input value
      if (input) {
        input.value = '';
      }

      this.roleCtrl.setValue(null);
    }
  }

  remove(role: string): void {
    const index = this.roles.indexOf(role);

    if (index >= 0) {
      this.roles.splice(index, 1);
    }
  }

  selected(event: MatAutocompleteSelectedEvent): void {
    this.roles.push(event.option.viewValue);
    this.roleInput.nativeElement.value = '';
    this.roleCtrl.setValue(null);
    this.roles = this.eliminateDuplicates(this.roles);
  }

  editUser(user: User) {
    this.roles = user.roles;
    this.userToBeChanged = user;
    window.scroll(0, 0);
  }

  confirm() {
    this.userToBeChanged.roles = this.roles;
    this.userService.updateUser(this.userToBeChanged).subscribe(data => {
        this.showSnackbar('Successfully updated');
        location.reload();
      },
      error => {
        this.showSnackbar(error.error.message);
      });
    location.reload();
  }

  eliminateDuplicates(roles: string[]): string[] {
    const unique = roles.filter(function (elem, index, self) {
      return index === self.indexOf(elem);
    });
    return unique;
  }

  showSnackbar(message: string) {
    this.snackBar.open(message, '', {
      duration: 3000,
      verticalPosition: 'top',
      panelClass: ['snackbar']
    });
  }

  private _filter(value: string): string[] {
    const filterValue = value.toLowerCase();

    return this.allRoles.filter(role => role.toLowerCase().indexOf(filterValue) === 0);
  }
}
