package hcmute.com.ShoeShop.dto;

import hcmute.com.ShoeShop.entity.Product;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class InventoryDto {
    protected int id;
    protected Long productId;
    private String title;
    private int quantity;
    private LocalDateTime createdAt;
}
