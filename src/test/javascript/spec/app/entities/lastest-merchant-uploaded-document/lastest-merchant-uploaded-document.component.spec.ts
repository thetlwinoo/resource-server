/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { ResourceTestModule } from '../../../test.module';
import { LastestMerchantUploadedDocumentComponent } from 'app/entities/lastest-merchant-uploaded-document/lastest-merchant-uploaded-document.component';
import { LastestMerchantUploadedDocumentService } from 'app/entities/lastest-merchant-uploaded-document/lastest-merchant-uploaded-document.service';
import { LastestMerchantUploadedDocument } from 'app/shared/model/lastest-merchant-uploaded-document.model';

describe('Component Tests', () => {
    describe('LastestMerchantUploadedDocument Management Component', () => {
        let comp: LastestMerchantUploadedDocumentComponent;
        let fixture: ComponentFixture<LastestMerchantUploadedDocumentComponent>;
        let service: LastestMerchantUploadedDocumentService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [ResourceTestModule],
                declarations: [LastestMerchantUploadedDocumentComponent],
                providers: []
            })
                .overrideTemplate(LastestMerchantUploadedDocumentComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(LastestMerchantUploadedDocumentComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(LastestMerchantUploadedDocumentService);
        });

        it('Should call load all on init', () => {
            // GIVEN
            const headers = new HttpHeaders().append('link', 'link;link');
            spyOn(service, 'query').and.returnValue(
                of(
                    new HttpResponse({
                        body: [new LastestMerchantUploadedDocument(123)],
                        headers
                    })
                )
            );

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.query).toHaveBeenCalled();
            expect(comp.lastestMerchantUploadedDocuments[0]).toEqual(jasmine.objectContaining({ id: 123 }));
        });
    });
});
