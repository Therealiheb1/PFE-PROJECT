import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AddSoldComponent } from './add-sold.component';

describe('AddSoldComponent', () => {
  let component: AddSoldComponent;
  let fixture: ComponentFixture<AddSoldComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ AddSoldComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(AddSoldComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
