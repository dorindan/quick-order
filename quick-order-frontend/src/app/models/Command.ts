import {CommandMenuItem} from './CommandMenuItem';

export class Command {
  public commandName: string;
  public specification: string;
  public isPacked: boolean;
  public status: string;
  public commandMenuItemDtos: CommandMenuItem[] = [];

  constructor() {
  }
}
