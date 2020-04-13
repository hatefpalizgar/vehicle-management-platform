import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'vehicle',
        loadChildren: () => import('./vehicle/vehicle.module').then(m => m.VehicleManagementPlatformVehicleModule)
      }
    ])
  ]
})
export class VehicleManagementPlatformEntityModule {}
