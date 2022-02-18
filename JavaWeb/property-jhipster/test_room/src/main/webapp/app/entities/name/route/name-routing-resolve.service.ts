import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IName, Name } from '../name.model';
import { NameService } from '../service/name.service';

@Injectable({ providedIn: 'root' })
export class NameRoutingResolveService implements Resolve<IName> {
  constructor(protected service: NameService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IName> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((name: HttpResponse<Name>) => {
          if (name.body) {
            return of(name.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Name());
  }
}
