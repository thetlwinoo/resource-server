import { Pipe, PipeTransform } from '@angular/core';
// import { makeAnimationEvent } from '@angular/animations/browser/src/render/shared';
import { IProductPhoto } from 'app/shared/model/product-photo.model';

@Pipe({ name: 'productFilter' })
export class ProductFilterPipe implements PipeTransform {
    /**
     * Transform
     *
     * @param {any[]} mainArr
     * @param {string} searchText
     * @param {string} property
     * @returns {any}
     */
    transform(productPhotos: IProductPhoto[], index: number, productId: number, missingInd: boolean = false): any {
        if (productPhotos && productPhotos.length > 0) {
            return productPhotos.filter(t => t.priority == index && t.productId == productId)[0];
        }
        return null;
    }
}
