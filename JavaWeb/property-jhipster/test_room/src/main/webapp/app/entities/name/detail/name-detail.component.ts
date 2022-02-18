import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IName } from '../name.model';

@Component({
  selector: 'jhi-name-detail',
  templateUrl: './name-detail.component.html',
})
export class NameDetailComponent implements OnInit {
  name: IName | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ name }) => {
      this.name = name;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
