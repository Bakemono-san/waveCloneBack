package org.odc.demo.Services.Impl;

import org.odc.demo.Datas.Entity.Plannification;
import org.odc.demo.Datas.Entity.Transaction;
import org.odc.demo.Datas.Entity.UserEntity;
import org.odc.demo.Datas.Enums.TransactionType;
import org.odc.demo.Datas.Repository.PlannificationRepository;
import org.odc.demo.Datas.Repository.TransactionRepository;
import org.odc.demo.Datas.Repository.UserRepository;
import org.odc.demo.Services.Interfaces.CrudService;
import org.odc.demo.Web.Dtos.Request.DeplafonnementDto;
import org.odc.demo.Web.Dtos.Request.TransactionDto;
import org.odc.demo.Web.Dtos.Request.TransactionUpdateDto;
import org.odc.demo.Web.Dtos.Request.TransfertGroupeDto;
import org.odc.demo.Web.Dtos.Response.ApiResponse;
import org.odc.demo.Web.Dtos.UserDetailImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@Transactional
public class TransactionServiceImpl implements CrudService<Transaction, TransactionDto, TransactionUpdateDto> {

    @Autowired
    TransactionRepository transactionRepository;

    @Autowired
    PlannificationServiceImpl plannificationService;

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PlannificationRepository plannificationRepository;


    @Override
    public ApiResponse<Transaction> Create(TransactionDto transactionDto) {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        UserEntity connectedUser = ((UserDetailImpl) authentication.getPrincipal()).getUserEntity();

        System.out.println(connectedUser);
        if(connectedUser.getAccount().getSolde() < transactionDto.getMontant()){
            return new ApiResponse<Transaction>(null,"votre solde est insuffisant pour effectuer ce transaction");
        }

        if((transactionDto.getType().equals("Depot") || transactionDto.getType().equals("Retrait")) && connectedUser.getRole().getLibelle() != "Distributeur"){
            return new ApiResponse<>(null,"vous n'etes pas authoriser a effectuer cette operation");
        }

        Optional<UserEntity> receiver = userRepository.findByTelephone(transactionDto.getReceiverTelephone());

        if(receiver.get().getAccount().isPlafonnee() || receiver.get().getAccount().getSommeDepot() + transactionDto.getMontant() > receiver.get().getAccount().getPlafond()){
            return new ApiResponse<Transaction>(null,"veuillez lui demander de deplafonner son compte! pour pouvoir recevoir de transaction");
        }

        if(!receiver.isPresent()){
            return  new ApiResponse<>(null,"le receveur n'existe pas");
        }

        Transaction transaction = new Transaction();
        transaction.setDate(transactionDto.getDate());
        transaction.setMontant(transactionDto.getMontant());
        transaction.setType(transactionDto.getType());
        transaction.setAgentTelephone(transactionDto.getAgentTelephone());
        transaction.setReceiverTelephone(transactionDto.getReceiverTelephone());
        transaction.setSenderTelephone(connectedUser.getTelephone());

        Transaction savedTransaction = transactionRepository.save(transaction);

        receiver.get().getAccount().setSolde(receiver.get().getAccount().getSolde() + transactionDto.getMontant());
        connectedUser.getAccount().setSolde(connectedUser.getAccount().getSolde() - transactionDto.getMontant());


        receiver.get().getAccount().setSommeDepot(connectedUser.getAccount().getSommeDepot() + transactionDto.getMontant());
        receiver.get().getAccount().setPlafonnee(receiver.get().getAccount().getSommeDepot() - receiver.get().getAccount().getPlafond() == 0);

        userRepository.save(receiver.get());
        userRepository.save(connectedUser);

        return new ApiResponse<Transaction>(savedTransaction,"transaction effectuee avec success");
    }

    @Override
    public ApiResponse<Transaction> Update(Long id, TransactionUpdateDto dto) {
        Transaction transaction = transactionRepository.findById(id).orElse(null);
        if (transaction == null) {
            return null;
        }

        transaction.setAnnulee(dto.isAnnulee());

        return new ApiResponse<Transaction>(transactionRepository.save(transaction),"transaction mis a jour avec success");
    }

    @Override
    public ApiResponse<Transaction> Delete(Long id) {
        Transaction transaction = transactionRepository.findById(id).orElse(null);
        if (transaction == null) {
            return new ApiResponse<Transaction>(null,"ce transaction n'existe pas");
        }
        transactionRepository.delete(transaction);
        return new ApiResponse<Transaction>(transaction,"transaction supprimee avec success");
    }

    @Override
    public ApiResponse<List<Transaction>> findAll() {
        List<Transaction> transactions = transactionRepository.findAll();
        return new ApiResponse<>(transactions, "transactions retrouvee avec success");
    }

    @Override
    public ApiResponse<Transaction> findById(Long id) {
        Optional<Transaction> transaction = transactionRepository.findById(id);

        if(!transaction.isPresent()){
            return new ApiResponse<Transaction>(null,"transaction n'existe pas");
        }

        return new ApiResponse(transaction,"transaction retrouvee avec success");
    }


    public List<Plannification> TransfertProgramme(){
        List<Plannification> plannifications= plannificationRepository.findAll();
        plannifications.forEach(plannification -> {
            Transaction transaction = new Transaction();
            transaction.setReceiverTelephone(plannification.getReceiverTelephone());
            transaction.setSenderTelephone(plannification.getSenderTelephone());
            transaction.setMontant(plannification.getMontant());
            transaction.setDate(new Date());

            transactionRepository.save(transaction);
            plannificationService.Delete(plannification.getId());
        });

        return plannifications;
    }

    public ApiResponse<Map<String, List<String>>> transfertGroupe(TransfertGroupeDto datas) {
        if (datas.getIds().length <= 0) {
            return new ApiResponse<>(null, "Il faut au minimum un utilisateur");
        }

        List<String> userNotExist = new ArrayList<>();
        List<String> userExist = new ArrayList<>();
        List<String> soldeInsufisant = new ArrayList<>();

        float montant = datas.getMontant();

        Arrays.stream(datas.getIds()) // Stream over the ids array
                .forEach(id -> {
                    // Check if the user with the id exists
                    boolean userExists = userRepository.findByTelephone(id).isPresent();


                    if (userExists) {
                        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

                        UserEntity connectedUser = ((UserDetailImpl) authentication.getPrincipal()).getUserEntity();

                        System.out.println(connectedUser);
                        if(connectedUser.getAccount().getSolde() < montant){
                            soldeInsufisant.add(id);
                        }else {

                        UserEntity receiver = userRepository.findByTelephone(id).orElse(null);

                            assert receiver != null;
                            receiver.getAccount().setSolde(receiver.getAccount().getSolde() + montant);
                            connectedUser.getAccount().setSolde(connectedUser.getAccount().getSolde() - montant);

                            userRepository.save(connectedUser);
                            userRepository.save(receiver);

                        // Handle the logic for when the user exists, e.g., process the user
                        Transaction transaction = new Transaction();
                        transaction.setDate(new Date());
                        transaction.setMontant(montant);
                        transaction.setType(TransactionType.Transfert);
                        transaction.setReceiverTelephone(id);
                        transaction.setSenderTelephone(connectedUser.getTelephone());
                        transactionRepository.save(transaction);
                        userExist.add(id);
                        }
                    } else {
                        // Handle the case when the user doesn't exist
                        userNotExist.add(id);
                        System.out.println("User with ID " + id + " does not exist.");
                    }
                });

        Map<String, List<String>> resultMap = new HashMap<>();
        resultMap.put("reussie", userExist);
        resultMap.put("echouee", userNotExist);
        resultMap.put("soldeInsufisant", soldeInsufisant);

        return new ApiResponse<>(resultMap, "Transfert effectuee :=  reussie : "+resultMap.get("reussie").size() + " , utilisateur non trouvee : "+resultMap.get("echouee").size() +
                " , echoue avec solde insufisant : "+resultMap.get("soldeInsufisant").size());
    }

    public ApiResponse deplafonnerCompte(DeplafonnementDto daplafonnementDto){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        UserEntity connectedUser = ((UserDetailImpl) authentication.getPrincipal()).getUserEntity();

        System.out.println(connectedUser.getRole().getLibelle() + " == Distributeur  = "+connectedUser.getRole().getLibelle().equals("Distributeur"));

        if(!connectedUser.getRole().getLibelle().equals("Distributeur")){
            return new ApiResponse(null, "vous n'etes pas autoriser a effectuer cette action ");
        }

        UserEntity client = userRepository.findByTelephone(daplafonnementDto.getNumero()).orElse(null);

        client.getAccount().setPlafond(client.getAccount().getPlafond() * 2);
        client.getAccount().setPlafonnee(false);

        return new ApiResponse<>(client.getAccount(),"compte deplafonnee avec success");

    }

    public ApiResponse<List<Transaction>> findMyTransactions() {


        List<Transaction> transactions = transactionRepository.findByReceiverTelephoneOrSenderTelephone("+785953562", "+221785953562").orElse(null);

        if (transactions == null) {
            return new ApiResponse<>(null, "No transactions found for this user.");
        }

        return new ApiResponse<>(transactions, "Transactions retrieved successfully.");
    }

}
