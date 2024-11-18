package org.odc.demo.Web.Controller.Impl;

import org.odc.demo.Datas.Entity.Account;
import org.odc.demo.Services.Impl.AccountServiceImpl;
import org.odc.demo.Web.Controller.Interfaces.CrudController;
import org.odc.demo.Web.Dtos.Request.AccountRequestDto;
import org.odc.demo.Web.Dtos.Request.AccountUpdateRequestDto;
import org.odc.demo.Web.Dtos.Response.ApiResponse;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/accounts") // Set the base path for the controller
public class AccountController implements CrudController<Account, AccountRequestDto, AccountUpdateRequestDto> {

    private final AccountServiceImpl accountService;

    // Constructor injection of the service layer
    public AccountController(AccountServiceImpl accountService) {
        this.accountService = accountService;
    }

    @Override
    @PostMapping
    public ApiResponse<Account> Create(@RequestBody AccountRequestDto accountRequestDto) {
        // Implement the creation logic here

        return accountService.Create(accountRequestDto);
    }

    @Override
    @PutMapping("/{id}")
    public ApiResponse<Account> Update(@PathVariable Long id, @RequestBody AccountUpdateRequestDto accountUpdateDto) {
        // Implement the update logic here

        return accountService.Update(id, accountUpdateDto);
    }

    @Override
    @GetMapping
    public ApiResponse<List<Account>> findAll() {
        // Fetch all accounts
        return accountService.findAll();
    }

    @Override
    @GetMapping("/{id}")
    public ApiResponse<Account> findById(@PathVariable Long id) {
        // Find an account by ID
        return accountService.findById(id);
    }
}
