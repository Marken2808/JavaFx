import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'category',
        data: { pageTitle: 'jhipsterApp.category.home.title' },
        loadChildren: () => import('./category/category.module').then(m => m.CategoryModule),
      },
      {
        path: 'product',
        data: { pageTitle: 'jhipsterApp.product.home.title' },
        loadChildren: () => import('./product/product.module').then(m => m.ProductModule),
      },
      {
        path: 'order',
        data: { pageTitle: 'jhipsterApp.order.home.title' },
        loadChildren: () => import('./order/order.module').then(m => m.OrderModule),
      },
      {
        path: 'customer',
        data: { pageTitle: 'jhipsterApp.customer.home.title' },
        loadChildren: () => import('./customer/customer.module').then(m => m.CustomerModule),
      },
      {
        path: 'address',
        data: { pageTitle: 'jhipsterApp.address.home.title' },
        loadChildren: () => import('./address/address.module').then(m => m.AddressModule),
      },
      {
        path: 'wish-list',
        data: { pageTitle: 'jhipsterApp.wishList.home.title' },
        loadChildren: () => import('./wish-list/wish-list.module').then(m => m.WishListModule),
      },
      {
        path: 'cart',
        data: { pageTitle: 'jhipsterApp.cart.home.title' },
        loadChildren: () => import('./cart/cart.module').then(m => m.CartModule),
      },
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ]),
  ],
})
export class EntityRoutingModule {}
