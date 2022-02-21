import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { PropertyService } from '../service/property.service';
import { IProperty, Property } from '../property.model';
import { IAddress } from 'app/entities/address/address.model';
import { AddressService } from 'app/entities/address/service/address.service';
import { IAccommodation } from 'app/entities/accommodation/accommodation.model';
import { AccommodationService } from 'app/entities/accommodation/service/accommodation.service';
import { IProject } from 'app/entities/project/project.model';
import { ProjectService } from 'app/entities/project/service/project.service';
import { ILand } from 'app/entities/land/land.model';
import { LandService } from 'app/entities/land/service/land.service';

import { PropertyUpdateComponent } from './property-update.component';

describe('Property Management Update Component', () => {
  let comp: PropertyUpdateComponent;
  let fixture: ComponentFixture<PropertyUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let propertyService: PropertyService;
  let addressService: AddressService;
  let accommodationService: AccommodationService;
  let projectService: ProjectService;
  let landService: LandService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [PropertyUpdateComponent],
      providers: [
        FormBuilder,
        {
          provide: ActivatedRoute,
          useValue: {
            params: from([{}]),
          },
        },
      ],
    })
      .overrideTemplate(PropertyUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(PropertyUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    propertyService = TestBed.inject(PropertyService);
    addressService = TestBed.inject(AddressService);
    accommodationService = TestBed.inject(AccommodationService);
    projectService = TestBed.inject(ProjectService);
    landService = TestBed.inject(LandService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call address query and add missing value', () => {
      const property: IProperty = { id: 456 };
      const address: IAddress = { id: 949 };
      property.address = address;

      const addressCollection: IAddress[] = [{ id: 59735 }];
      jest.spyOn(addressService, 'query').mockReturnValue(of(new HttpResponse({ body: addressCollection })));
      const expectedCollection: IAddress[] = [address, ...addressCollection];
      jest.spyOn(addressService, 'addAddressToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ property });
      comp.ngOnInit();

      expect(addressService.query).toHaveBeenCalled();
      expect(addressService.addAddressToCollectionIfMissing).toHaveBeenCalledWith(addressCollection, address);
      expect(comp.addressesCollection).toEqual(expectedCollection);
    });

    it('Should call accommodation query and add missing value', () => {
      const property: IProperty = { id: 456 };
      const accommodation: IAccommodation = { id: 78429 };
      property.accommodation = accommodation;

      const accommodationCollection: IAccommodation[] = [{ id: 45428 }];
      jest.spyOn(accommodationService, 'query').mockReturnValue(of(new HttpResponse({ body: accommodationCollection })));
      const expectedCollection: IAccommodation[] = [accommodation, ...accommodationCollection];
      jest.spyOn(accommodationService, 'addAccommodationToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ property });
      comp.ngOnInit();

      expect(accommodationService.query).toHaveBeenCalled();
      expect(accommodationService.addAccommodationToCollectionIfMissing).toHaveBeenCalledWith(accommodationCollection, accommodation);
      expect(comp.accommodationsCollection).toEqual(expectedCollection);
    });

    it('Should call project query and add missing value', () => {
      const property: IProperty = { id: 456 };
      const project: IProject = { id: 56634 };
      property.project = project;

      const projectCollection: IProject[] = [{ id: 27778 }];
      jest.spyOn(projectService, 'query').mockReturnValue(of(new HttpResponse({ body: projectCollection })));
      const expectedCollection: IProject[] = [project, ...projectCollection];
      jest.spyOn(projectService, 'addProjectToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ property });
      comp.ngOnInit();

      expect(projectService.query).toHaveBeenCalled();
      expect(projectService.addProjectToCollectionIfMissing).toHaveBeenCalledWith(projectCollection, project);
      expect(comp.projectsCollection).toEqual(expectedCollection);
    });

    it('Should call land query and add missing value', () => {
      const property: IProperty = { id: 456 };
      const land: ILand = { id: 83622 };
      property.land = land;

      const landCollection: ILand[] = [{ id: 37801 }];
      jest.spyOn(landService, 'query').mockReturnValue(of(new HttpResponse({ body: landCollection })));
      const expectedCollection: ILand[] = [land, ...landCollection];
      jest.spyOn(landService, 'addLandToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ property });
      comp.ngOnInit();

      expect(landService.query).toHaveBeenCalled();
      expect(landService.addLandToCollectionIfMissing).toHaveBeenCalledWith(landCollection, land);
      expect(comp.landsCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const property: IProperty = { id: 456 };
      const address: IAddress = { id: 84136 };
      property.address = address;
      const accommodation: IAccommodation = { id: 14100 };
      property.accommodation = accommodation;
      const project: IProject = { id: 89574 };
      property.project = project;
      const land: ILand = { id: 33444 };
      property.land = land;

      activatedRoute.data = of({ property });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(property));
      expect(comp.addressesCollection).toContain(address);
      expect(comp.accommodationsCollection).toContain(accommodation);
      expect(comp.projectsCollection).toContain(project);
      expect(comp.landsCollection).toContain(land);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Property>>();
      const property = { id: 123 };
      jest.spyOn(propertyService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ property });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: property }));
      saveSubject.complete();

      // THEN
      expect(comp.previousState).toHaveBeenCalled();
      expect(propertyService.update).toHaveBeenCalledWith(property);
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Property>>();
      const property = new Property();
      jest.spyOn(propertyService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ property });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: property }));
      saveSubject.complete();

      // THEN
      expect(propertyService.create).toHaveBeenCalledWith(property);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Property>>();
      const property = { id: 123 };
      jest.spyOn(propertyService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ property });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(propertyService.update).toHaveBeenCalledWith(property);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Tracking relationships identifiers', () => {
    describe('trackAddressById', () => {
      it('Should return tracked Address primary key', () => {
        const entity = { id: 123 };
        const trackResult = comp.trackAddressById(0, entity);
        expect(trackResult).toEqual(entity.id);
      });
    });

    describe('trackAccommodationById', () => {
      it('Should return tracked Accommodation primary key', () => {
        const entity = { id: 123 };
        const trackResult = comp.trackAccommodationById(0, entity);
        expect(trackResult).toEqual(entity.id);
      });
    });

    describe('trackProjectById', () => {
      it('Should return tracked Project primary key', () => {
        const entity = { id: 123 };
        const trackResult = comp.trackProjectById(0, entity);
        expect(trackResult).toEqual(entity.id);
      });
    });

    describe('trackLandById', () => {
      it('Should return tracked Land primary key', () => {
        const entity = { id: 123 };
        const trackResult = comp.trackLandById(0, entity);
        expect(trackResult).toEqual(entity.id);
      });
    });
  });
});
