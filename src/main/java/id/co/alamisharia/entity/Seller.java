package id.co.alamisharia.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "SELLER_TBL", schema = "testing_product")
public class Seller {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false, length = 50)
    @Size(max = 50)
    @NotBlank
    private String nama;

    @Column(nullable = false, length = 50)
    @Size(max = 50)
    @NotBlank
    private String kota;

    @Column(length = 50)
    @Size(max = 50)
    private String jenis;

    @Column(name = "tahun_berdiri", length = 4)
    private Integer tahunBerdiri;

    @JsonBackReference
    @OneToMany(
        cascade = CascadeType.ALL,
        orphanRemoval = true,
        mappedBy = "seller"
    )
    private List<Product> products = new ArrayList<>();

}
