import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { AccommodationType } from 'app/entities/enumerations/accommodation-type.model';
import { AccommodationStatus } from 'app/entities/enumerations/accommodation-status.model';
import { IAccommodation, Accommodation } from '../accommodation.model';

import { AccommodationService } from './accommodation.service';

describe('Accommodation Service', () => {
  let service: AccommodationService;
  let httpMock: HttpTestingController;
  let elemDefault: IAccommodation;
  let expectedResult: IAccommodation | IAccommodation[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(AccommodationService);
    httpMock = TestBed.inject(HttpTestingController);

    elemDefault = {
      id: 0,
      title: 'AAAAAAA',
      type: AccommodationType.Flat,
      status: AccommodationStatus.Furnished,
      total: 0,
    };
  });

  describe('Service methods', () => {
    it('should find an element', () => {
      const returnedFromService = Object.assign({}, elemDefault);

      service.find(123).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(elemDefault);
    });

    it('should create a Accommodation', () => {
      const returnedFromService = Object.assign(
        {
          id: 0,
        },
        elemDefault
      );

      const expected = Object.assign({}, returnedFromService);

      service.create(new Accommodation()).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a Accommodation', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          title: 'BBBBBB',
          type: 'BBBBBB',
          status: 'BBBBBB',
          total: 1,
        },
        elemDefault
      );

      const expected = Object.assign({}, returnedFromService);

      service.update(expected).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a Accommodation', () => {
      const patchObject = Object.assign(
        {
          type: 'BBBBBB',
          status: 'BBBBBB',
          total: 1,
        },
        new Accommodation()
      );

      const returnedFromService = Object.assign(patchObject, elemDefault);

      const expected = Object.assign({}, returnedFromService);

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of Accommodation', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          title: 'BBBBBB',
          type: 'BBBBBB',
          status: 'BBBBBB',
          total: 1,
        },
        elemDefault
      );

      const expected = Object.assign({}, returnedFromService);

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toContainEqual(expected);
    });

    it('should delete a Accommodation', () => {
      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult);
    });

    describe('addAccommodationToCollectionIfMissing', () => {
      it('should add a Accommodation to an empty array', () => {
        const accommodation: IAccommodation = { id: 123 };
        expectedResult = service.addAccommodationToCollectionIfMissing([], accommodation);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(accommodation);
      });

      it('should not add a Accommodation to an array that contains it', () => {
        const accommodation: IAccommodation = { id: 123 };
        const accommodationCollection: IAccommodation[] = [
          {
            ...accommodation,
          },
          { id: 456 },
        ];
        expectedResult = service.addAccommodationToCollectionIfMissing(accommodationCollection, accommodation);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a Accommodation to an array that doesn't contain it", () => {
        const accommodation: IAccommodation = { id: 123 };
        const accommodationCollection: IAccommodation[] = [{ id: 456 }];
        expectedResult = service.addAccommodationToCollectionIfMissing(accommodationCollection, accommodation);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(accommodation);
      });

      it('should add only unique Accommodation to an array', () => {
        const accommodationArray: IAccommodation[] = [{ id: 123 }, { id: 456 }, { id: 13779 }];
        const accommodationCollection: IAccommodation[] = [{ id: 123 }];
        expectedResult = service.addAccommodationToCollectionIfMissing(accommodationCollection, ...accommodationArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const accommodation: IAccommodation = { id: 123 };
        const accommodation2: IAccommodation = { id: 456 };
        expectedResult = service.addAccommodationToCollectionIfMissing([], accommodation, accommodation2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(accommodation);
        expect(expectedResult).toContain(accommodation2);
      });

      it('should accept null and undefined values', () => {
        const accommodation: IAccommodation = { id: 123 };
        expectedResult = service.addAccommodationToCollectionIfMissing([], null, accommodation, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(accommodation);
      });

      it('should return initial array if no Accommodation is added', () => {
        const accommodationCollection: IAccommodation[] = [{ id: 123 }];
        expectedResult = service.addAccommodationToCollectionIfMissing(accommodationCollection, undefined, null);
        expect(expectedResult).toEqual(accommodationCollection);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
