import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CpannelComponent } from './cpannel.component';

describe('CpannelComponent', () => {
  let component: CpannelComponent;
  let fixture: ComponentFixture<CpannelComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ CpannelComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(CpannelComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
