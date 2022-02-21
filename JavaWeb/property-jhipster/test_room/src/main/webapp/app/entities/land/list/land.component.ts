import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { ILand } from '../land.model';
import { LandService } from '../service/land.service';
import { LandDeleteDialogComponent } from '../delete/land-delete-dialog.component';

@Component({
  selector: 'jhi-land',
  templateUrl: './land.component.html',
})
export class LandComponent implements OnInit {
  lands?: ILand[];
  isLoading = false;

  constructor(protected landService: LandService, protected modalService: NgbModal) {}

  loadAll(): void {
    this.isLoading = true;

    this.landService.query().subscribe({
      next: (res: HttpResponse<ILand[]>) => {
        this.isLoading = false;
        this.lands = res.body ?? [];
      },
      error: () => {
        this.isLoading = false;
      },
    });
  }

  ngOnInit(): void {
    this.loadAll();
  }

  trackId(index: number, item: ILand): number {
    return item.id!;
  }

  delete(land: ILand): void {
    const modalRef = this.modalService.open(LandDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.land = land;
    // unsubscribe not needed because closed completes on modal close
    modalRef.closed.subscribe(reason => {
      if (reason === 'deleted') {
        this.loadAll();
      }
    });
  }
}
