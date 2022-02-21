import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IProperty } from '../property.model';
import { DataUtils } from 'app/core/util/data-util.service';

@Component({
  selector: 'jhi-property-detail',
  templateUrl: './property-detail.component.html',
})
export class PropertyDetailComponent implements OnInit {
  property: IProperty | null = null;

  constructor(protected dataUtils: DataUtils, protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ property }) => {
      this.property = property;
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
