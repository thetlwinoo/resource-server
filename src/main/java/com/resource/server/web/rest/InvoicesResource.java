package com.resource.server.web.rest;
import com.resource.server.service.InvoicesService;
import com.resource.server.web.rest.errors.BadRequestAlertException;
import com.resource.server.web.rest.util.HeaderUtil;
import com.resource.server.service.dto.InvoicesDTO;
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
 * REST controller for managing Invoices.
 */
@RestController
@RequestMapping("/api")
public class InvoicesResource {

    private final Logger log = LoggerFactory.getLogger(InvoicesResource.class);

    private static final String ENTITY_NAME = "invoices";

    private final InvoicesService invoicesService;

    public InvoicesResource(InvoicesService invoicesService) {
        this.invoicesService = invoicesService;
    }

    /**
     * POST  /invoices : Create a new invoices.
     *
     * @param invoicesDTO the invoicesDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new invoicesDTO, or with status 400 (Bad Request) if the invoices has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/invoices")
    public ResponseEntity<InvoicesDTO> createInvoices(@Valid @RequestBody InvoicesDTO invoicesDTO) throws URISyntaxException {
        log.debug("REST request to save Invoices : {}", invoicesDTO);
        if (invoicesDTO.getId() != null) {
            throw new BadRequestAlertException("A new invoices cannot already have an ID", ENTITY_NAME, "idexists");
        }
        InvoicesDTO result = invoicesService.save(invoicesDTO);
        return ResponseEntity.created(new URI("/api/invoices/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /invoices : Updates an existing invoices.
     *
     * @param invoicesDTO the invoicesDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated invoicesDTO,
     * or with status 400 (Bad Request) if the invoicesDTO is not valid,
     * or with status 500 (Internal Server Error) if the invoicesDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/invoices")
    public ResponseEntity<InvoicesDTO> updateInvoices(@Valid @RequestBody InvoicesDTO invoicesDTO) throws URISyntaxException {
        log.debug("REST request to update Invoices : {}", invoicesDTO);
        if (invoicesDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        InvoicesDTO result = invoicesService.save(invoicesDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, invoicesDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /invoices : get all the invoices.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of invoices in body
     */
    @GetMapping("/invoices")
    public List<InvoicesDTO> getAllInvoices() {
        log.debug("REST request to get all Invoices");
        return invoicesService.findAll();
    }

    /**
     * GET  /invoices/:id : get the "id" invoices.
     *
     * @param id the id of the invoicesDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the invoicesDTO, or with status 404 (Not Found)
     */
    @GetMapping("/invoices/{id}")
    public ResponseEntity<InvoicesDTO> getInvoices(@PathVariable Long id) {
        log.debug("REST request to get Invoices : {}", id);
        Optional<InvoicesDTO> invoicesDTO = invoicesService.findOne(id);
        return ResponseUtil.wrapOrNotFound(invoicesDTO);
    }

    /**
     * DELETE  /invoices/:id : delete the "id" invoices.
     *
     * @param id the id of the invoicesDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/invoices/{id}")
    public ResponseEntity<Void> deleteInvoices(@PathVariable Long id) {
        log.debug("REST request to delete Invoices : {}", id);
        invoicesService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
