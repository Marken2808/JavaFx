import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { IName, Name } from '../name.model';
import { NameService } from '../service/name.service';
import { Title } from 'app/entities/enumerations/title.model';

@Component({
  selector: 'jhi-name-update',
  templateUrl: './name-update.component.html',
})
export class NameUpdateComponent implements OnInit {
  isSaving = false;
  titleValues = Object.keys(Title);

  editForm = this.fb.group({
    id: [],
    title: [null, [Validators.required]],
    firstName: [null, [Validators.required]],
    middleName: [],
    lastName: [null, [Validators.required]],
    displayName: [null, [Validators.required]],
  });

  constructor(protected nameService: NameService, protected activatedRoute: ActivatedRoute, protected fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ name }) => {
      this.updateForm(name);
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const name = this.createFromForm();
    if (name.id !== undefined) {
      this.subscribeToSaveResponse(this.nameService.update(name));
    } else {
      this.subscribeToSaveResponse(this.nameService.create(name));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IName>>): void {
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

  protected updateForm(name: IName): void {
    this.editForm.patchValue({
      id: name.id,
      title: name.title,
      firstName: name.firstName,
      middleName: name.middleName,
      lastName: name.lastName,
      displayName: name.displayName,
    });
  }

  protected createFromForm(): IName {
    return {
      ...new Name(),
      id: this.editForm.get(['id'])!.value,
      title: this.editForm.get(['title'])!.value,
      firstName: this.editForm.get(['firstName'])!.value,
      middleName: this.editForm.get(['middleName'])!.value,
      lastName: this.editForm.get(['lastName'])!.value,
      displayName: this.editForm.get(['displayName'])!.value,
    };
  }
}
