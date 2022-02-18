import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IName } from '../name.model';
import { NameService } from '../service/name.service';

@Component({
  templateUrl: './name-delete-dialog.component.html',
})
export class NameDeleteDialogComponent {
  name?: IName;

  constructor(protected nameService: NameService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.nameService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
