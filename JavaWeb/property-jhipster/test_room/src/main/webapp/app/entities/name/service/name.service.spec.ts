import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { Title } from 'app/entities/enumerations/title.model';
import { IName, Name } from '../name.model';

import { NameService } from './name.service';

describe('Name Service', () => {
  let service: NameService;
  let httpMock: HttpTestingController;
  let elemDefault: IName;
  let expectedResult: IName | IName[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(NameService);
    httpMock = TestBed.inject(HttpTestingController);

    elemDefault = {
      id: 0,
      title: Title.Mr,
      firstName: 'AAAAAAA',
      middleName: 'AAAAAAA',
      lastName: 'AAAAAAA',
      displayName: 'AAAAAAA',
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

    it('should create a Name', () => {
      const returnedFromService = Object.assign(
        {
          id: 0,
        },
        elemDefault
      );

      const expected = Object.assign({}, returnedFromService);

      service.create(new Name()).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a Name', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          title: 'BBBBBB',
          firstName: 'BBBBBB',
          middleName: 'BBBBBB',
          lastName: 'BBBBBB',
          displayName: 'BBBBBB',
        },
        elemDefault
      );

      const expected = Object.assign({}, returnedFromService);

      service.update(expected).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a Name', () => {
      const patchObject = Object.assign(
        {
          title: 'BBBBBB',
          firstName: 'BBBBBB',
          middleName: 'BBBBBB',
          lastName: 'BBBBBB',
        },
        new Name()
      );

      const returnedFromService = Object.assign(patchObject, elemDefault);

      const expected = Object.assign({}, returnedFromService);

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of Name', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          title: 'BBBBBB',
          firstName: 'BBBBBB',
          middleName: 'BBBBBB',
          lastName: 'BBBBBB',
          displayName: 'BBBBBB',
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

    it('should delete a Name', () => {
      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult);
    });

    describe('addNameToCollectionIfMissing', () => {
      it('should add a Name to an empty array', () => {
        const name: IName = { id: 123 };
        expectedResult = service.addNameToCollectionIfMissing([], name);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(name);
      });

      it('should not add a Name to an array that contains it', () => {
        const name: IName = { id: 123 };
        const nameCollection: IName[] = [
          {
            ...name,
          },
          { id: 456 },
        ];
        expectedResult = service.addNameToCollectionIfMissing(nameCollection, name);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a Name to an array that doesn't contain it", () => {
        const name: IName = { id: 123 };
        const nameCollection: IName[] = [{ id: 456 }];
        expectedResult = service.addNameToCollectionIfMissing(nameCollection, name);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(name);
      });

      it('should add only unique Name to an array', () => {
        const nameArray: IName[] = [{ id: 123 }, { id: 456 }, { id: 42145 }];
        const nameCollection: IName[] = [{ id: 123 }];
        expectedResult = service.addNameToCollectionIfMissing(nameCollection, ...nameArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const name: IName = { id: 123 };
        const name2: IName = { id: 456 };
        expectedResult = service.addNameToCollectionIfMissing([], name, name2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(name);
        expect(expectedResult).toContain(name2);
      });

      it('should accept null and undefined values', () => {
        const name: IName = { id: 123 };
        expectedResult = service.addNameToCollectionIfMissing([], null, name, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(name);
      });

      it('should return initial array if no Name is added', () => {
        const nameCollection: IName[] = [{ id: 123 }];
        expectedResult = service.addNameToCollectionIfMissing(nameCollection, undefined, null);
        expect(expectedResult).toEqual(nameCollection);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
