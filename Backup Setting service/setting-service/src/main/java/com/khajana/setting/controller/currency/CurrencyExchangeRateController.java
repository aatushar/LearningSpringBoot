package com.khajana.setting.controller.currency;

import com.khajana.setting.dto.ApiResponse;
import com.khajana.setting.dto.SortField;
import com.khajana.setting.dto.currency.CurrencyExchangeRateRequestDto;
import com.khajana.setting.service.currency.CurrencyExchangeRateService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/setting/api/v1/currency-exchange-rate")
public class CurrencyExchangeRateController {
    @Autowired
    private CurrencyExchangeRateService currencyExchangeRateService;


    @GetMapping("/all")
    public ResponseEntity<ApiResponse> getCurrencyExchangeRates(@RequestParam(defaultValue = "0") int pageNo,
                                                                @RequestParam(defaultValue = "10") int pageSize, @RequestParam(defaultValue = "ID") SortField sortField,
                                                                @RequestParam(defaultValue = "DESC") Sort.Direction sortDirection,
                                                                @RequestParam(defaultValue = "") String filter) {

        ApiResponse apiResponse;

        Pageable pageable = PageRequest.of(pageNo, pageSize, sortDirection, sortField.getDbFieldName());

        try {
            var results = currencyExchangeRateService.findAllCurrencyExchangeRates(pageable);

            if (results.hasContent()) {
                apiResponse = new ApiResponse(200, "Paginated results", results);
            } else {
                apiResponse = new ApiResponse(404, "No Records Found!", "");
            }

        } catch (Exception e) {
            apiResponse = new ApiResponse(500, e.getMessage(), "error");
        }

        return new ResponseEntity<ApiResponse>(apiResponse, HttpStatus.OK);
    }

    @GetMapping("/find/{id}")
    public ResponseEntity<ApiResponse> getCurrencyExchangeRateById(@PathVariable("id") Long id) {

        ApiResponse apiResponse;
        try {
            var result = currencyExchangeRateService.findCurrencyExchangeRateById(id);

            if (result != null) {
                apiResponse = new ApiResponse(200, "OK", result);
            } else {
                apiResponse = new ApiResponse(404, "No Records Found!", "");
            }

        } catch (Exception e) {
            apiResponse = new ApiResponse(500, e.getMessage(), "error");
        }

        return new ResponseEntity<ApiResponse>(apiResponse, HttpStatus.OK);
    }

    @GetMapping("/drop-down")
    public ResponseEntity<ApiResponse> getDropDown() {

        ApiResponse apiResponse;
        try {
            var result = currencyExchangeRateService.getDropDown();

            if (result != null) {
                apiResponse = new ApiResponse(200, "Drop Down Found", result);
            } else {
                apiResponse = new ApiResponse(404, "No Records Found!", "");
            }

        } catch (Exception e) {
            apiResponse = new ApiResponse(500, e.getMessage(), "error");
        }

        return new ResponseEntity<ApiResponse>(apiResponse, HttpStatus.OK);
    }

    @PostMapping("/add")
    public ResponseEntity<ApiResponse> addCurrencyExchangeRate(@Valid @RequestBody CurrencyExchangeRateRequestDto dto) {

        var result = currencyExchangeRateService.addCurrencyExchangeRate(dto);

        return new ResponseEntity<ApiResponse>(result, HttpStatus.CREATED);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<ApiResponse> updateCurrency(@Valid @PathVariable("id") Long id,
                                                      @Valid @RequestBody CurrencyExchangeRateRequestDto dto) {

        var result = currencyExchangeRateService.updateCurrencyExchangeRate(id, dto);

        return new ResponseEntity<ApiResponse>(result, HttpStatus.CREATED);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ApiResponse> deleteCurrencyExchangeRate(@PathVariable("id") Long id) {

        ApiResponse apiResponse;
        try {

            currencyExchangeRateService.deleteCurrencyExchangeRate(id);

            apiResponse = new ApiResponse(200, "ok", "");

        } catch (Exception e) {
            apiResponse = new ApiResponse(500, e.getMessage(), "error");
        }

        return new ResponseEntity<ApiResponse>(apiResponse, HttpStatus.OK);

    }

}
