import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IArea } from '../area.model';
import { AreaService } from '../service/area.service';
import { AreaDeleteDialogComponent } from '../delete/area-delete-dialog.component';

@Component({
  selector: 'jhi-area',
  templateUrl: './area.component.html',
})
export class AreaComponent implements OnInit {
  areas?: IArea[];
  isLoading = false;

  constructor(protected areaService: AreaService, protected modalService: NgbModal) {}

  loadAll(): void {
    this.isLoading = true;

    this.areaService.query().subscribe({
      next: (res: HttpResponse<IArea[]>) => {
        this.isLoading = false;
        this.areas = res.body ?? [];
      },
      error: () => {
        this.isLoading = false;
      },
    });
  }

  ngOnInit(): void {
    this.loadAll();
  }

  trackId(index: number, item: IArea): number {
    return item.id!;
  }

  delete(area: IArea): void {
    const modalRef = this.modalService.open(AreaDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.area = area;
    // unsubscribe not needed because closed completes on modal close
    modalRef.closed.subscribe(reason => {
      if (reason === 'deleted') {
        this.loadAll();
      }
    });
  }
}
