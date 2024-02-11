package org.itmo.practice.pfm.presentation.http.account.mapper;

import org.itmo.practice.pfm.domain.account.AccountBalance;
import org.itmo.practice.pfm.domain.account.AccountName;
import org.itmo.practice.pfm.domain.account.UserAccount;
import org.itmo.practice.pfm.presentation.http.account.dto.AccountDto;
import org.itmo.practice.pfm.presentation.http.account.dto.OutputAccountDto;
import org.itmo.practice.pfm.presentation.http.mapper.EntityMapper;
import org.springframework.stereotype.Component;

@Component
public class AccountMapper implements EntityMapper<UserAccount, AccountDto, OutputAccountDto> {

    @Override
    public UserAccount fromDto(AccountDto accountDto, String userId) {
        return new UserAccount(userId,
                new AccountBalance(accountDto.amount, accountDto.currency),
                new AccountName(accountDto.name));
    }

    @Override
    public OutputAccountDto toDto(UserAccount entity) {
        return OutputAccountDto.builder()
                .id(entity.getId())
                .userId(entity.getUserId())
                .name(entity.getName().getName())
                .currency(entity.getBalance().getCurrency())
                .balance(entity.getBalance().getBalance())
                .build();
    }
}
