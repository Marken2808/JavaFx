import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { AccommodationService } from '../service/accommodation.service';
import { IAccommodation, Accommodation } from '../accommodation.model';
import { IRoom } from 'app/entities/room/room.model';
import { RoomService } from 'app/entities/room/service/room.service';

import { AccommodationUpdateComponent } from './accommodation-update.component';

describe('Accommodation Management Update Component', () => {
  let comp: AccommodationUpdateComponent;
  let fixture: ComponentFixture<AccommodationUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let accommodationService: AccommodationService;
  let roomService: RoomService;

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
    roomService = TestBed.inject(RoomService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call Room query and add missing value', () => {
      const accommodation: IAccommodation = { id: 456 };
      const rooms: IRoom[] = [{ id: 54158 }];
      accommodation.rooms = rooms;

      const roomCollection: IRoom[] = [{ id: 48469 }];
      jest.spyOn(roomService, 'query').mockReturnValue(of(new HttpResponse({ body: roomCollection })));
      const additionalRooms = [...rooms];
      const expectedCollection: IRoom[] = [...additionalRooms, ...roomCollection];
      jest.spyOn(roomService, 'addRoomToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ accommodation });
      comp.ngOnInit();

      expect(roomService.query).toHaveBeenCalled();
      expect(roomService.addRoomToCollectionIfMissing).toHaveBeenCalledWith(roomCollection, ...additionalRooms);
      expect(comp.roomsSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const accommodation: IAccommodation = { id: 456 };
      const rooms: IRoom = { id: 40475 };
      accommodation.rooms = [rooms];

      activatedRoute.data = of({ accommodation });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(accommodation));
      expect(comp.roomsSharedCollection).toContain(rooms);
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

  describe('Tracking relationships identifiers', () => {
    describe('trackRoomById', () => {
      it('Should return tracked Room primary key', () => {
        const entity = { id: 123 };
        const trackResult = comp.trackRoomById(0, entity);
        expect(trackResult).toEqual(entity.id);
      });
    });
  });

  describe('Getting selected relationships', () => {
    describe('getSelectedRoom', () => {
      it('Should return option if no Room is selected', () => {
        const option = { id: 123 };
        const result = comp.getSelectedRoom(option);
        expect(result === option).toEqual(true);
      });

      it('Should return selected Room for according option', () => {
        const option = { id: 123 };
        const selected = { id: 123 };
        const selected2 = { id: 456 };
        const result = comp.getSelectedRoom(option, [selected2, selected]);
        expect(result === selected).toEqual(true);
        expect(result === selected2).toEqual(false);
        expect(result === option).toEqual(false);
      });

      it('Should return option if this Room is not selected', () => {
        const option = { id: 123 };
        const selected = { id: 456 };
        const result = comp.getSelectedRoom(option, [selected]);
        expect(result === option).toEqual(true);
        expect(result === selected).toEqual(false);
      });
    });
  });
});
