import { Component } from '@angular/core';
import { TranslateService } from '@ngx-translate/core';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent {
  title = 'QuickOrder';
  constructor(private translate: TranslateService) {
    translate.setDefaultLang('en');
  }
  get myMethodFunc() {
    return this.switchLanguage.bind(this);
  }

  public switchLanguage(language: string) {
    this.translate.use(language);
  }
}
