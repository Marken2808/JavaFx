import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { of } from 'rxjs';

import { AreaService } from '../service/area.service';

import { AreaComponent } from './area.component';

describe('Area Management Component', () => {
  let comp: AreaComponent;
  let fixture: ComponentFixture<AreaComponent>;
  let service: AreaService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      declarations: [AreaComponent],
    })
      .overrideTemplate(AreaComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(AreaComponent);
    comp = fixture.componentInstance;
    service = TestBed.inject(AreaService);

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
    expect(comp.areas?.[0]).toEqual(expect.objectContaining({ id: 123 }));
  });
});
