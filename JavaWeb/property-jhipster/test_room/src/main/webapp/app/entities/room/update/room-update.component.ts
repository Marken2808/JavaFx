import { Component, OnInit, ElementRef } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { IRoom, Room } from '../room.model';
import { RoomService } from '../service/room.service';
import { AlertError } from 'app/shared/alert/alert-error.model';
import { EventManager, EventWithContent } from 'app/core/util/event-manager.service';
import { DataUtils, FileLoadError } from 'app/core/util/data-util.service';
import { IAccommodation } from 'app/entities/accommodation/accommodation.model';
import { AccommodationService } from 'app/entities/accommodation/service/accommodation.service';
import { RoomType } from 'app/entities/enumerations/room-type.model';
import { RoomSize } from 'app/entities/enumerations/room-size.model';

@Component({
  selector: 'jhi-room-update',
  templateUrl: './room-update.component.html',
})
export class RoomUpdateComponent implements OnInit {
  isSaving = false;
  roomTypeValues = Object.keys(RoomType);
  roomSizeValues = Object.keys(RoomSize);

  accommodationsSharedCollection: IAccommodation[] = [];

  editForm = this.fb.group({
    id: [],
    rTitle: [null, [Validators.required]],
    rType: [null, [Validators.required]],
    rAcreage: [null, [Validators.required]],
    rSize: [],
    rImage: [],
    rImageContentType: [],
    rPrice: [],
    accommodation: [null, Validators.required],
  });

  constructor(
    protected dataUtils: DataUtils,
    protected eventManager: EventManager,
    protected roomService: RoomService,
    protected accommodationService: AccommodationService,
    protected elementRef: ElementRef,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ room }) => {
      this.updateForm(room);

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
    const room = this.createFromForm();
    if (room.id !== undefined) {
      this.subscribeToSaveResponse(this.roomService.update(room));
    } else {
      this.subscribeToSaveResponse(this.roomService.create(room));
    }
  }

  trackAccommodationById(index: number, item: IAccommodation): number {
    return item.id!;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IRoom>>): void {
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

  protected updateForm(room: IRoom): void {
    this.editForm.patchValue({
      id: room.id,
      rTitle: room.rTitle,
      rType: room.rType,
      rAcreage: room.rAcreage,
      rSize: room.rSize,
      rImage: room.rImage,
      rImageContentType: room.rImageContentType,
      rPrice: room.rPrice,
      accommodation: room.accommodation,
    });

    this.accommodationsSharedCollection = this.accommodationService.addAccommodationToCollectionIfMissing(
      this.accommodationsSharedCollection,
      room.accommodation
    );
  }

  protected loadRelationshipsOptions(): void {
    this.accommodationService
      .query()
      .pipe(map((res: HttpResponse<IAccommodation[]>) => res.body ?? []))
      .pipe(
        map((accommodations: IAccommodation[]) =>
          this.accommodationService.addAccommodationToCollectionIfMissing(accommodations, this.editForm.get('accommodation')!.value)
        )
      )
      .subscribe((accommodations: IAccommodation[]) => (this.accommodationsSharedCollection = accommodations));
  }

  protected createFromForm(): IRoom {
    return {
      ...new Room(),
      id: this.editForm.get(['id'])!.value,
      rTitle: this.editForm.get(['rTitle'])!.value,
      rType: this.editForm.get(['rType'])!.value,
      rAcreage: this.editForm.get(['rAcreage'])!.value,
      rSize: this.editForm.get(['rSize'])!.value,
      rImageContentType: this.editForm.get(['rImageContentType'])!.value,
      rImage: this.editForm.get(['rImage'])!.value,
      rPrice: this.editForm.get(['rPrice'])!.value,
      accommodation: this.editForm.get(['accommodation'])!.value,
    };
  }
}
