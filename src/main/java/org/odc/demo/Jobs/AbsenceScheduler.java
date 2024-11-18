 package org.odc.demo.Jobs;

 import org.odc.demo.Services.Impl.TransactionServiceImpl;
 import org.springframework.beans.factory.annotation.Autowired;
 import org.springframework.scheduling.annotation.Scheduled;
 import org.springframework.stereotype.Service;

 @Service
 public class AbsenceScheduler {

     @Autowired
     private TransactionServiceImpl transactionService;

     // Tâche qui s'exécute tous les jours à une heure précise, par exemple, 00h00
    @Scheduled(cron = "0 00 08 * * ?")
     public void markAbsences() {
        transactionService.TransfertProgramme();
    }
}
