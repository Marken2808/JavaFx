import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { AccommodationComponent } from '../list/accommodation.component';
import { AccommodationDetailComponent } from '../detail/accommodation-detail.component';
import { AccommodationUpdateComponent } from '../update/accommodation-update.component';
import { AccommodationRoutingResolveService } from './accommodation-routing-resolve.service';

const accommodationRoute: Routes = [
  {
    path: '',
    component: AccommodationComponent,
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: AccommodationDetailComponent,
    resolve: {
      accommodation: AccommodationRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: AccommodationUpdateComponent,
    resolve: {
      accommodation: AccommodationRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: AccommodationUpdateComponent,
    resolve: {
      accommodation: AccommodationRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(accommodationRoute)],
  exports: [RouterModule],
})
export class AccommodationRoutingModule {}
