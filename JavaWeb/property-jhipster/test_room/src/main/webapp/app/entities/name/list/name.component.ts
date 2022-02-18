import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IName } from '../name.model';
import { NameService } from '../service/name.service';
import { NameDeleteDialogComponent } from '../delete/name-delete-dialog.component';

@Component({
  selector: 'jhi-name',
  templateUrl: './name.component.html',
})
export class NameComponent implements OnInit {
  names?: IName[];
  isLoading = false;

  constructor(protected nameService: NameService, protected modalService: NgbModal) {}

  loadAll(): void {
    this.isLoading = true;

    this.nameService.query().subscribe({
      next: (res: HttpResponse<IName[]>) => {
        this.isLoading = false;
        this.names = res.body ?? [];
      },
      error: () => {
        this.isLoading = false;
      },
    });
  }

  ngOnInit(): void {
    this.loadAll();
  }

  trackId(index: number, item: IName): number {
    return item.id!;
  }

  delete(name: IName): void {
    const modalRef = this.modalService.open(NameDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.name = name;
    // unsubscribe not needed because closed completes on modal close
    modalRef.closed.subscribe(reason => {
      if (reason === 'deleted') {
        this.loadAll();
      }
    });
  }
}
