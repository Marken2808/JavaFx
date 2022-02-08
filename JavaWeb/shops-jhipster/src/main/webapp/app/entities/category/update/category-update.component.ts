import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { ICategory, Category } from '../category.model';
import { CategoryService } from '../service/category.service';
import { IProduct } from 'app/entities/product/product.model';
import { ProductService } from 'app/entities/product/service/product.service';
import { CategoryStatus } from 'app/entities/enumerations/category-status.model';

@Component({
  selector: 'jhi-category-update',
  templateUrl: './category-update.component.html',
})
export class CategoryUpdateComponent implements OnInit {
  isSaving = false;
  categoryStatusValues = Object.keys(CategoryStatus);

  categoriesSharedCollection: ICategory[] = [];
  productsSharedCollection: IProduct[] = [];

  editForm = this.fb.group({
    id: [],
    description: [null, [Validators.required]],
    sortOrder: [],
    dateAdded: [],
    dateModified: [],
    status: [],
    parent: [],
    products: [],
  });

  constructor(
    protected categoryService: CategoryService,
    protected productService: ProductService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ category }) => {
      this.updateForm(category);

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const category = this.createFromForm();
    if (category.id !== undefined) {
      this.subscribeToSaveResponse(this.categoryService.update(category));
    } else {
      this.subscribeToSaveResponse(this.categoryService.create(category));
    }
  }

  trackCategoryById(index: number, item: ICategory): number {
    return item.id!;
  }

  trackProductById(index: number, item: IProduct): number {
    return item.id!;
  }

  getSelectedProduct(option: IProduct, selectedVals?: IProduct[]): IProduct {
    if (selectedVals) {
      for (const selectedVal of selectedVals) {
        if (option.id === selectedVal.id) {
          return selectedVal;
        }
      }
    }
    return option;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ICategory>>): void {
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

  protected updateForm(category: ICategory): void {
    this.editForm.patchValue({
      id: category.id,
      description: category.description,
      sortOrder: category.sortOrder,
      dateAdded: category.dateAdded,
      dateModified: category.dateModified,
      status: category.status,
      parent: category.parent,
      products: category.products,
    });

    this.categoriesSharedCollection = this.categoryService.addCategoryToCollectionIfMissing(
      this.categoriesSharedCollection,
      category.parent
    );
    this.productsSharedCollection = this.productService.addProductToCollectionIfMissing(
      this.productsSharedCollection,
      ...(category.products ?? [])
    );
  }

  protected loadRelationshipsOptions(): void {
    this.categoryService
      .query()
      .pipe(map((res: HttpResponse<ICategory[]>) => res.body ?? []))
      .pipe(
        map((categories: ICategory[]) =>
          this.categoryService.addCategoryToCollectionIfMissing(categories, this.editForm.get('parent')!.value)
        )
      )
      .subscribe((categories: ICategory[]) => (this.categoriesSharedCollection = categories));

    this.productService
      .query()
      .pipe(map((res: HttpResponse<IProduct[]>) => res.body ?? []))
      .pipe(
        map((products: IProduct[]) =>
          this.productService.addProductToCollectionIfMissing(products, ...(this.editForm.get('products')!.value ?? []))
        )
      )
      .subscribe((products: IProduct[]) => (this.productsSharedCollection = products));
  }

  protected createFromForm(): ICategory {
    return {
      ...new Category(),
      id: this.editForm.get(['id'])!.value,
      description: this.editForm.get(['description'])!.value,
      sortOrder: this.editForm.get(['sortOrder'])!.value,
      dateAdded: this.editForm.get(['dateAdded'])!.value,
      dateModified: this.editForm.get(['dateModified'])!.value,
      status: this.editForm.get(['status'])!.value,
      parent: this.editForm.get(['parent'])!.value,
      products: this.editForm.get(['products'])!.value,
    };
  }
}
