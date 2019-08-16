import { Pipe, PipeTransform } from '@angular/core';
// import { makeAnimationEvent } from '@angular/animations/browser/src/render/shared';

@Pipe({ name: 'missingImagesFilter' })
export class MissingImagesFilterPipe implements PipeTransform {
    /**
     * Transform
     *
     * @param {any[]} mainArr
     * @param {string} searchText
     * @param {string} property
     * @returns {any}
     */
    transform(products: any[], missingImagesInd: boolean = false): any {
        if (!missingImagesInd) return products;
        else return products.filter(t => t.productPhotos.length <= 0);
    }
}
