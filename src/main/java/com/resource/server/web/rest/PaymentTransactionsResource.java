package com.resource.server.web.rest;
import com.resource.server.service.PaymentTransactionsService;
import com.resource.server.web.rest.errors.BadRequestAlertException;
import com.resource.server.web.rest.util.HeaderUtil;
import com.resource.server.service.dto.PaymentTransactionsDTO;
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
 * REST controller for managing PaymentTransactions.
 */
@RestController
@RequestMapping("/api")
public class PaymentTransactionsResource {

    private final Logger log = LoggerFactory.getLogger(PaymentTransactionsResource.class);

    private static final String ENTITY_NAME = "paymentTransactions";

    private final PaymentTransactionsService paymentTransactionsService;

    public PaymentTransactionsResource(PaymentTransactionsService paymentTransactionsService) {
        this.paymentTransactionsService = paymentTransactionsService;
    }

    /**
     * POST  /payment-transactions : Create a new paymentTransactions.
     *
     * @param paymentTransactionsDTO the paymentTransactionsDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new paymentTransactionsDTO, or with status 400 (Bad Request) if the paymentTransactions has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/payment-transactions")
    public ResponseEntity<PaymentTransactionsDTO> createPaymentTransactions(@Valid @RequestBody PaymentTransactionsDTO paymentTransactionsDTO) throws URISyntaxException {
        log.debug("REST request to save PaymentTransactions : {}", paymentTransactionsDTO);
        if (paymentTransactionsDTO.getId() != null) {
            throw new BadRequestAlertException("A new paymentTransactions cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PaymentTransactionsDTO result = paymentTransactionsService.save(paymentTransactionsDTO);
        return ResponseEntity.created(new URI("/api/payment-transactions/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /payment-transactions : Updates an existing paymentTransactions.
     *
     * @param paymentTransactionsDTO the paymentTransactionsDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated paymentTransactionsDTO,
     * or with status 400 (Bad Request) if the paymentTransactionsDTO is not valid,
     * or with status 500 (Internal Server Error) if the paymentTransactionsDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/payment-transactions")
    public ResponseEntity<PaymentTransactionsDTO> updatePaymentTransactions(@Valid @RequestBody PaymentTransactionsDTO paymentTransactionsDTO) throws URISyntaxException {
        log.debug("REST request to update PaymentTransactions : {}", paymentTransactionsDTO);
        if (paymentTransactionsDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        PaymentTransactionsDTO result = paymentTransactionsService.save(paymentTransactionsDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, paymentTransactionsDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /payment-transactions : get all the paymentTransactions.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of paymentTransactions in body
     */
    @GetMapping("/payment-transactions")
    public List<PaymentTransactionsDTO> getAllPaymentTransactions() {
        log.debug("REST request to get all PaymentTransactions");
        return paymentTransactionsService.findAll();
    }

    /**
     * GET  /payment-transactions/:id : get the "id" paymentTransactions.
     *
     * @param id the id of the paymentTransactionsDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the paymentTransactionsDTO, or with status 404 (Not Found)
     */
    @GetMapping("/payment-transactions/{id}")
    public ResponseEntity<PaymentTransactionsDTO> getPaymentTransactions(@PathVariable Long id) {
        log.debug("REST request to get PaymentTransactions : {}", id);
        Optional<PaymentTransactionsDTO> paymentTransactionsDTO = paymentTransactionsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(paymentTransactionsDTO);
    }

    /**
     * DELETE  /payment-transactions/:id : delete the "id" paymentTransactions.
     *
     * @param id the id of the paymentTransactionsDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/payment-transactions/{id}")
    public ResponseEntity<Void> deletePaymentTransactions(@PathVariable Long id) {
        log.debug("REST request to delete PaymentTransactions : {}", id);
        paymentTransactionsService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
