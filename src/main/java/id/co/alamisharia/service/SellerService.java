package id.co.alamisharia.service;

import id.co.alamisharia.entity.Seller;
import id.co.alamisharia.repository.SellerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SellerService {
    @Autowired
    private SellerRepository sellerRepository;

    public Seller saveSeller(Seller seller) {
        return sellerRepository.save(seller);
    }

    public boolean existsById(long id) {
        return sellerRepository.existsById(id);
    }

    public boolean existsByNama(String nama) {
        return sellerRepository.existsByNama(nama);
    }

    public List<Seller> getAllSeller() {
        return sellerRepository.findAll();
    }

    public Optional<Seller> getSellerById(long sellerId) {
        return sellerRepository.findById(sellerId);
    }
}
