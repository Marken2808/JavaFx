import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { LandService } from '../service/land.service';
import { ILand, Land } from '../land.model';
import { IProperty } from 'app/entities/property/property.model';
import { PropertyService } from 'app/entities/property/service/property.service';

import { LandUpdateComponent } from './land-update.component';

describe('Land Management Update Component', () => {
  let comp: LandUpdateComponent;
  let fixture: ComponentFixture<LandUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let landService: LandService;
  let propertyService: PropertyService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [LandUpdateComponent],
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
      .overrideTemplate(LandUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(LandUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    landService = TestBed.inject(LandService);
    propertyService = TestBed.inject(PropertyService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call property query and add missing value', () => {
      const land: ILand = { id: 456 };
      const property: IProperty = { id: 57993 };
      land.property = property;

      const propertyCollection: IProperty[] = [{ id: 6561 }];
      jest.spyOn(propertyService, 'query').mockReturnValue(of(new HttpResponse({ body: propertyCollection })));
      const expectedCollection: IProperty[] = [property, ...propertyCollection];
      jest.spyOn(propertyService, 'addPropertyToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ land });
      comp.ngOnInit();

      expect(propertyService.query).toHaveBeenCalled();
      expect(propertyService.addPropertyToCollectionIfMissing).toHaveBeenCalledWith(propertyCollection, property);
      expect(comp.propertiesCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const land: ILand = { id: 456 };
      const property: IProperty = { id: 89662 };
      land.property = property;

      activatedRoute.data = of({ land });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(land));
      expect(comp.propertiesCollection).toContain(property);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Land>>();
      const land = { id: 123 };
      jest.spyOn(landService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ land });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: land }));
      saveSubject.complete();

      // THEN
      expect(comp.previousState).toHaveBeenCalled();
      expect(landService.update).toHaveBeenCalledWith(land);
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Land>>();
      const land = new Land();
      jest.spyOn(landService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ land });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: land }));
      saveSubject.complete();

      // THEN
      expect(landService.create).toHaveBeenCalledWith(land);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Land>>();
      const land = { id: 123 };
      jest.spyOn(landService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ land });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(landService.update).toHaveBeenCalledWith(land);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Tracking relationships identifiers', () => {
    describe('trackPropertyById', () => {
      it('Should return tracked Property primary key', () => {
        const entity = { id: 123 };
        const trackResult = comp.trackPropertyById(0, entity);
        expect(trackResult).toEqual(entity.id);
      });
    });
  });
});
