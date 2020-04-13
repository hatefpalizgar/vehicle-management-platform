import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IVehicle, Vehicle } from 'app/shared/model/vehicle.model';
import { VehicleService } from './vehicle.service';

@Component({
  selector: 'jhi-vehicle-update',
  templateUrl: './vehicle-update.component.html'
})
export class VehicleUpdateComponent implements OnInit {
  isSaving = false;
  creationDateDp: any;

  editForm = this.fb.group({
    id: [],
    brand: [],
    model: [],
    vehicleType: [],
    plateCountry: [],
    plateNumber: [],
    vin: [null, []],
    creationDate: [],
    manufacturedCountry: []
  });

  constructor(protected vehicleService: VehicleService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ vehicle }) => {
      this.updateForm(vehicle);
    });
  }

  updateForm(vehicle: IVehicle): void {
    this.editForm.patchValue({
      id: vehicle.id,
      brand: vehicle.brand,
      model: vehicle.model,
      vehicleType: vehicle.vehicleType,
      plateCountry: vehicle.plateCountry,
      plateNumber: vehicle.plateNumber,
      vin: vehicle.vin,
      creationDate: vehicle.creationDate,
      manufacturedCountry: vehicle.manufacturedCountry
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const vehicle = this.createFromForm();
    if (vehicle.id !== undefined) {
      this.subscribeToSaveResponse(this.vehicleService.update(vehicle));
    } else {
      this.subscribeToSaveResponse(this.vehicleService.create(vehicle));
    }
  }

  private createFromForm(): IVehicle {
    return {
      ...new Vehicle(),
      id: this.editForm.get(['id'])!.value,
      brand: this.editForm.get(['brand'])!.value,
      model: this.editForm.get(['model'])!.value,
      vehicleType: this.editForm.get(['vehicleType'])!.value,
      plateCountry: this.editForm.get(['plateCountry'])!.value,
      plateNumber: this.editForm.get(['plateNumber'])!.value,
      vin: this.editForm.get(['vin'])!.value,
      creationDate: this.editForm.get(['creationDate'])!.value,
      manufacturedCountry: this.editForm.get(['manufacturedCountry'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IVehicle>>): void {
    result.subscribe(
      () => this.onSaveSuccess(),
      () => this.onSaveError()
    );
  }

  protected onSaveSuccess(): void {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError(): void {
    this.isSaving = false;
  }
}
