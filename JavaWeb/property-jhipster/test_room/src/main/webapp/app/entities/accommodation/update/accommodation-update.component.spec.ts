import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { AccommodationService } from '../service/accommodation.service';
import { IAccommodation, Accommodation } from '../accommodation.model';

import { AccommodationUpdateComponent } from './accommodation-update.component';

describe('Accommodation Management Update Component', () => {
  let comp: AccommodationUpdateComponent;
  let fixture: ComponentFixture<AccommodationUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let accommodationService: AccommodationService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [AccommodationUpdateComponent],
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
      .overrideTemplate(AccommodationUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(AccommodationUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    accommodationService = TestBed.inject(AccommodationService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const accommodation: IAccommodation = { id: 456 };

      activatedRoute.data = of({ accommodation });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(accommodation));
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Accommodation>>();
      const accommodation = { id: 123 };
      jest.spyOn(accommodationService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ accommodation });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: accommodation }));
      saveSubject.complete();

      // THEN
      expect(comp.previousState).toHaveBeenCalled();
      expect(accommodationService.update).toHaveBeenCalledWith(accommodation);
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Accommodation>>();
      const accommodation = new Accommodation();
      jest.spyOn(accommodationService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ accommodation });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: accommodation }));
      saveSubject.complete();

      // THEN
      expect(accommodationService.create).toHaveBeenCalledWith(accommodation);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Accommodation>>();
      const accommodation = { id: 123 };
      jest.spyOn(accommodationService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ accommodation });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(accommodationService.update).toHaveBeenCalledWith(accommodation);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
