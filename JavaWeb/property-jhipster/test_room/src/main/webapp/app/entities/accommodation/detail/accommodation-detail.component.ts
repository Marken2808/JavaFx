import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IAccommodation } from '../accommodation.model';
import { DataUtils } from 'app/core/util/data-util.service';

@Component({
  selector: 'jhi-accommodation-detail',
  templateUrl: './accommodation-detail.component.html',
})
export class AccommodationDetailComponent implements OnInit {
  accommodation: IAccommodation | null = null;

  constructor(protected dataUtils: DataUtils, protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ accommodation }) => {
      this.accommodation = accommodation;
    });
  }

  byteSize(base64String: string): string {
    return this.dataUtils.byteSize(base64String);
  }

  openFile(base64String: string, contentType: string | null | undefined): void {
    this.dataUtils.openFile(base64String, contentType);
  }

  previousState(): void {
    window.history.back();
  }
}
