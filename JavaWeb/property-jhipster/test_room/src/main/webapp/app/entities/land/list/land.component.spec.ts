import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { of } from 'rxjs';

import { LandService } from '../service/land.service';

import { LandComponent } from './land.component';

describe('Land Management Component', () => {
  let comp: LandComponent;
  let fixture: ComponentFixture<LandComponent>;
  let service: LandService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      declarations: [LandComponent],
    })
      .overrideTemplate(LandComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(LandComponent);
    comp = fixture.componentInstance;
    service = TestBed.inject(LandService);

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
    expect(comp.lands?.[0]).toEqual(expect.objectContaining({ id: 123 }));
  });
});
