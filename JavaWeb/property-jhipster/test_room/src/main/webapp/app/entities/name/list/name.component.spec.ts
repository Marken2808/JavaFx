import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { of } from 'rxjs';

import { NameService } from '../service/name.service';

import { NameComponent } from './name.component';

describe('Name Management Component', () => {
  let comp: NameComponent;
  let fixture: ComponentFixture<NameComponent>;
  let service: NameService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      declarations: [NameComponent],
    })
      .overrideTemplate(NameComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(NameComponent);
    comp = fixture.componentInstance;
    service = TestBed.inject(NameService);

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
    expect(comp.names?.[0]).toEqual(expect.objectContaining({ id: 123 }));
  });
});
