import {MenuItemCommand} from './MenuItemCommand';

export class Command {
  public commandName: string;
  public specification: string;
  public isPacked: boolean;
  public status: string;
  public menuItemCommandDtos: MenuItemCommand[] = [];

  constructor() {
  }
}
