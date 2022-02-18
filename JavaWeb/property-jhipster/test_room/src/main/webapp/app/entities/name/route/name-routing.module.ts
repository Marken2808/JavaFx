import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { NameComponent } from '../list/name.component';
import { NameDetailComponent } from '../detail/name-detail.component';
import { NameUpdateComponent } from '../update/name-update.component';
import { NameRoutingResolveService } from './name-routing-resolve.service';

const nameRoute: Routes = [
  {
    path: '',
    component: NameComponent,
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: NameDetailComponent,
    resolve: {
      name: NameRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: NameUpdateComponent,
    resolve: {
      name: NameRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: NameUpdateComponent,
    resolve: {
      name: NameRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(nameRoute)],
  exports: [RouterModule],
})
export class NameRoutingModule {}
