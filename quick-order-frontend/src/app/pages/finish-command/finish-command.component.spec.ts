import {async, ComponentFixture, TestBed} from '@angular/core/testing';

import {FinishCommandComponent} from './finish-command.component';

describe('FinishCommandComponent', () => {
  let component: FinishCommandComponent;
  let fixture: ComponentFixture<FinishCommandComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [FinishCommandComponent]
    })
      .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(FinishCommandComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
