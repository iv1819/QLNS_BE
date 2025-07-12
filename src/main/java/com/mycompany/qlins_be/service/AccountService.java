/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
// AccountService.java
package com.mycompany.qlins_be.service;

import com.mycompany.qlins_be.entity.Account;
import com.mycompany.qlins_be.model.AccountDto;
import com.mycompany.qlins_be.model.UpdateaccountDto;
import com.mycompany.qlins_be.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AccountService {

    @Autowired
    private AccountRepository accountRepository;

    // Helper method to convert Entity to DTO
    private AccountDto convertToDto(Account account) {
        if (account == null) {
            return null;
        }
        AccountDto dto = new AccountDto();
        dto.setTaiKhoan(account.getTaiKhoan());
        dto.setMatKhau(account.getMatKhau());
        dto.setChucVu(account.getChucVu());
        dto.setTrangThai(account.getTrangThai());
        dto.setTennv(account.getTennv());
        return dto;
    }

    // Helper method to convert DTO to Entity
    private Account convertToEntity(AccountDto dto) {
        if (dto == null) {
            return null;
        }
        Account account = new Account();
        account.setTaiKhoan(dto.getTaiKhoan()); // Use taiKhoan directly from DTO
        account.setMatKhau(dto.getMatKhau());
        account.setChucVu(dto.getChucVu());
        account.setTrangThai(dto.getTrangThai());
        account.setTennv(dto.getTennv());
        return account;
    }

    public AccountDto registerAccount(AccountDto accountDto) {
        // Check if account already exists using taiKhoan
        if (accountRepository.existsById(accountDto.getTaiKhoan())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Tài khoản đã tồn tại.");
        }
        accountDto.setTrangThai("No");
        Account account = convertToEntity(accountDto);
        // No need to set taiKhoan separately, it's already set from DTO in convertToEntity
        Account savedAccount = accountRepository.save(account);
        return convertToDto(savedAccount);
    }

    public AccountDto loginAccount(String taiKhoan, String matKhau) {
        Optional<Account> accountOpt = accountRepository.findByTaiKhoanAndMatKhau(taiKhoan, matKhau);
        if (accountOpt.isPresent()) {
            Account account = accountOpt.get();
            if (!account.getTrangThai().equalsIgnoreCase("Yes")) {
                throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Tài khoản chưa được duyệt.");
            }
return convertToDto(account);
        } else {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Tài khoản hoặc mật khẩu không đúng.");
        }
    }

    public AccountDto updateAccount(@PathVariable  String taiKhoan, UpdateaccountDto updateaccountDto) {
        Account existingAccount = accountRepository.findById(taiKhoan)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Không tìm thấy tài khoản với username: " + taiKhoan));

        existingAccount.setMatKhau(updateaccountDto.getMatKhau());
        existingAccount.setTrangThai(updateaccountDto.getTrangThai());

        Account updatedAccount = accountRepository.save(existingAccount);
        return convertToDto(updatedAccount);
    }

    public void deleteAccount(String taiKhoan) {
        if (!accountRepository.existsById(taiKhoan)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Không tìm thấy tài khoản với username: " + taiKhoan);
        }
        accountRepository.deleteById(taiKhoan);
    }

    public List<AccountDto> getAllAccounts() {
        return accountRepository.findAll().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    public AccountDto getAccountByTaiKhoan(String taiKhoan) {
        return accountRepository.findById(taiKhoan)
                .map(this::convertToDto)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Không tìm thấy tài khoản với username: " + taiKhoan));
    }

    public boolean existsByTaiKhoan(String taiKhoan) {
        return accountRepository.existsByTaiKhoan(taiKhoan);
    }

    public List<AccountDto> searchAccounts(String keyword) {
        return accountRepository.searchByTaiKhoan(keyword).stream()
                .map(account -> convertToDto(account))
                .collect(Collectors.toList());
    }

}