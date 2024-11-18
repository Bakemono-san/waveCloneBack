package org.odc.demo.Web.Controller.Impl;

import org.odc.demo.Datas.Entity.Transaction;
import org.odc.demo.Datas.Entity.UserEntity;
import org.odc.demo.Datas.Enums.TransactionType;
import org.odc.demo.Datas.Repository.TransactionRepository;
import org.odc.demo.Datas.Repository.UserRepository;
import org.odc.demo.Services.Impl.TransactionServiceImpl;
import org.odc.demo.Web.Controller.Interfaces.CrudController;
import org.odc.demo.Web.Dtos.Request.DeplafonnementDto;
import org.odc.demo.Web.Dtos.Request.TransactionDto;
import org.odc.demo.Web.Dtos.Request.TransactionUpdateDto;
import org.odc.demo.Web.Dtos.Request.TransfertGroupeDto;
import org.odc.demo.Web.Dtos.Response.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.odc.demo.utils.DateComparison;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/Transaction")
public class TransactionController implements CrudController<Transaction, TransactionDto, TransactionUpdateDto> {

    @Autowired
    TransactionServiceImpl transactionService;

    @Autowired
    UserController userController;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private TransactionRepository transactionRepository;

    @Override
    @PostMapping
    public ApiResponse Create(@RequestBody TransactionDto transactionDto) {
        return transactionService.Create(transactionDto) ;
    }

    @PostMapping("/paiements")
    public ApiResponse CreatePaiements(@RequestBody TransactionDto transactionDto) {
        transactionDto.setType(TransactionType.Paiement);
        return transactionService.Create(transactionDto);
    }

    @Override
    @PutMapping("/{id}")
    public ApiResponse Update(@PathVariable Long id,@RequestBody TransactionUpdateDto dtoUpdate) {
        return transactionService.Update(id, dtoUpdate);
    }

    @Override
    @GetMapping
    public ApiResponse findAll() {
        return transactionService.findAll();
    }

    @GetMapping("/myTransaction")
    public ApiResponse findMyTransactions(){
        return transactionService.findMyTransactions();
    }

    @Override
    @GetMapping("/{id}")
    public ApiResponse<Transaction> findById(@PathVariable Long id) {
        return transactionService.findById(id);
    }

    @PostMapping("/groupe")
    public ApiResponse<Map<String, List<String>>> TransfertGroupe(@RequestBody TransfertGroupeDto datas){
        System.out.println(datas);
        return transactionService.transfertGroupe(datas);
    }

    @PostMapping("/{id}")
    public ApiResponse<Transaction> AnnulerTransfert(@PathVariable Long id){
        Transaction transaction = transactionRepository.findById(id).orElse(null);

        Transaction trans = transaction;

        if(trans != null){
            if(
            DateComparison.plusDe30mn(trans.getDate().toString())
            ){
                trans.setAnnulee(true);
                UserEntity sender = userRepository.findByTelephone(trans.getSenderTelephone()).orElse(null);
                UserEntity receiver = userRepository.findByTelephone(trans.getReceiverTelephone()).orElse(null);

                assert sender != null;
                sender.getAccount().setSolde(sender.getAccount().getSolde() + trans.getMontant());
                assert receiver != null;
                receiver.getAccount().setSolde(receiver.getAccount().getSolde() - trans.getMontant());

                userRepository.save(sender);
                userRepository.save(receiver);
                transactionRepository.save(trans);

                return new ApiResponse<Transaction>(trans,"transaction annulee avec success");

            }
            return new ApiResponse<>(null, "the transaction cannot be cancelled");
        }
            return new ApiResponse<>(null, "the transaction does not exist");
    }

    @PostMapping("/deplafonner")
    public ApiResponse deplafonnerCompte(@RequestBody DeplafonnementDto number){
        return transactionService.deplafonnerCompte(number);
    }
}
