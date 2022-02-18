import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { NameDetailComponent } from './name-detail.component';

describe('Name Management Detail Component', () => {
  let comp: NameDetailComponent;
  let fixture: ComponentFixture<NameDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [NameDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ name: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(NameDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(NameDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load name on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.name).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
