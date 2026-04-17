package ee.msaareva.veebipood.repository;

import ee.msaareva.veebipood.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

// repository --> andmebaasiga suhtlemiseks. tema sees on kõik funktsioonid, mida on võimalik
//              andmebaasiga teha.

public interface ProductRepository extends JpaRepository<Product, Long> {
    // igas repos on võimalik seda klassi (tabeli kirjet) kätte saada listina või üksikuna
    // siin on võimalik saada kätte nt: List<Product>, Product, int, boolean jne
    Page<Product> findAllByCategoryId(Pageable pageable, Long categoryId);
}
