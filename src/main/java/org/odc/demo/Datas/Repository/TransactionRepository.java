package org.odc.demo.Datas.Repository;

import org.odc.demo.Datas.Entity.Transaction;
import org.odc.utils.Generics.Repositories.SoftDeleteRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TransactionRepository extends SoftDeleteRepository<Transaction,Long> {
    List<Transaction> findTransactionByReceiverTelephoneEqualsOrSenderTelephoneEquals(String Telephone, String SenderTelephone);
    Optional<List<Transaction>> findByReceiverTelephoneOrSenderTelephone(String receiverTelephone, String senderTelephone);

}
