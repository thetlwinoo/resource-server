/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { ResourceTestModule } from '../../../test.module';
import { LastestMerchantUploadedDocumentDetailComponent } from 'app/entities/lastest-merchant-uploaded-document/lastest-merchant-uploaded-document-detail.component';
import { LastestMerchantUploadedDocument } from 'app/shared/model/lastest-merchant-uploaded-document.model';

describe('Component Tests', () => {
    describe('LastestMerchantUploadedDocument Management Detail Component', () => {
        let comp: LastestMerchantUploadedDocumentDetailComponent;
        let fixture: ComponentFixture<LastestMerchantUploadedDocumentDetailComponent>;
        const route = ({
            data: of({ lastestMerchantUploadedDocument: new LastestMerchantUploadedDocument(123) })
        } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [ResourceTestModule],
                declarations: [LastestMerchantUploadedDocumentDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(LastestMerchantUploadedDocumentDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(LastestMerchantUploadedDocumentDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.lastestMerchantUploadedDocument).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
