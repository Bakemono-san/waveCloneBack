package org.odc.demo.Services.Impl;

import org.odc.demo.Datas.Entity.Account;
import org.odc.demo.Datas.Repository.AccountRepository;
import org.odc.demo.Services.Interfaces.CrudService;
import org.odc.demo.Web.Dtos.Request.AccountRequestDto;
import org.odc.demo.Web.Dtos.Request.AccountUpdateRequestDto;
import org.odc.demo.Web.Dtos.Response.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AccountServiceImpl implements CrudService<Account, AccountRequestDto, AccountUpdateRequestDto> {

    @Autowired
    AccountRepository accountRepository;

    @Override
    public ApiResponse<Account> Create(AccountRequestDto accountRequestDto) {
        Account createdAccount = new Account();
        createdAccount.setSolde(accountRequestDto.getSolde());
        createdAccount.setType(accountRequestDto.getType());
        createdAccount.setPlafond(accountRequestDto.getPlafond());

        Account savedAccount = accountRepository.save(createdAccount);
        return new ApiResponse<>(savedAccount, "Account created successfully");
    }

    @Override
    public ApiResponse<Account> Update(Long id, AccountUpdateRequestDto accountUpdateRequestDto) {
        Optional<Account> accountOpt = accountRepository.findById(id);
        if (accountOpt.isPresent()) {
            Account updatedAccount = accountOpt.get();
            updatedAccount.setSolde(accountUpdateRequestDto.getSolde());
            updatedAccount.setType(accountUpdateRequestDto.getType());
            updatedAccount.setPlafond(accountUpdateRequestDto.getPlafond());

            Account savedAccount = accountRepository.save(updatedAccount);
            return new ApiResponse<>(savedAccount, "Account updated successfully");
        }

        return new ApiResponse<>(null, "Account not found");
    }

    @Override
    public ApiResponse<Account> Delete(Long id) {
        Optional<Account> accountOpt = accountRepository.findById(id);
        if (accountOpt.isPresent()) {
            Account deletedAccount = accountOpt.get();
            accountRepository.deleteById(id);
            return new ApiResponse<>(deletedAccount, "Account deleted successfully");
        }

        return new ApiResponse<>(null, "Account not found");
    }

    @Override
    public ApiResponse<List<Account>> findAll() {
        List<Account> accounts = accountRepository.findAll();
        if (accounts.isEmpty()) {
            return new ApiResponse<>(null, "No accounts found");
        }
        return new ApiResponse<>(accounts, "Accounts retrieved successfully");
    }

    @Override
    public ApiResponse<Account> findById(Long id) {
        Optional<Account> accountOpt = accountRepository.findById(id);
        if (accountOpt.isPresent()) {
            return new ApiResponse<>(accountOpt.get(), "Account found");
        }

        return new ApiResponse<>(null, "Account not found");
    }
}
