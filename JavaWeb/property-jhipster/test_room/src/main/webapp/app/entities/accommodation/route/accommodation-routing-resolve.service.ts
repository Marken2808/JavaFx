import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IAccommodation, Accommodation } from '../accommodation.model';
import { AccommodationService } from '../service/accommodation.service';

@Injectable({ providedIn: 'root' })
export class AccommodationRoutingResolveService implements Resolve<IAccommodation> {
  constructor(protected service: AccommodationService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IAccommodation> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((accommodation: HttpResponse<Accommodation>) => {
          if (accommodation.body) {
            return of(accommodation.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Accommodation());
  }
}
