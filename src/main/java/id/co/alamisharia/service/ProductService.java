package id.co.alamisharia.service;

import id.co.alamisharia.entity.Product;
import id.co.alamisharia.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {
    @Autowired
    private ProductRepository repository;

    public Product saveProduct(Product product) {
        return repository.save(product);
    }

    public boolean existsByIdOrAndNamaAndSellerId(long id, String nama, long sellerId) {
        return repository.existsByIdOrAndNamaAndSellerId(id, nama, sellerId);
    }

    public List<Product> listProductBySellerId(long sellerId) {
        return repository.findBySellerId(sellerId);
    }

    public List<Product> searchProductByKeyword(String keyword) {
        return repository.findByNamaContains(keyword);
    }

    public Page<Product> findAll(Pageable paging) {
        return repository.findAll(paging);
    }

    public Page<Product> findByKeywordContaining(String keyword, Pageable pageable) {
        return repository.findByNamaContaining(keyword, pageable);
    }

}
