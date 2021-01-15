package id.co.alamisharia.controller;

import id.co.alamisharia.entity.Seller;
import id.co.alamisharia.enums.ResponseEnum;
import id.co.alamisharia.service.SellerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
public class SellerController {
    @Autowired
    private SellerService service;

    @PostMapping("/addSeller")
    public ResponseEntity<ResponseEnum> addSeller(@Valid @RequestBody Seller seller, Errors errors) {
        try {
            ResponseEnum response;

            if (errors.hasErrors()) {
                FieldError fe = errors.getFieldError();
                response = ResponseEnum.BAD_REQUEST;
                response.setMessage(fe.getField() + " " + fe.getDefaultMessage());
                return new ResponseEntity<>(response, HttpStatus.OK);
            }

            if (service.existsById(seller.getId()) || service.existsByNama(seller.getNama())) {
                response = ResponseEnum.BAD_REQUEST;
                response.setMessage("Seller Already Exist");
                return new ResponseEntity<>(response, HttpStatus.OK);
            }

            Seller s = service.saveSeller(seller);
            response = ResponseEnum.SUCCESS;
            response.setData(s);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
