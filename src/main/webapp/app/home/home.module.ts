import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { VehicleManagementPlatformSharedModule } from 'app/shared/shared.module';
import { HOME_ROUTE } from './home.route';
import { HomeComponent } from './home.component';

@NgModule({
  imports: [VehicleManagementPlatformSharedModule, RouterModule.forChild([HOME_ROUTE])],
  declarations: [HomeComponent]
})
export class VehicleManagementPlatformHomeModule {}
