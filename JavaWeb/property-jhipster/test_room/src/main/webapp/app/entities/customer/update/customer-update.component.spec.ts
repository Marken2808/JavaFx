import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { CustomerService } from '../service/customer.service';
import { ICustomer, Customer } from '../customer.model';

import { IUser } from 'app/entities/user/user.model';
import { UserService } from 'app/entities/user/user.service';
import { IName } from 'app/entities/name/name.model';
import { NameService } from 'app/entities/name/service/name.service';
import { IProperty } from 'app/entities/property/property.model';
import { PropertyService } from 'app/entities/property/service/property.service';

import { CustomerUpdateComponent } from './customer-update.component';

describe('Customer Management Update Component', () => {
  let comp: CustomerUpdateComponent;
  let fixture: ComponentFixture<CustomerUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let customerService: CustomerService;
  let userService: UserService;
  let nameService: NameService;
  let propertyService: PropertyService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [CustomerUpdateComponent],
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
      .overrideTemplate(CustomerUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(CustomerUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    customerService = TestBed.inject(CustomerService);
    userService = TestBed.inject(UserService);
    nameService = TestBed.inject(NameService);
    propertyService = TestBed.inject(PropertyService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call User query and add missing value', () => {
      const customer: ICustomer = { id: 456 };
      const user: IUser = { id: 67969 };
      customer.user = user;

      const userCollection: IUser[] = [{ id: 33034 }];
      jest.spyOn(userService, 'query').mockReturnValue(of(new HttpResponse({ body: userCollection })));
      const additionalUsers = [user];
      const expectedCollection: IUser[] = [...additionalUsers, ...userCollection];
      jest.spyOn(userService, 'addUserToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ customer });
      comp.ngOnInit();

      expect(userService.query).toHaveBeenCalled();
      expect(userService.addUserToCollectionIfMissing).toHaveBeenCalledWith(userCollection, ...additionalUsers);
      expect(comp.usersSharedCollection).toEqual(expectedCollection);
    });

    it('Should call Name query and add missing value', () => {
      const customer: ICustomer = { id: 456 };
      const name: IName = { id: 16794 };
      customer.name = name;

      const nameCollection: IName[] = [{ id: 36746 }];
      jest.spyOn(nameService, 'query').mockReturnValue(of(new HttpResponse({ body: nameCollection })));
      const additionalNames = [name];
      const expectedCollection: IName[] = [...additionalNames, ...nameCollection];
      jest.spyOn(nameService, 'addNameToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ customer });
      comp.ngOnInit();

      expect(nameService.query).toHaveBeenCalled();
      expect(nameService.addNameToCollectionIfMissing).toHaveBeenCalledWith(nameCollection, ...additionalNames);
      expect(comp.namesSharedCollection).toEqual(expectedCollection);
    });

    it('Should call Property query and add missing value', () => {
      const customer: ICustomer = { id: 456 };
      const property: IProperty = { id: 7205 };
      customer.property = property;

      const propertyCollection: IProperty[] = [{ id: 65575 }];
      jest.spyOn(propertyService, 'query').mockReturnValue(of(new HttpResponse({ body: propertyCollection })));
      const additionalProperties = [property];
      const expectedCollection: IProperty[] = [...additionalProperties, ...propertyCollection];
      jest.spyOn(propertyService, 'addPropertyToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ customer });
      comp.ngOnInit();

      expect(propertyService.query).toHaveBeenCalled();
      expect(propertyService.addPropertyToCollectionIfMissing).toHaveBeenCalledWith(propertyCollection, ...additionalProperties);
      expect(comp.propertiesSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const customer: ICustomer = { id: 456 };
      const user: IUser = { id: 90005 };
      customer.user = user;
      const name: IName = { id: 31146 };
      customer.name = name;
      const property: IProperty = { id: 55436 };
      customer.property = property;

      activatedRoute.data = of({ customer });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(customer));
      expect(comp.usersSharedCollection).toContain(user);
      expect(comp.namesSharedCollection).toContain(name);
      expect(comp.propertiesSharedCollection).toContain(property);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Customer>>();
      const customer = { id: 123 };
      jest.spyOn(customerService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ customer });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: customer }));
      saveSubject.complete();

      // THEN
      expect(comp.previousState).toHaveBeenCalled();
      expect(customerService.update).toHaveBeenCalledWith(customer);
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Customer>>();
      const customer = new Customer();
      jest.spyOn(customerService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ customer });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: customer }));
      saveSubject.complete();

      // THEN
      expect(customerService.create).toHaveBeenCalledWith(customer);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Customer>>();
      const customer = { id: 123 };
      jest.spyOn(customerService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ customer });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(customerService.update).toHaveBeenCalledWith(customer);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Tracking relationships identifiers', () => {
    describe('trackUserById', () => {
      it('Should return tracked User primary key', () => {
        const entity = { id: 123 };
        const trackResult = comp.trackUserById(0, entity);
        expect(trackResult).toEqual(entity.id);
      });
    });

    describe('trackNameById', () => {
      it('Should return tracked Name primary key', () => {
        const entity = { id: 123 };
        const trackResult = comp.trackNameById(0, entity);
        expect(trackResult).toEqual(entity.id);
      });
    });

    describe('trackPropertyById', () => {
      it('Should return tracked Property primary key', () => {
        const entity = { id: 123 };
        const trackResult = comp.trackPropertyById(0, entity);
        expect(trackResult).toEqual(entity.id);
      });
    });
  });
});
