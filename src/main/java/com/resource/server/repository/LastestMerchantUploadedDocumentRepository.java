package com.resource.server.repository;

import com.resource.server.domain.LastestMerchantUploadedDocument;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the LastestMerchantUploadedDocument entity.
 */
@SuppressWarnings("unused")
@Repository
public interface LastestMerchantUploadedDocumentRepository extends JpaRepository<LastestMerchantUploadedDocument, Long> {

}
