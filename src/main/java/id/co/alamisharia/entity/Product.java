package id.co.alamisharia.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "PRODUCT_TBL", schema = "testing_product", uniqueConstraints = {
        @UniqueConstraint(name = "uk_nama_seller_id", columnNames = {"nama", "seller_id"})
})
public class Product {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false, length = 50)
    @NotBlank
    @NotEmpty
    @NotNull
    @Size(max = 50)
    private String nama;

    @Column(nullable = false, length = 15)
    @NotBlank
    @NotEmpty
    @NotNull
    @Size(max = 15)
    private String satuan;


    @Column(name = "harga_satuan", nullable = false)
    @NotNull
    @Min(1)
    private double hargaSatuan;

    @ManyToOne
    @JoinColumn(
            name = "seller_id",
            nullable = false,
            foreignKey = @ForeignKey(
                    name = "fk_product_seller_id"
            ),
            insertable = false,
            updatable = false
    )
    @JsonIgnore
    private Seller seller;

    @NotNull
    @Column(name = "seller_id", nullable = false)
    private long sellerId;

    @Column(length = 100)
    @Size(max = 100)
    private String deskripsi;

}
