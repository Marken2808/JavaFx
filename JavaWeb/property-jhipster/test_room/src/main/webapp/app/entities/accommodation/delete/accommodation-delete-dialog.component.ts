import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IAccommodation } from '../accommodation.model';
import { AccommodationService } from '../service/accommodation.service';

@Component({
  templateUrl: './accommodation-delete-dialog.component.html',
})
export class AccommodationDeleteDialogComponent {
  accommodation?: IAccommodation;

  constructor(protected accommodationService: AccommodationService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.accommodationService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
