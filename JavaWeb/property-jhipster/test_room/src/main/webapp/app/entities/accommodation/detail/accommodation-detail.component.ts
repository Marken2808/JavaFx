import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IAccommodation } from '../accommodation.model';

@Component({
  selector: 'jhi-accommodation-detail',
  templateUrl: './accommodation-detail.component.html',
})
export class AccommodationDetailComponent implements OnInit {
  accommodation: IAccommodation | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ accommodation }) => {
      this.accommodation = accommodation;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
