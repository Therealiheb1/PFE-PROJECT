import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AdminpanComponent } from './adminpan.component';

describe('AdminpanComponent', () => {
  let component: AdminpanComponent;
  let fixture: ComponentFixture<AdminpanComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ AdminpanComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(AdminpanComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
