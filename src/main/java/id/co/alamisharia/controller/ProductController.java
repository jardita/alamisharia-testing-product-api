package id.co.alamisharia.controller;

import id.co.alamisharia.entity.Product;
import id.co.alamisharia.enums.ResponseEnum;
import id.co.alamisharia.service.ProductService;
import id.co.alamisharia.service.SellerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Sort.Order;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class ProductController {
    @Autowired
    private ProductService service;

    @Autowired
    private SellerService sellerService;

    @PostMapping("/addProduct")
    public ResponseEntity<ResponseEnum> addProduct(@Valid @RequestBody Product product, Errors errors) {
        try {
            ResponseEnum response;

            if (errors.hasErrors()) {
                FieldError fe = errors.getFieldError();
                response = ResponseEnum.BAD_REQUEST;
                response.setMessage(fe.getField() + " " + fe.getDefaultMessage());
                return new ResponseEntity<>(response, HttpStatus.OK);
            }

            if (service.existsByIdOrAndNamaAndSellerId(product.getId(), product.getNama().trim(), product.getSellerId())) {
                response = ResponseEnum.BAD_REQUEST;
                response.setMessage("Product Already Exist");
                return new ResponseEntity<>(response, HttpStatus.OK);
            }

            if (!sellerService.existsById(product.getSellerId())) {
                response = ResponseEnum.NOT_FOUND;
                response.setMessage("Seller Not Found");
                return new ResponseEntity<>(response, HttpStatus.OK);
            }

            Product p = service.saveProduct(product);
            response = ResponseEnum.SUCCESS;
            response.setData(p);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/listProductBySellerId")
    public ResponseEntity<ResponseEnum> listProductBySellerId(@RequestParam("seller_id") int sellerId) {
        try {
            ResponseEnum response;

            if (!sellerService.existsById(sellerId)) {
                response = ResponseEnum.NOT_FOUND;
                response.setMessage("Seller Not Found");
                return new ResponseEntity<>(response, HttpStatus.OK);
            }

            List<Product> products = service.listProductBySellerId(sellerId);
            if (products.size() < 1) {
                response = ResponseEnum.NOT_FOUND;
                response.setMessage("Seller Product Not Found");
                return new ResponseEntity<>(response, HttpStatus.OK);
            }

            response = ResponseEnum.SUCCESS;
            response.setData(products);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/searchProductByKeyword")
    public ResponseEntity<ResponseEnum> searchProductByKeyword(@RequestParam("keyword") String keyword) {
        try {
            ResponseEnum response;

            List<Product> products = service.searchProductByKeyword(keyword.trim());
            if (products.size() < 1) {
                response = ResponseEnum.NOT_FOUND;
                response.setMessage("Product Not Found!");
                return new ResponseEntity<>(response, HttpStatus.OK);
            }

            response = ResponseEnum.SUCCESS;
            response.setData(products);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/searchProductByKeywordPaging")
    public ResponseEntity<Map<String, Object>> searchProductByKeywordPaging(
            @RequestParam(required = false) String keyword,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "3") int size,
            @RequestParam(defaultValue = "id,desc") String[] sort
    ) {

        try {
            List<Order> orders = new ArrayList<Order>();

            if (sort[0].contains(",")) {
                // will sort more than 2 fields
                // sortOrder="field, direction"
                for (String sortOrder : sort) {
                    String[] _sort = sortOrder.split(",");
                    orders.add(new Order(Sort.Direction.fromString(_sort[1]), _sort[0]));
                }
            } else {
                // sort=[field, direction]
                orders.add(new Order(Sort.Direction.fromString(sort[1]), sort[0]));
            }

            Pageable paging = PageRequest.of(page, size, Sort.by(orders));

            Page<Product> pageProducts;
            if (keyword == null)
                pageProducts = service.findAll(paging);
            else
                pageProducts = service.findByKeywordContaining(keyword.trim(), paging);

            if (pageProducts.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            Map<String, Object> response = new HashMap<>();
            response.put("code", HttpStatus.OK.value());
            response.put("status", "Success");
            response.put("message", "");
            response.put("data", pageProducts);
            //response.put("currentPage", pageProducts.getNumber());
            //response.put("totalItems", pageProducts.getTotalElements());
            //response.put("totalPages", pageProducts.getTotalPages());

            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
