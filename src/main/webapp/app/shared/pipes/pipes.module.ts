import { NgModule } from '@angular/core';

import { ProductFilterPipe } from './product-filter.pipe';
import { MissingImagesFilterPipe } from './missing-images-filter.pipe';

@NgModule({
    declarations: [ProductFilterPipe, MissingImagesFilterPipe],
    imports: [],
    exports: [ProductFilterPipe, MissingImagesFilterPipe]
})
export class jhiPipesModule {}
