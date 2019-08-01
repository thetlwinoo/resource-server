/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { ResourceTestModule } from '../../../test.module';
import { StockGroupsComponent } from 'app/entities/stock-groups/stock-groups.component';
import { StockGroupsService } from 'app/entities/stock-groups/stock-groups.service';
import { StockGroups } from 'app/shared/model/stock-groups.model';

describe('Component Tests', () => {
    describe('StockGroups Management Component', () => {
        let comp: StockGroupsComponent;
        let fixture: ComponentFixture<StockGroupsComponent>;
        let service: StockGroupsService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [ResourceTestModule],
                declarations: [StockGroupsComponent],
                providers: []
            })
                .overrideTemplate(StockGroupsComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(StockGroupsComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(StockGroupsService);
        });

        it('Should call load all on init', () => {
            // GIVEN
            const headers = new HttpHeaders().append('link', 'link;link');
            spyOn(service, 'query').and.returnValue(
                of(
                    new HttpResponse({
                        body: [new StockGroups(123)],
                        headers
                    })
                )
            );

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.query).toHaveBeenCalled();
            expect(comp.stockGroups[0]).toEqual(jasmine.objectContaining({ id: 123 }));
        });
    });
});
