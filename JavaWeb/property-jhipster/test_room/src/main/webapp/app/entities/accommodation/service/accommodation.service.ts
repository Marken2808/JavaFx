import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IAccommodation, getAccommodationIdentifier } from '../accommodation.model';

export type EntityResponseType = HttpResponse<IAccommodation>;
export type EntityArrayResponseType = HttpResponse<IAccommodation[]>;

@Injectable({ providedIn: 'root' })
export class AccommodationService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/accommodations');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(accommodation: IAccommodation): Observable<EntityResponseType> {
    return this.http.post<IAccommodation>(this.resourceUrl, accommodation, { observe: 'response' });
  }

  update(accommodation: IAccommodation): Observable<EntityResponseType> {
    return this.http.put<IAccommodation>(`${this.resourceUrl}/${getAccommodationIdentifier(accommodation) as number}`, accommodation, {
      observe: 'response',
    });
  }

  partialUpdate(accommodation: IAccommodation): Observable<EntityResponseType> {
    return this.http.patch<IAccommodation>(`${this.resourceUrl}/${getAccommodationIdentifier(accommodation) as number}`, accommodation, {
      observe: 'response',
    });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IAccommodation>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IAccommodation[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addAccommodationToCollectionIfMissing(
    accommodationCollection: IAccommodation[],
    ...accommodationsToCheck: (IAccommodation | null | undefined)[]
  ): IAccommodation[] {
    const accommodations: IAccommodation[] = accommodationsToCheck.filter(isPresent);
    if (accommodations.length > 0) {
      const accommodationCollectionIdentifiers = accommodationCollection.map(
        accommodationItem => getAccommodationIdentifier(accommodationItem)!
      );
      const accommodationsToAdd = accommodations.filter(accommodationItem => {
        const accommodationIdentifier = getAccommodationIdentifier(accommodationItem);
        if (accommodationIdentifier == null || accommodationCollectionIdentifiers.includes(accommodationIdentifier)) {
          return false;
        }
        accommodationCollectionIdentifiers.push(accommodationIdentifier);
        return true;
      });
      return [...accommodationsToAdd, ...accommodationCollection];
    }
    return accommodationCollection;
  }
}
