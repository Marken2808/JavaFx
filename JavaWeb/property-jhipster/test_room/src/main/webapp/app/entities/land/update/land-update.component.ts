import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { ILand, Land } from '../land.model';
import { LandService } from '../service/land.service';

@Component({
  selector: 'jhi-land-update',
  templateUrl: './land-update.component.html',
})
export class LandUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    title: [null, [Validators.required]],
    price: [null, [Validators.required]],
  });

  constructor(protected landService: LandService, protected activatedRoute: ActivatedRoute, protected fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ land }) => {
      this.updateForm(land);
    });
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
    });
  }

  protected createFromForm(): ILand {
    return {
      ...new Land(),
      id: this.editForm.get(['id'])!.value,
      title: this.editForm.get(['title'])!.value,
      price: this.editForm.get(['price'])!.value,
    };
  }
}
