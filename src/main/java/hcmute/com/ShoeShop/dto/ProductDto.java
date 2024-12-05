package hcmute.com.ShoeShop.dto;
import lombok.Data;

@Data
public class ProductDto {

    protected Long id;
    protected Long categoryId;

    private String title;

    private String description;
    private Long voucher;

    private double price;

    private String categoryName;
}
