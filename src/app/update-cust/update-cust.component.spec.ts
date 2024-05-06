import { ComponentFixture, TestBed } from '@angular/core/testing';

import { UpdateCustComponent } from './update-cust.component';

describe('UpdateCustComponent', () => {
  let component: UpdateCustComponent;
  let fixture: ComponentFixture<UpdateCustComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ UpdateCustComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(UpdateCustComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
