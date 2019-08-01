package com.resource.server.web.rest;
import com.resource.server.service.CurrencyRateService;
import com.resource.server.web.rest.errors.BadRequestAlertException;
import com.resource.server.web.rest.util.HeaderUtil;
import com.resource.server.service.dto.CurrencyRateDTO;
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
 * REST controller for managing CurrencyRate.
 */
@RestController
@RequestMapping("/api")
public class CurrencyRateResource {

    private final Logger log = LoggerFactory.getLogger(CurrencyRateResource.class);

    private static final String ENTITY_NAME = "currencyRate";

    private final CurrencyRateService currencyRateService;

    public CurrencyRateResource(CurrencyRateService currencyRateService) {
        this.currencyRateService = currencyRateService;
    }

    /**
     * POST  /currency-rates : Create a new currencyRate.
     *
     * @param currencyRateDTO the currencyRateDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new currencyRateDTO, or with status 400 (Bad Request) if the currencyRate has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/currency-rates")
    public ResponseEntity<CurrencyRateDTO> createCurrencyRate(@Valid @RequestBody CurrencyRateDTO currencyRateDTO) throws URISyntaxException {
        log.debug("REST request to save CurrencyRate : {}", currencyRateDTO);
        if (currencyRateDTO.getId() != null) {
            throw new BadRequestAlertException("A new currencyRate cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CurrencyRateDTO result = currencyRateService.save(currencyRateDTO);
        return ResponseEntity.created(new URI("/api/currency-rates/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /currency-rates : Updates an existing currencyRate.
     *
     * @param currencyRateDTO the currencyRateDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated currencyRateDTO,
     * or with status 400 (Bad Request) if the currencyRateDTO is not valid,
     * or with status 500 (Internal Server Error) if the currencyRateDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/currency-rates")
    public ResponseEntity<CurrencyRateDTO> updateCurrencyRate(@Valid @RequestBody CurrencyRateDTO currencyRateDTO) throws URISyntaxException {
        log.debug("REST request to update CurrencyRate : {}", currencyRateDTO);
        if (currencyRateDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        CurrencyRateDTO result = currencyRateService.save(currencyRateDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, currencyRateDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /currency-rates : get all the currencyRates.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of currencyRates in body
     */
    @GetMapping("/currency-rates")
    public List<CurrencyRateDTO> getAllCurrencyRates() {
        log.debug("REST request to get all CurrencyRates");
        return currencyRateService.findAll();
    }

    /**
     * GET  /currency-rates/:id : get the "id" currencyRate.
     *
     * @param id the id of the currencyRateDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the currencyRateDTO, or with status 404 (Not Found)
     */
    @GetMapping("/currency-rates/{id}")
    public ResponseEntity<CurrencyRateDTO> getCurrencyRate(@PathVariable Long id) {
        log.debug("REST request to get CurrencyRate : {}", id);
        Optional<CurrencyRateDTO> currencyRateDTO = currencyRateService.findOne(id);
        return ResponseUtil.wrapOrNotFound(currencyRateDTO);
    }

    /**
     * DELETE  /currency-rates/:id : delete the "id" currencyRate.
     *
     * @param id the id of the currencyRateDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/currency-rates/{id}")
    public ResponseEntity<Void> deleteCurrencyRate(@PathVariable Long id) {
        log.debug("REST request to delete CurrencyRate : {}", id);
        currencyRateService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
