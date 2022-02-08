import { Component, OnInit, ElementRef } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { IProduct, Product } from '../product.model';
import { ProductService } from '../service/product.service';
import { AlertError } from 'app/shared/alert/alert-error.model';
import { EventManager, EventWithContent } from 'app/core/util/event-manager.service';
import { DataUtils, FileLoadError } from 'app/core/util/data-util.service';
import { IWishList } from 'app/entities/wish-list/wish-list.model';
import { WishListService } from 'app/entities/wish-list/service/wish-list.service';
import { Size } from 'app/entities/enumerations/size.model';

@Component({
  selector: 'jhi-product-update',
  templateUrl: './product-update.component.html',
})
export class ProductUpdateComponent implements OnInit {
  isSaving = false;
  sizeValues = Object.keys(Size);

  wishListsSharedCollection: IWishList[] = [];

  editForm = this.fb.group({
    id: [],
    title: [null, [Validators.required]],
    keywords: [],
    description: [],
    price: [null, [Validators.required, Validators.min(0)]],
    productSize: [null, [Validators.required]],
    image: [],
    imageContentType: [],
    rating: [],
    dateAdded: [],
    dateModified: [],
    wishList: [null, Validators.required],
  });

  constructor(
    protected dataUtils: DataUtils,
    protected eventManager: EventManager,
    protected productService: ProductService,
    protected wishListService: WishListService,
    protected elementRef: ElementRef,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ product }) => {
      this.updateForm(product);

      this.loadRelationshipsOptions();
    });
  }

  byteSize(base64String: string): string {
    return this.dataUtils.byteSize(base64String);
  }

  openFile(base64String: string, contentType: string | null | undefined): void {
    this.dataUtils.openFile(base64String, contentType);
  }

  setFileData(event: Event, field: string, isImage: boolean): void {
    this.dataUtils.loadFileToForm(event, this.editForm, field, isImage).subscribe({
      error: (err: FileLoadError) =>
        this.eventManager.broadcast(new EventWithContent<AlertError>('testApp.error', { message: err.message })),
    });
  }

  clearInputImage(field: string, fieldContentType: string, idInput: string): void {
    this.editForm.patchValue({
      [field]: null,
      [fieldContentType]: null,
    });
    if (idInput && this.elementRef.nativeElement.querySelector('#' + idInput)) {
      this.elementRef.nativeElement.querySelector('#' + idInput).value = null;
    }
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const product = this.createFromForm();
    if (product.id !== undefined) {
      this.subscribeToSaveResponse(this.productService.update(product));
    } else {
      this.subscribeToSaveResponse(this.productService.create(product));
    }
  }

  trackWishListById(index: number, item: IWishList): number {
    return item.id!;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IProduct>>): void {
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

  protected updateForm(product: IProduct): void {
    this.editForm.patchValue({
      id: product.id,
      title: product.title,
      keywords: product.keywords,
      description: product.description,
      price: product.price,
      productSize: product.productSize,
      image: product.image,
      imageContentType: product.imageContentType,
      rating: product.rating,
      dateAdded: product.dateAdded,
      dateModified: product.dateModified,
      wishList: product.wishList,
    });

    this.wishListsSharedCollection = this.wishListService.addWishListToCollectionIfMissing(
      this.wishListsSharedCollection,
      product.wishList
    );
  }

  protected loadRelationshipsOptions(): void {
    this.wishListService
      .query()
      .pipe(map((res: HttpResponse<IWishList[]>) => res.body ?? []))
      .pipe(
        map((wishLists: IWishList[]) =>
          this.wishListService.addWishListToCollectionIfMissing(wishLists, this.editForm.get('wishList')!.value)
        )
      )
      .subscribe((wishLists: IWishList[]) => (this.wishListsSharedCollection = wishLists));
  }

  protected createFromForm(): IProduct {
    return {
      ...new Product(),
      id: this.editForm.get(['id'])!.value,
      title: this.editForm.get(['title'])!.value,
      keywords: this.editForm.get(['keywords'])!.value,
      description: this.editForm.get(['description'])!.value,
      price: this.editForm.get(['price'])!.value,
      productSize: this.editForm.get(['productSize'])!.value,
      imageContentType: this.editForm.get(['imageContentType'])!.value,
      image: this.editForm.get(['image'])!.value,
      rating: this.editForm.get(['rating'])!.value,
      dateAdded: this.editForm.get(['dateAdded'])!.value,
      dateModified: this.editForm.get(['dateModified'])!.value,
      wishList: this.editForm.get(['wishList'])!.value,
    };
  }
}
