import { Component, OnInit, ElementRef } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { ILand, Land } from '../land.model';
import { LandService } from '../service/land.service';
import { AlertError } from 'app/shared/alert/alert-error.model';
import { EventManager, EventWithContent } from 'app/core/util/event-manager.service';
import { DataUtils, FileLoadError } from 'app/core/util/data-util.service';
import { IProperty } from 'app/entities/property/property.model';
import { PropertyService } from 'app/entities/property/service/property.service';

@Component({
  selector: 'jhi-land-update',
  templateUrl: './land-update.component.html',
})
export class LandUpdateComponent implements OnInit {
  isSaving = false;

  propertiesCollection: IProperty[] = [];

  editForm = this.fb.group({
    id: [],
    title: [null, [Validators.required]],
    price: [null, [Validators.required]],
    image: [],
    imageContentType: [],
    property: [],
  });

  constructor(
    protected dataUtils: DataUtils,
    protected eventManager: EventManager,
    protected landService: LandService,
    protected propertyService: PropertyService,
    protected elementRef: ElementRef,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ land }) => {
      this.updateForm(land);

      this.loadRelationshipsOptions();
    });
  }

  byteSize(base64String: string): string {
    return this.dataUtils.byteSize(base64String);
  }

  openFile(base64String: string, contentType: string | null | undefined): void {
    this.dataUtils.openFile(base64String, contentType);
  }

  setFileData(event: Event, field: string, isImage: boolean): void {
    this.dataUtils.loadFileToForm(event, this.editForm, field, isImage).subscribe({
      error: (err: FileLoadError) =>
        this.eventManager.broadcast(new EventWithContent<AlertError>('testRoomApp.error', { ...err, key: 'error.file.' + err.key })),
    });
  }

  clearInputImage(field: string, fieldContentType: string, idInput: string): void {
    this.editForm.patchValue({
      [field]: null,
      [fieldContentType]: null,
    });
    if (idInput && this.elementRef.nativeElement.querySelector('#' + idInput)) {
      this.elementRef.nativeElement.querySelector('#' + idInput).value = null;
    }
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const land = this.createFromForm();
    if (land.id !== undefined) {
      this.subscribeToSaveResponse(this.landService.update(land));
    } else {
      this.subscribeToSaveResponse(this.landService.create(land));
    }
  }

  trackPropertyById(index: number, item: IProperty): number {
    return item.id!;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ILand>>): void {
    result.pipe(finalize(() => this.onSaveFinalize())).subscribe({
      next: () => this.onSaveSuccess(),
      error: () => this.onSaveError(),
    });
  }

  protected onSaveSuccess(): void {
    this.previousState();
  }

  protected onSaveError(): void {
    // Api for inheritance.
  }

  protected onSaveFinalize(): void {
    this.isSaving = false;
  }

  protected updateForm(land: ILand): void {
    this.editForm.patchValue({
      id: land.id,
      title: land.title,
      price: land.price,
      image: land.image,
      imageContentType: land.imageContentType,
      property: land.property,
    });

    this.propertiesCollection = this.propertyService.addPropertyToCollectionIfMissing(this.propertiesCollection, land.property);
  }

  protected loadRelationshipsOptions(): void {
    this.propertyService
      .query({ filter: 'land-is-null' })
      .pipe(map((res: HttpResponse<IProperty[]>) => res.body ?? []))
      .pipe(
        map((properties: IProperty[]) =>
          this.propertyService.addPropertyToCollectionIfMissing(properties, this.editForm.get('property')!.value)
        )
      )
      .subscribe((properties: IProperty[]) => (this.propertiesCollection = properties));
  }

  protected createFromForm(): ILand {
    return {
      ...new Land(),
      id: this.editForm.get(['id'])!.value,
      title: this.editForm.get(['title'])!.value,
      price: this.editForm.get(['price'])!.value,
      imageContentType: this.editForm.get(['imageContentType'])!.value,
      image: this.editForm.get(['image'])!.value,
      property: this.editForm.get(['property'])!.value,
    };
  }
}
