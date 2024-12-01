package hcmute.com.ShoeShop.dto;

import hcmute.com.ShoeShop.entity.Category;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;

@Data
public class ProductDto {

    protected Long id;
    protected Long categoryId;

    private String title;

    private String description;
    private Long voucher;

    private BigDecimal price;

    private String categoryName;
}
