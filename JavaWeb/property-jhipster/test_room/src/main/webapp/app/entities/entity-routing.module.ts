import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'address',
        data: { pageTitle: 'testRoomApp.address.home.title' },
        loadChildren: () => import('./address/address.module').then(m => m.AddressModule),
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
      {
        path: 'project',
        data: { pageTitle: 'testRoomApp.project.home.title' },
        loadChildren: () => import('./project/project.module').then(m => m.ProjectModule),
      },
      {
        path: 'land',
        data: { pageTitle: 'testRoomApp.land.home.title' },
        loadChildren: () => import('./land/land.module').then(m => m.LandModule),
      },
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ]),
  ],
})
export class EntityRoutingModule {}
