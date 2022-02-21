import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { IProperty, Property } from '../property.model';
import { PropertyService } from '../service/property.service';
import { IAddress } from 'app/entities/address/address.model';
import { AddressService } from 'app/entities/address/service/address.service';
import { IAccommodation } from 'app/entities/accommodation/accommodation.model';
import { AccommodationService } from 'app/entities/accommodation/service/accommodation.service';
import { IProject } from 'app/entities/project/project.model';
import { ProjectService } from 'app/entities/project/service/project.service';
import { ILand } from 'app/entities/land/land.model';
import { LandService } from 'app/entities/land/service/land.service';
import { PropertyType } from 'app/entities/enumerations/property-type.model';
import { PropertyStatus } from 'app/entities/enumerations/property-status.model';

@Component({
  selector: 'jhi-property-update',
  templateUrl: './property-update.component.html',
})
export class PropertyUpdateComponent implements OnInit {
  isSaving = false;
  propertyTypeValues = Object.keys(PropertyType);
  propertyStatusValues = Object.keys(PropertyStatus);

  addressesCollection: IAddress[] = [];
  accommodationsCollection: IAccommodation[] = [];
  projectsCollection: IProject[] = [];
  landsCollection: ILand[] = [];

  editForm = this.fb.group({
    id: [],
    title: [null, [Validators.required]],
    type: [null, [Validators.required]],
    status: [null, [Validators.required]],
    isUrgent: [],
    address: [null, Validators.required],
    accommodation: [],
    project: [],
    land: [],
  });

  constructor(
    protected propertyService: PropertyService,
    protected addressService: AddressService,
    protected accommodationService: AccommodationService,
    protected projectService: ProjectService,
    protected landService: LandService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ property }) => {
      this.updateForm(property);

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const property = this.createFromForm();
    if (property.id !== undefined) {
      this.subscribeToSaveResponse(this.propertyService.update(property));
    } else {
      this.subscribeToSaveResponse(this.propertyService.create(property));
    }
  }

  trackAddressById(index: number, item: IAddress): number {
    return item.id!;
  }

  trackAccommodationById(index: number, item: IAccommodation): number {
    return item.id!;
  }

  trackProjectById(index: number, item: IProject): number {
    return item.id!;
  }

  trackLandById(index: number, item: ILand): number {
    return item.id!;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IProperty>>): void {
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

  protected updateForm(property: IProperty): void {
    this.editForm.patchValue({
      id: property.id,
      title: property.title,
      type: property.type,
      status: property.status,
      isUrgent: property.isUrgent,
      address: property.address,
      accommodation: property.accommodation,
      project: property.project,
      land: property.land,
    });

    this.addressesCollection = this.addressService.addAddressToCollectionIfMissing(this.addressesCollection, property.address);
    this.accommodationsCollection = this.accommodationService.addAccommodationToCollectionIfMissing(
      this.accommodationsCollection,
      property.accommodation
    );
    this.projectsCollection = this.projectService.addProjectToCollectionIfMissing(this.projectsCollection, property.project);
    this.landsCollection = this.landService.addLandToCollectionIfMissing(this.landsCollection, property.land);
  }

  protected loadRelationshipsOptions(): void {
    this.addressService
      .query({ filter: 'property-is-null' })
      .pipe(map((res: HttpResponse<IAddress[]>) => res.body ?? []))
      .pipe(
        map((addresses: IAddress[]) => this.addressService.addAddressToCollectionIfMissing(addresses, this.editForm.get('address')!.value))
      )
      .subscribe((addresses: IAddress[]) => (this.addressesCollection = addresses));

    this.accommodationService
      .query({ filter: 'property-is-null' })
      .pipe(map((res: HttpResponse<IAccommodation[]>) => res.body ?? []))
      .pipe(
        map((accommodations: IAccommodation[]) =>
          this.accommodationService.addAccommodationToCollectionIfMissing(accommodations, this.editForm.get('accommodation')!.value)
        )
      )
      .subscribe((accommodations: IAccommodation[]) => (this.accommodationsCollection = accommodations));

    this.projectService
      .query({ filter: 'property-is-null' })
      .pipe(map((res: HttpResponse<IProject[]>) => res.body ?? []))
      .pipe(
        map((projects: IProject[]) => this.projectService.addProjectToCollectionIfMissing(projects, this.editForm.get('project')!.value))
      )
      .subscribe((projects: IProject[]) => (this.projectsCollection = projects));

    this.landService
      .query({ filter: 'property-is-null' })
      .pipe(map((res: HttpResponse<ILand[]>) => res.body ?? []))
      .pipe(map((lands: ILand[]) => this.landService.addLandToCollectionIfMissing(lands, this.editForm.get('land')!.value)))
      .subscribe((lands: ILand[]) => (this.landsCollection = lands));
  }

  protected createFromForm(): IProperty {
    return {
      ...new Property(),
      id: this.editForm.get(['id'])!.value,
      title: this.editForm.get(['title'])!.value,
      type: this.editForm.get(['type'])!.value,
      status: this.editForm.get(['status'])!.value,
      isUrgent: this.editForm.get(['isUrgent'])!.value,
      address: this.editForm.get(['address'])!.value,
      accommodation: this.editForm.get(['accommodation'])!.value,
      project: this.editForm.get(['project'])!.value,
      land: this.editForm.get(['land'])!.value,
    };
  }
}
