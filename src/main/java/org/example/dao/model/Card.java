package org.example.dao.model;

import lombok.*;
import org.example.enums.CardStatus;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDate;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Card {
    private Long id;
    private Long customerId;
    private String cardNumber;
    private String holderName;
    private BigDecimal amount;
    private String cvv;
    private LocalDate expireDate;
    private CardStatus status;
    private Timestamp createdAt;
    private Timestamp updatedAt;

}
