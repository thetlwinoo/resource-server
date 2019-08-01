package com.resource.server.web.rest;
import com.resource.server.service.PaymentMethodsService;
import com.resource.server.web.rest.errors.BadRequestAlertException;
import com.resource.server.web.rest.util.HeaderUtil;
import com.resource.server.service.dto.PaymentMethodsDTO;
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
 * REST controller for managing PaymentMethods.
 */
@RestController
@RequestMapping("/api")
public class PaymentMethodsResource {

    private final Logger log = LoggerFactory.getLogger(PaymentMethodsResource.class);

    private static final String ENTITY_NAME = "paymentMethods";

    private final PaymentMethodsService paymentMethodsService;

    public PaymentMethodsResource(PaymentMethodsService paymentMethodsService) {
        this.paymentMethodsService = paymentMethodsService;
    }

    /**
     * POST  /payment-methods : Create a new paymentMethods.
     *
     * @param paymentMethodsDTO the paymentMethodsDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new paymentMethodsDTO, or with status 400 (Bad Request) if the paymentMethods has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/payment-methods")
    public ResponseEntity<PaymentMethodsDTO> createPaymentMethods(@Valid @RequestBody PaymentMethodsDTO paymentMethodsDTO) throws URISyntaxException {
        log.debug("REST request to save PaymentMethods : {}", paymentMethodsDTO);
        if (paymentMethodsDTO.getId() != null) {
            throw new BadRequestAlertException("A new paymentMethods cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PaymentMethodsDTO result = paymentMethodsService.save(paymentMethodsDTO);
        return ResponseEntity.created(new URI("/api/payment-methods/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /payment-methods : Updates an existing paymentMethods.
     *
     * @param paymentMethodsDTO the paymentMethodsDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated paymentMethodsDTO,
     * or with status 400 (Bad Request) if the paymentMethodsDTO is not valid,
     * or with status 500 (Internal Server Error) if the paymentMethodsDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/payment-methods")
    public ResponseEntity<PaymentMethodsDTO> updatePaymentMethods(@Valid @RequestBody PaymentMethodsDTO paymentMethodsDTO) throws URISyntaxException {
        log.debug("REST request to update PaymentMethods : {}", paymentMethodsDTO);
        if (paymentMethodsDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        PaymentMethodsDTO result = paymentMethodsService.save(paymentMethodsDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, paymentMethodsDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /payment-methods : get all the paymentMethods.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of paymentMethods in body
     */
    @GetMapping("/payment-methods")
    public List<PaymentMethodsDTO> getAllPaymentMethods() {
        log.debug("REST request to get all PaymentMethods");
        return paymentMethodsService.findAll();
    }

    /**
     * GET  /payment-methods/:id : get the "id" paymentMethods.
     *
     * @param id the id of the paymentMethodsDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the paymentMethodsDTO, or with status 404 (Not Found)
     */
    @GetMapping("/payment-methods/{id}")
    public ResponseEntity<PaymentMethodsDTO> getPaymentMethods(@PathVariable Long id) {
        log.debug("REST request to get PaymentMethods : {}", id);
        Optional<PaymentMethodsDTO> paymentMethodsDTO = paymentMethodsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(paymentMethodsDTO);
    }

    /**
     * DELETE  /payment-methods/:id : delete the "id" paymentMethods.
     *
     * @param id the id of the paymentMethodsDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/payment-methods/{id}")
    public ResponseEntity<Void> deletePaymentMethods(@PathVariable Long id) {
        log.debug("REST request to delete PaymentMethods : {}", id);
        paymentMethodsService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
