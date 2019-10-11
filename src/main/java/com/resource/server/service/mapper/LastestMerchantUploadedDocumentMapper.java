package com.resource.server.service.mapper;

import com.resource.server.domain.*;
import com.resource.server.service.dto.LastestMerchantUploadedDocumentDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity LastestMerchantUploadedDocument and its DTO LastestMerchantUploadedDocumentDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface LastestMerchantUploadedDocumentMapper extends EntityMapper<LastestMerchantUploadedDocumentDTO, LastestMerchantUploadedDocument> {



    default LastestMerchantUploadedDocument fromId(Long id) {
        if (id == null) {
            return null;
        }
        LastestMerchantUploadedDocument lastestMerchantUploadedDocument = new LastestMerchantUploadedDocument();
        lastestMerchantUploadedDocument.setId(id);
        return lastestMerchantUploadedDocument;
    }
}
