import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { NameComponent } from './list/name.component';
import { NameDetailComponent } from './detail/name-detail.component';
import { NameUpdateComponent } from './update/name-update.component';
import { NameDeleteDialogComponent } from './delete/name-delete-dialog.component';
import { NameRoutingModule } from './route/name-routing.module';

@NgModule({
  imports: [SharedModule, NameRoutingModule],
  declarations: [NameComponent, NameDetailComponent, NameUpdateComponent, NameDeleteDialogComponent],
  entryComponents: [NameDeleteDialogComponent],
})
export class NameModule {}
