package org.itmo.practice.pfm.presentation.http.transaction.dto;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.UUID;

@Data
@EqualsAndHashCode(callSuper = true)
@Builder
public class OutputTransferTransactionDto extends OutputTransactionDto {
    public UUID fromAccountId;
    public UUID toAccountId;
}
