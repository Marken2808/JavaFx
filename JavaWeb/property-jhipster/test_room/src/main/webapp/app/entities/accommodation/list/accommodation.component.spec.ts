import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { of } from 'rxjs';

import { AccommodationService } from '../service/accommodation.service';

import { AccommodationComponent } from './accommodation.component';

describe('Accommodation Management Component', () => {
  let comp: AccommodationComponent;
  let fixture: ComponentFixture<AccommodationComponent>;
  let service: AccommodationService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      declarations: [AccommodationComponent],
    })
      .overrideTemplate(AccommodationComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(AccommodationComponent);
    comp = fixture.componentInstance;
    service = TestBed.inject(AccommodationService);

    const headers = new HttpHeaders();
    jest.spyOn(service, 'query').mockReturnValue(
      of(
        new HttpResponse({
          body: [{ id: 123 }],
          headers,
        })
      )
    );
  });

  it('Should call load all on init', () => {
    // WHEN
    comp.ngOnInit();

    // THEN
    expect(service.query).toHaveBeenCalled();
    expect(comp.accommodations?.[0]).toEqual(expect.objectContaining({ id: 123 }));
  });
});
