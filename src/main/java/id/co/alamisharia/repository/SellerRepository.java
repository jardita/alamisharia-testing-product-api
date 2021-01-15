package id.co.alamisharia.repository;

import id.co.alamisharia.entity.Seller;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SellerRepository extends JpaRepository<Seller, Long> {

    boolean existsByNama(String nama);
}
