import {async, ComponentFixture, TestBed} from '@angular/core/testing';

import {PropertyAdministrationComponent} from './property-administration.component';

describe('PropertyAdministrationComponent', () => {
  let component: PropertyAdministrationComponent;
  let fixture: ComponentFixture<PropertyAdministrationComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [PropertyAdministrationComponent]
    })
      .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(PropertyAdministrationComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
