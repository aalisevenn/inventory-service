package com.aliseven.inventoryservice.model.entity;

import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;

@Entity
@Table(name = "orders")
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = {"id"})
@ToString
@SuperBuilder
public class OrderEntity extends BaseEntity{

    private static final long serialVersionUID = 1220928611946995982L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    @Builder.Default
    private BigDecimal discount = BigDecimal.ZERO;

    @Column(nullable = false)
    private BigDecimal price;

    private Integer quantity;

    @ManyToMany
    private List<ProductEntity> products;

    private Long userId;
}
