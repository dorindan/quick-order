import {async, ComponentFixture, TestBed} from '@angular/core/testing';
import {StartLoggedInComponent} from './start-logged-in.component';

describe('StartLoggedInComponent', () => {
  let component: StartLoggedInComponent;
  let fixture: ComponentFixture<StartLoggedInComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [StartLoggedInComponent]
    })
      .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(StartLoggedInComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
