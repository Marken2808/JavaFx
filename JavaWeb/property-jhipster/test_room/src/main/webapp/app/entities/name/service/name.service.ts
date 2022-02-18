import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IName, getNameIdentifier } from '../name.model';

export type EntityResponseType = HttpResponse<IName>;
export type EntityArrayResponseType = HttpResponse<IName[]>;

@Injectable({ providedIn: 'root' })
export class NameService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/names');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(name: IName): Observable<EntityResponseType> {
    return this.http.post<IName>(this.resourceUrl, name, { observe: 'response' });
  }

  update(name: IName): Observable<EntityResponseType> {
    return this.http.put<IName>(`${this.resourceUrl}/${getNameIdentifier(name) as number}`, name, { observe: 'response' });
  }

  partialUpdate(name: IName): Observable<EntityResponseType> {
    return this.http.patch<IName>(`${this.resourceUrl}/${getNameIdentifier(name) as number}`, name, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IName>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IName[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addNameToCollectionIfMissing(nameCollection: IName[], ...namesToCheck: (IName | null | undefined)[]): IName[] {
    const names: IName[] = namesToCheck.filter(isPresent);
    if (names.length > 0) {
      const nameCollectionIdentifiers = nameCollection.map(nameItem => getNameIdentifier(nameItem)!);
      const namesToAdd = names.filter(nameItem => {
        const nameIdentifier = getNameIdentifier(nameItem);
        if (nameIdentifier == null || nameCollectionIdentifiers.includes(nameIdentifier)) {
          return false;
        }
        nameCollectionIdentifiers.push(nameIdentifier);
        return true;
      });
      return [...namesToAdd, ...nameCollection];
    }
    return nameCollection;
  }
}
