import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { AccommodationComponent } from './list/accommodation.component';
import { AccommodationDetailComponent } from './detail/accommodation-detail.component';
import { AccommodationUpdateComponent } from './update/accommodation-update.component';
import { AccommodationDeleteDialogComponent } from './delete/accommodation-delete-dialog.component';
import { AccommodationRoutingModule } from './route/accommodation-routing.module';

@NgModule({
  imports: [SharedModule, AccommodationRoutingModule],
  declarations: [AccommodationComponent, AccommodationDetailComponent, AccommodationUpdateComponent, AccommodationDeleteDialogComponent],
  entryComponents: [AccommodationDeleteDialogComponent],
})
export class AccommodationModule {}
