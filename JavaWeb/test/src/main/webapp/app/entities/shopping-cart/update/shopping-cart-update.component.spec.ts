import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { ShoppingCartService } from '../service/shopping-cart.service';
import { IShoppingCart, ShoppingCart } from '../shopping-cart.model';
import { ICustomer } from 'app/entities/customer/customer.model';
import { CustomerService } from 'app/entities/customer/service/customer.service';

import { ShoppingCartUpdateComponent } from './shopping-cart-update.component';

describe('ShoppingCart Management Update Component', () => {
  let comp: ShoppingCartUpdateComponent;
  let fixture: ComponentFixture<ShoppingCartUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let shoppingCartService: ShoppingCartService;
  let customerService: CustomerService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [ShoppingCartUpdateComponent],
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
      .overrideTemplate(ShoppingCartUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(ShoppingCartUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    shoppingCartService = TestBed.inject(ShoppingCartService);
    customerService = TestBed.inject(CustomerService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call Customer query and add missing value', () => {
      const shoppingCart: IShoppingCart = { id: 456 };
      const customer: ICustomer = { id: 93330 };
      shoppingCart.customer = customer;

      const customerCollection: ICustomer[] = [{ id: 86160 }];
      jest.spyOn(customerService, 'query').mockReturnValue(of(new HttpResponse({ body: customerCollection })));
      const additionalCustomers = [customer];
      const expectedCollection: ICustomer[] = [...additionalCustomers, ...customerCollection];
      jest.spyOn(customerService, 'addCustomerToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ shoppingCart });
      comp.ngOnInit();

      expect(customerService.query).toHaveBeenCalled();
      expect(customerService.addCustomerToCollectionIfMissing).toHaveBeenCalledWith(customerCollection, ...additionalCustomers);
      expect(comp.customersSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const shoppingCart: IShoppingCart = { id: 456 };
      const customer: ICustomer = { id: 4684 };
      shoppingCart.customer = customer;

      activatedRoute.data = of({ shoppingCart });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(shoppingCart));
      expect(comp.customersSharedCollection).toContain(customer);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ShoppingCart>>();
      const shoppingCart = { id: 123 };
      jest.spyOn(shoppingCartService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ shoppingCart });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: shoppingCart }));
      saveSubject.complete();

      // THEN
      expect(comp.previousState).toHaveBeenCalled();
      expect(shoppingCartService.update).toHaveBeenCalledWith(shoppingCart);
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ShoppingCart>>();
      const shoppingCart = new ShoppingCart();
      jest.spyOn(shoppingCartService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ shoppingCart });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: shoppingCart }));
      saveSubject.complete();

      // THEN
      expect(shoppingCartService.create).toHaveBeenCalledWith(shoppingCart);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ShoppingCart>>();
      const shoppingCart = { id: 123 };
      jest.spyOn(shoppingCartService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ shoppingCart });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(shoppingCartService.update).toHaveBeenCalledWith(shoppingCart);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Tracking relationships identifiers', () => {
    describe('trackCustomerById', () => {
      it('Should return tracked Customer primary key', () => {
        const entity = { id: 123 };
        const trackResult = comp.trackCustomerById(0, entity);
        expect(trackResult).toEqual(entity.id);
      });
    });
  });
});
