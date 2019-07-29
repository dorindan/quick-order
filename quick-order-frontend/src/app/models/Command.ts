import {MenuItemCommand} from './MenuItemCommand';
import {User} from './User';

export class Command {
  public commandName: string;
  public specification: string;
  public packed: boolean;
  public status: string;
  public userDto: User;
  public menuItemCommandDtos: MenuItemCommand[] = [];

  constructor() {
  }
}
