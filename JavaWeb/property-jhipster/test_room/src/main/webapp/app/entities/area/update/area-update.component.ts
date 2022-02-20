import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { IArea, Area } from '../area.model';
import { AreaService } from '../service/area.service';
import { AreaType } from 'app/entities/enumerations/area-type.model';

@Component({
  selector: 'jhi-area-update',
  templateUrl: './area-update.component.html',
})
export class AreaUpdateComponent implements OnInit {
  isSaving = false;
  areaTypeValues = Object.keys(AreaType);

  editForm = this.fb.group({
    id: [],
    title: [null, [Validators.required]],
    type: [null, [Validators.required]],
  });

  constructor(protected areaService: AreaService, protected activatedRoute: ActivatedRoute, protected fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ area }) => {
      this.updateForm(area);
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const area = this.createFromForm();
    if (area.id !== undefined) {
      this.subscribeToSaveResponse(this.areaService.update(area));
    } else {
      this.subscribeToSaveResponse(this.areaService.create(area));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IArea>>): void {
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

  protected updateForm(area: IArea): void {
    this.editForm.patchValue({
      id: area.id,
      title: area.title,
      type: area.type,
    });
  }

  protected createFromForm(): IArea {
    return {
      ...new Area(),
      id: this.editForm.get(['id'])!.value,
      title: this.editForm.get(['title'])!.value,
      type: this.editForm.get(['type'])!.value,
    };
  }
}
