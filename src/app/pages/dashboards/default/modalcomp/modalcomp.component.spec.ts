import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ModalcompComponent } from './modalcomp.component';

describe('ModalcompComponent', () => {
  let component: ModalcompComponent;
  let fixture: ComponentFixture<ModalcompComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ModalcompComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(ModalcompComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
