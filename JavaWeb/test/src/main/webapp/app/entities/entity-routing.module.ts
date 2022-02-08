import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'category',
        data: { pageTitle: 'Categories' },
        loadChildren: () => import('./category/category.module').then(m => m.CategoryModule),
      },
      {
        path: 'product',
        data: { pageTitle: 'Products' },
        loadChildren: () => import('./product/product.module').then(m => m.ProductModule),
      },
      {
        path: 'product-order',
        data: { pageTitle: 'ProductOrders' },
        loadChildren: () => import('./product-order/product-order.module').then(m => m.ProductOrderModule),
      },
      {
        path: 'customer',
        data: { pageTitle: 'Customers' },
        loadChildren: () => import('./customer/customer.module').then(m => m.CustomerModule),
      },
      {
        path: 'address',
        data: { pageTitle: 'Addresses' },
        loadChildren: () => import('./address/address.module').then(m => m.AddressModule),
      },
      {
        path: 'wish-list',
        data: { pageTitle: 'WishLists' },
        loadChildren: () => import('./wish-list/wish-list.module').then(m => m.WishListModule),
      },
      {
        path: 'shopping-cart',
        data: { pageTitle: 'ShoppingCarts' },
        loadChildren: () => import('./shopping-cart/shopping-cart.module').then(m => m.ShoppingCartModule),
      },
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ]),
  ],
})
export class EntityRoutingModule {}
