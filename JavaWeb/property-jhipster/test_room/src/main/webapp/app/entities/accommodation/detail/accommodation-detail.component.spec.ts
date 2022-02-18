import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { AccommodationDetailComponent } from './accommodation-detail.component';

describe('Accommodation Management Detail Component', () => {
  let comp: AccommodationDetailComponent;
  let fixture: ComponentFixture<AccommodationDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [AccommodationDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ accommodation: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(AccommodationDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(AccommodationDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load accommodation on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.accommodation).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
