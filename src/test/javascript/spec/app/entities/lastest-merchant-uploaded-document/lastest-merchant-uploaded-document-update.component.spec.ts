/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { ResourceTestModule } from '../../../test.module';
import { LastestMerchantUploadedDocumentUpdateComponent } from 'app/entities/lastest-merchant-uploaded-document/lastest-merchant-uploaded-document-update.component';
import { LastestMerchantUploadedDocumentService } from 'app/entities/lastest-merchant-uploaded-document/lastest-merchant-uploaded-document.service';
import { LastestMerchantUploadedDocument } from 'app/shared/model/lastest-merchant-uploaded-document.model';

describe('Component Tests', () => {
    describe('LastestMerchantUploadedDocument Management Update Component', () => {
        let comp: LastestMerchantUploadedDocumentUpdateComponent;
        let fixture: ComponentFixture<LastestMerchantUploadedDocumentUpdateComponent>;
        let service: LastestMerchantUploadedDocumentService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [ResourceTestModule],
                declarations: [LastestMerchantUploadedDocumentUpdateComponent]
            })
                .overrideTemplate(LastestMerchantUploadedDocumentUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(LastestMerchantUploadedDocumentUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(LastestMerchantUploadedDocumentService);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity', fakeAsync(() => {
                // GIVEN
                const entity = new LastestMerchantUploadedDocument(123);
                spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.lastestMerchantUploadedDocument = entity;
                // WHEN
                comp.save();
                tick(); // simulate async

                // THEN
                expect(service.update).toHaveBeenCalledWith(entity);
                expect(comp.isSaving).toEqual(false);
            }));

            it('Should call create service on save for new entity', fakeAsync(() => {
                // GIVEN
                const entity = new LastestMerchantUploadedDocument();
                spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.lastestMerchantUploadedDocument = entity;
                // WHEN
                comp.save();
                tick(); // simulate async

                // THEN
                expect(service.create).toHaveBeenCalledWith(entity);
                expect(comp.isSaving).toEqual(false);
            }));
        });
    });
});
