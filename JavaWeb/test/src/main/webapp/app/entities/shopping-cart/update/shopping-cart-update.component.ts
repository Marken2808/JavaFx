import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import dayjs from 'dayjs/esm';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';

import { IShoppingCart, ShoppingCart } from '../shopping-cart.model';
import { ShoppingCartService } from '../service/shopping-cart.service';
import { ICustomer } from 'app/entities/customer/customer.model';
import { CustomerService } from 'app/entities/customer/service/customer.service';
import { OrderStatus } from 'app/entities/enumerations/order-status.model';
import { PaymentMethod } from 'app/entities/enumerations/payment-method.model';

@Component({
  selector: 'jhi-shopping-cart-update',
  templateUrl: './shopping-cart-update.component.html',
})
export class ShoppingCartUpdateComponent implements OnInit {
  isSaving = false;
  orderStatusValues = Object.keys(OrderStatus);
  paymentMethodValues = Object.keys(PaymentMethod);

  customersSharedCollection: ICustomer[] = [];

  editForm = this.fb.group({
    id: [],
    placedDate: [null, [Validators.required]],
    status: [null, [Validators.required]],
    totalPrice: [null, [Validators.required, Validators.min(0)]],
    paymentMethod: [null, [Validators.required]],
    paymentReference: [],
    customer: [null, Validators.required],
  });

  constructor(
    protected shoppingCartService: ShoppingCartService,
    protected customerService: CustomerService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ shoppingCart }) => {
      if (shoppingCart.id === undefined) {
        const today = dayjs().startOf('day');
        shoppingCart.placedDate = today;
      }

      this.updateForm(shoppingCart);

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const shoppingCart = this.createFromForm();
    if (shoppingCart.id !== undefined) {
      this.subscribeToSaveResponse(this.shoppingCartService.update(shoppingCart));
    } else {
      this.subscribeToSaveResponse(this.shoppingCartService.create(shoppingCart));
    }
  }

  trackCustomerById(index: number, item: ICustomer): number {
    return item.id!;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IShoppingCart>>): void {
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

  protected updateForm(shoppingCart: IShoppingCart): void {
    this.editForm.patchValue({
      id: shoppingCart.id,
      placedDate: shoppingCart.placedDate ? shoppingCart.placedDate.format(DATE_TIME_FORMAT) : null,
      status: shoppingCart.status,
      totalPrice: shoppingCart.totalPrice,
      paymentMethod: shoppingCart.paymentMethod,
      paymentReference: shoppingCart.paymentReference,
      customer: shoppingCart.customer,
    });

    this.customersSharedCollection = this.customerService.addCustomerToCollectionIfMissing(
      this.customersSharedCollection,
      shoppingCart.customer
    );
  }

  protected loadRelationshipsOptions(): void {
    this.customerService
      .query()
      .pipe(map((res: HttpResponse<ICustomer[]>) => res.body ?? []))
      .pipe(
        map((customers: ICustomer[]) =>
          this.customerService.addCustomerToCollectionIfMissing(customers, this.editForm.get('customer')!.value)
        )
      )
      .subscribe((customers: ICustomer[]) => (this.customersSharedCollection = customers));
  }

  protected createFromForm(): IShoppingCart {
    return {
      ...new ShoppingCart(),
      id: this.editForm.get(['id'])!.value,
      placedDate: this.editForm.get(['placedDate'])!.value ? dayjs(this.editForm.get(['placedDate'])!.value, DATE_TIME_FORMAT) : undefined,
      status: this.editForm.get(['status'])!.value,
      totalPrice: this.editForm.get(['totalPrice'])!.value,
      paymentMethod: this.editForm.get(['paymentMethod'])!.value,
      paymentReference: this.editForm.get(['paymentReference'])!.value,
      customer: this.editForm.get(['customer'])!.value,
    };
  }
}
