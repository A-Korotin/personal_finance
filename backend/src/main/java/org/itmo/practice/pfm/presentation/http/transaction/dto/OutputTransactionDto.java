package org.itmo.practice.pfm.presentation.http.transaction.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.itmo.practice.pfm.domain.funds.Currency;
import org.itmo.practice.pfm.domain.transaction.TransactionStatus;
import org.itmo.practice.pfm.domain.transaction.TransactionType;

import java.time.ZonedDateTime;
import java.util.UUID;

@Data
@Schema(
        name = "Output Transaction DTO",
        subTypes = {
                OutputIncomeTransactionDto.class,
                OutputExpenseTransactionDto.class,
                OutputTransferTransactionDto.class
        }
)
public abstract class OutputTransactionDto {
    public UUID id;
    public String userId;
    public TransactionType type;
    public TransactionStatus status;
    public String comment;
    public ZonedDateTime timestamp;
    public Currency currency;
    public Integer amount;
}
