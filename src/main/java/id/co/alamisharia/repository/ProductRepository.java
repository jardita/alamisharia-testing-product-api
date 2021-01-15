package id.co.alamisharia.repository;

import id.co.alamisharia.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ProductRepository  extends CrudRepository<Product, Long> {

    Page<Product> findAll(Pageable pageable);

    Page<Product> findByNamaContaining(String keyword, Pageable pageable);

    List<Product> findBySellerId(long sellerId);

    List<Product> findByNamaContains(String keyword);

    boolean existsByIdOrAndNamaAndSellerId(long id, String nama, long sellerId);
}
