package org.example.dao.model;

import lombok.*;
import org.example.enums.TransferStatus;

import java.math.BigDecimal;
import java.sql.Timestamp;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Transactions {
    private Long id;
    private String sender;
    private String receiver;
    private BigDecimal amount;
    private String ccy;
    private TransferStatus status;
    private Timestamp createdAt;
    private Timestamp updatedAt;
}
