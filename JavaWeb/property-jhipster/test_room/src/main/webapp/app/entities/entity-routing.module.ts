import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'name',
        data: { pageTitle: 'testRoomApp.name.home.title' },
        loadChildren: () => import('./name/name.module').then(m => m.NameModule),
      },
      {
        path: 'address',
        data: { pageTitle: 'testRoomApp.address.home.title' },
        loadChildren: () => import('./address/address.module').then(m => m.AddressModule),
      },
      {
        path: 'customer',
        data: { pageTitle: 'testRoomApp.customer.home.title' },
        loadChildren: () => import('./customer/customer.module').then(m => m.CustomerModule),
      },
      {
        path: 'property',
        data: { pageTitle: 'testRoomApp.property.home.title' },
        loadChildren: () => import('./property/property.module').then(m => m.PropertyModule),
      },
      {
        path: 'accommodation',
        data: { pageTitle: 'testRoomApp.accommodation.home.title' },
        loadChildren: () => import('./accommodation/accommodation.module').then(m => m.AccommodationModule),
      },
      {
        path: 'room',
        data: { pageTitle: 'testRoomApp.room.home.title' },
        loadChildren: () => import('./room/room.module').then(m => m.RoomModule),
      },
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ]),
  ],
})
export class EntityRoutingModule {}
