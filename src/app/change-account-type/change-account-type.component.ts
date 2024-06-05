import { Component, EventEmitter, Output } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

@Component({
  selector: 'app-change-account-type',
  templateUrl: './change-account-type.component.html',
  styleUrls: ['./change-account-type.component.scss']
})
export class ChangeAccountTypeComponent {

  @Output() typeChanged = new EventEmitter<string>();
  selectedType: string;

  constructor(public activeModal: NgbActiveModal) { }

  closeModal() {
    this.activeModal.dismiss('Cross click');
  }

  changeType() {
    this.typeChanged.emit(this.selectedType);
    this.activeModal.close('Close click');
  }
}
