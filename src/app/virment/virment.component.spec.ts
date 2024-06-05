import { ComponentFixture, TestBed } from '@angular/core/testing';

import { VirmentComponent } from './virment.component';

describe('VirmentComponent', () => {
  let component: VirmentComponent;
  let fixture: ComponentFixture<VirmentComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ VirmentComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(VirmentComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
