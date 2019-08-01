package com.resource.server.web.rest;
import com.resource.server.service.InvoiceLinesService;
import com.resource.server.web.rest.errors.BadRequestAlertException;
import com.resource.server.web.rest.util.HeaderUtil;
import com.resource.server.service.dto.InvoiceLinesDTO;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing InvoiceLines.
 */
@RestController
@RequestMapping("/api")
public class InvoiceLinesResource {

    private final Logger log = LoggerFactory.getLogger(InvoiceLinesResource.class);

    private static final String ENTITY_NAME = "invoiceLines";

    private final InvoiceLinesService invoiceLinesService;

    public InvoiceLinesResource(InvoiceLinesService invoiceLinesService) {
        this.invoiceLinesService = invoiceLinesService;
    }

    /**
     * POST  /invoice-lines : Create a new invoiceLines.
     *
     * @param invoiceLinesDTO the invoiceLinesDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new invoiceLinesDTO, or with status 400 (Bad Request) if the invoiceLines has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/invoice-lines")
    public ResponseEntity<InvoiceLinesDTO> createInvoiceLines(@Valid @RequestBody InvoiceLinesDTO invoiceLinesDTO) throws URISyntaxException {
        log.debug("REST request to save InvoiceLines : {}", invoiceLinesDTO);
        if (invoiceLinesDTO.getId() != null) {
            throw new BadRequestAlertException("A new invoiceLines cannot already have an ID", ENTITY_NAME, "idexists");
        }
        InvoiceLinesDTO result = invoiceLinesService.save(invoiceLinesDTO);
        return ResponseEntity.created(new URI("/api/invoice-lines/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /invoice-lines : Updates an existing invoiceLines.
     *
     * @param invoiceLinesDTO the invoiceLinesDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated invoiceLinesDTO,
     * or with status 400 (Bad Request) if the invoiceLinesDTO is not valid,
     * or with status 500 (Internal Server Error) if the invoiceLinesDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/invoice-lines")
    public ResponseEntity<InvoiceLinesDTO> updateInvoiceLines(@Valid @RequestBody InvoiceLinesDTO invoiceLinesDTO) throws URISyntaxException {
        log.debug("REST request to update InvoiceLines : {}", invoiceLinesDTO);
        if (invoiceLinesDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        InvoiceLinesDTO result = invoiceLinesService.save(invoiceLinesDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, invoiceLinesDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /invoice-lines : get all the invoiceLines.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of invoiceLines in body
     */
    @GetMapping("/invoice-lines")
    public List<InvoiceLinesDTO> getAllInvoiceLines() {
        log.debug("REST request to get all InvoiceLines");
        return invoiceLinesService.findAll();
    }

    /**
     * GET  /invoice-lines/:id : get the "id" invoiceLines.
     *
     * @param id the id of the invoiceLinesDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the invoiceLinesDTO, or with status 404 (Not Found)
     */
    @GetMapping("/invoice-lines/{id}")
    public ResponseEntity<InvoiceLinesDTO> getInvoiceLines(@PathVariable Long id) {
        log.debug("REST request to get InvoiceLines : {}", id);
        Optional<InvoiceLinesDTO> invoiceLinesDTO = invoiceLinesService.findOne(id);
        return ResponseUtil.wrapOrNotFound(invoiceLinesDTO);
    }

    /**
     * DELETE  /invoice-lines/:id : delete the "id" invoiceLines.
     *
     * @param id the id of the invoiceLinesDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/invoice-lines/{id}")
    public ResponseEntity<Void> deleteInvoiceLines(@PathVariable Long id) {
        log.debug("REST request to delete InvoiceLines : {}", id);
        invoiceLinesService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
