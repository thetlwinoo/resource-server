import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpHeaders, HttpResponse } from '@angular/common/http';
import { Observable, Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService, JhiParseLinks } from 'ng-jhipster';

import { IProducts } from 'app/shared/model/products.model';
import { AccountService } from 'app/core';
import { ProductsService } from 'app/entities/products';
import { MenuItem } from 'primeng/api';
import { ProductPhotoService } from 'app/entities/product-photo';
import { IProductPhoto } from 'app/shared/model/product-photo.model';
import { ITEMS_PER_PAGE } from 'app/shared';
import { ActivatedRoute, Router } from '@angular/router';

@Component({
    selector: 'jhi-manage-images',
    templateUrl: './manage-images.component.html',
    styleUrls: ['manage-images.scss']
})
export class ManageImagesComponent implements OnInit, OnDestroy {
    products: IProducts[];
    currentAccount: any;
    eventSubscriber: Subscription;
    mangeImageTabs: MenuItem[];
    activeManageTab: MenuItem;
    uploadProgress = 0;
    missingImagesInd = false;

    routeData: any;
    totalItems: any;
    itemsPerPage: any;
    page: any;
    predicate: any;
    previousPage: any;
    reverse: any;
    error: any;
    links: any;

    missedInd = false;
    cols: any[];

    brands: any[];

    colors: any[];

    yearFilter: number;

    yearTimeout: any;

    constructor(
        protected productsService: ProductsService,
        protected jhiAlertService: JhiAlertService,
        protected eventManager: JhiEventManager,
        protected accountService: AccountService,
        protected productPhotoService: ProductPhotoService,
        protected activatedRoute: ActivatedRoute,
        protected router: Router,
        protected parseLinks: JhiParseLinks
    ) {
        this.itemsPerPage = ITEMS_PER_PAGE;
        this.routeData = this.activatedRoute.data.subscribe(data => {
            this.page = data.pagingParams.page;
            this.previousPage = data.pagingParams.page;
            this.reverse = data.pagingParams.ascending;
            this.predicate = data.pagingParams.predicate;
        });
    }

    loadAll() {
        this.productsService
            .query({
                page: this.page - 1,
                size: this.itemsPerPage,
                sort: this.sort()
            })
            .subscribe(
                (res: HttpResponse<IProducts[]>) => {
                    this.paginateProducts(res.body, res.headers);
                },
                (res: HttpErrorResponse) => this.onError(res.message)
            );
    }

    ngOnInit() {
        this.loadAll();
        this.accountService.identity().then(account => {
            this.currentAccount = account;
        });

        this.mangeImageTabs = [
            {
                id: '0',
                label: 'All Images',
                icon: 'fa fa-fw fa-book',
                command: event => {
                    this.missingImagesInd = false;
                }
            },
            {
                id: '1',
                label: 'Missing Images',
                icon: 'fa fa-fw fa-support',
                command: event => {
                    this.missingImagesInd = true;
                }
            }
        ];
        this.activeManageTab = this.mangeImageTabs[0];

        this.brands = [
            { label: 'All Brands', value: null },
            { label: 'Audi', value: 'Audi' },
            { label: 'BMW', value: 'BMW' },
            { label: 'Fiat', value: 'Fiat' },
            { label: 'Honda', value: 'Honda' },
            { label: 'Jaguar', value: 'Jaguar' },
            { label: 'Mercedes', value: 'Mercedes' },
            { label: 'Renault', value: 'Renault' },
            { label: 'VW', value: 'VW' },
            { label: 'Volvo', value: 'Volvo' }
        ];

        this.colors = [
            { label: 'White', value: 'White' },
            { label: 'Green', value: 'Green' },
            { label: 'Silver', value: 'Silver' },
            { label: 'Black', value: 'Black' },
            { label: 'Red', value: 'Red' },
            { label: 'Maroon', value: 'Maroon' },
            { label: 'Brown', value: 'Brown' },
            { label: 'Orange', value: 'Orange' },
            { label: 'Blue', value: 'Blue' }
        ];

        this.cols = [{ field: 'productName', header: 'Products', filterMatchMode: 'contains', width: '30%' }];

        this.registerChangeInProducts();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    // closeManageTab(event, index) {
    //     this.mangeImageTabs = this.mangeImageTabs.filter((item, i) => i !== index);
    //     event.preventDefault();
    // }

    trackId(index: number, item: IProducts) {
        return item.id;
    }

    registerChangeInProducts() {
        this.eventSubscriber = this.eventManager.subscribe('productImageListModification', response => this.loadAll());
    }

    protected onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }

    public giveImage(event) {
        console.log(event);
    }

    trackPrimengPage(event) {
        const page = event.first / event.rows + 1;
        this.loadPage(page);
    }

    loadPage(page: number) {
        if (page !== this.previousPage) {
            this.previousPage = page;
            this.transition();
        }
    }

    clear() {
        this.page = 0;
        this.router.navigate([
            '/products',
            {
                page: this.page,
                sort: this.predicate + ',' + (this.reverse ? 'asc' : 'desc')
            }
        ]);
        this.loadAll();
    }

    transition() {
        this.router.navigate(['/manage-images'], {
            queryParams: {
                page: this.page,
                size: this.itemsPerPage,
                sort: this.predicate + ',' + (this.reverse ? 'asc' : 'desc')
            }
        });
        this.loadAll();
    }

    sort() {
        const result = [this.predicate + ',' + (this.reverse ? 'asc' : 'desc')];
        if (this.predicate !== 'id') {
            result.push('id');
        }
        return result;
    }

    protected paginateProducts(data: IProducts[], headers: HttpHeaders) {
        this.links = this.parseLinks.parse(headers.get('link'));
        this.totalItems = parseInt(headers.get('X-Total-Count'), 10);
        this.products = data;

        this.products.forEach(product => {
            this.productPhotoService
                .query({
                    'productId.equals': product.id
                })
                .pipe(
                    filter((res: HttpResponse<IProductPhoto[]>) => res.ok),
                    map((res: HttpResponse<IProductPhoto[]>) => res.body)
                )
                .subscribe(
                    (res: IProductPhoto[]) => {
                        Object.assign(product, { productPhotos: res });
                    },
                    (res: HttpErrorResponse) => this.onError(res.message)
                );
        });

        console.log('Products', this.products);
    }
}
