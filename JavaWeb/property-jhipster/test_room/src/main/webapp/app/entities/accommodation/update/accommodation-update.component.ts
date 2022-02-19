import { Component, OnInit, ElementRef } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { IAccommodation, Accommodation } from '../accommodation.model';
import { AccommodationService } from '../service/accommodation.service';
import { AlertError } from 'app/shared/alert/alert-error.model';
import { EventManager, EventWithContent } from 'app/core/util/event-manager.service';
import { DataUtils, FileLoadError } from 'app/core/util/data-util.service';
import { IRoom } from 'app/entities/room/room.model';
import { RoomService } from 'app/entities/room/service/room.service';
import { AccommodationType } from 'app/entities/enumerations/accommodation-type.model';
import { AccommodationStatus } from 'app/entities/enumerations/accommodation-status.model';

@Component({
  selector: 'jhi-accommodation-update',
  templateUrl: './accommodation-update.component.html',
})
export class AccommodationUpdateComponent implements OnInit {
  isSaving = false;
  accommodationTypeValues = Object.keys(AccommodationType);
  accommodationStatusValues = Object.keys(AccommodationStatus);

  roomsSharedCollection: IRoom[] = [];

  editForm = this.fb.group({
    id: [],
    title: [null, [Validators.required]],
    type: [null, [Validators.required]],
    status: [null, [Validators.required]],
    acreage: [null, [Validators.required]],
    image: [],
    imageContentType: [],
    price: [],
    rooms: [],
  });

  constructor(
    protected dataUtils: DataUtils,
    protected eventManager: EventManager,
    protected accommodationService: AccommodationService,
    protected roomService: RoomService,
    protected elementRef: ElementRef,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ accommodation }) => {
      this.updateForm(accommodation);

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
    const accommodation = this.createFromForm();
    if (accommodation.id !== undefined) {
      this.subscribeToSaveResponse(this.accommodationService.update(accommodation));
    } else {
      this.subscribeToSaveResponse(this.accommodationService.create(accommodation));
    }
  }

  trackRoomById(index: number, item: IRoom): number {
    return item.id!;
  }

  getSelectedRoom(option: IRoom, selectedVals?: IRoom[]): IRoom {
    if (selectedVals) {
      for (const selectedVal of selectedVals) {
        if (option.id === selectedVal.id) {
          return selectedVal;
        }
      }
    }
    return option;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IAccommodation>>): void {
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

  protected updateForm(accommodation: IAccommodation): void {
    this.editForm.patchValue({
      id: accommodation.id,
      title: accommodation.title,
      type: accommodation.type,
      status: accommodation.status,
      acreage: accommodation.acreage,
      image: accommodation.image,
      imageContentType: accommodation.imageContentType,
      price: accommodation.price,
      rooms: accommodation.rooms,
    });

    this.roomsSharedCollection = this.roomService.addRoomToCollectionIfMissing(this.roomsSharedCollection, ...(accommodation.rooms ?? []));
  }

  protected loadRelationshipsOptions(): void {
    this.roomService
      .query()
      .pipe(map((res: HttpResponse<IRoom[]>) => res.body ?? []))
      .pipe(map((rooms: IRoom[]) => this.roomService.addRoomToCollectionIfMissing(rooms, ...(this.editForm.get('rooms')!.value ?? []))))
      .subscribe((rooms: IRoom[]) => (this.roomsSharedCollection = rooms));
  }

  protected createFromForm(): IAccommodation {
    return {
      ...new Accommodation(),
      id: this.editForm.get(['id'])!.value,
      title: this.editForm.get(['title'])!.value,
      type: this.editForm.get(['type'])!.value,
      status: this.editForm.get(['status'])!.value,
      acreage: this.editForm.get(['acreage'])!.value,
      imageContentType: this.editForm.get(['imageContentType'])!.value,
      image: this.editForm.get(['image'])!.value,
      price: this.editForm.get(['price'])!.value,
      rooms: this.editForm.get(['rooms'])!.value,
    };
  }
}
