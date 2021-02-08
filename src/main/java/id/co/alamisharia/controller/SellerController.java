package id.co.alamisharia.controller;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import id.co.alamisharia.entity.Seller;
import id.co.alamisharia.enums.ResponseEnum;
import id.co.alamisharia.service.SellerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
public class SellerController {
    @Autowired
    private SellerService sellerService;

    @RequestMapping(value={"/addSeller", "/addSeller2"}, method = RequestMethod.POST)
    public ResponseEntity<ResponseEnum> addSeller(@Valid @RequestBody Seller seller, Errors errors, HttpServletRequest request) {
        try {
            ResponseEnum response;

            if (errors.hasErrors()) {
                FieldError fe = errors.getFieldError();
                response = ResponseEnum.BAD_REQUEST;
                response.setMessage(fe.getField() + " " + fe.getDefaultMessage());
                return new ResponseEntity<>(response, HttpStatus.OK);
            }

            if (sellerService.existsById(seller.getId()) || sellerService.existsByNama(seller.getNama())) {
                response = ResponseEnum.BAD_REQUEST;
                response.setMessage("Seller Already Exist");
                return new ResponseEntity<>(response, HttpStatus.OK);
            }

            Seller s = sellerService.saveSeller(seller);

            response = ResponseEnum.SUCCESS;

            // remove json if null
            String url = request.getServletPath();
            if(!url.contains("addSeller2")) {
                ObjectMapper mapper = new ObjectMapper();
                mapper.setSerializationInclusion(JsonInclude.Include.NON_EMPTY);
                String jsonString = mapper.writeValueAsString(s);
                JsonNode actualObj = mapper.readTree(jsonString);
                response.setData(actualObj);
            } else {
                response.setData(s);
            }

            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/getAllSeller")
    public ResponseEntity<ResponseEnum> getAllSeller() {
        try {
            ResponseEnum response;

            List<Seller> sellers = sellerService.getAllSeller();
            if (sellers.size() < 1) {
                response = ResponseEnum.NOT_FOUND;
                response.setMessage("Seller Not Found");
                return new ResponseEntity<>(response, HttpStatus.OK);
            }

            response = ResponseEnum.SUCCESS;
            response.setData(sellers);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/getSellerById")
    public ResponseEntity<ResponseEnum> getSellerById(@RequestParam("seller_id") int sellerId) {
        try {
            ResponseEnum response;

            if (!sellerService.existsById(sellerId)) {
                response = ResponseEnum.NOT_FOUND;
                response.setMessage("Seller Not Found");
                return new ResponseEntity<>(response, HttpStatus.OK);
            }

            Optional<Seller> seller = sellerService.getSellerById(sellerId);

            response = ResponseEnum.SUCCESS;
            response.setData(seller);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
