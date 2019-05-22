package edu.andrew.rest;

import edu.andrew.TransferFailedException;
import edu.andrew.dao.AccountRepositoryImpl;
import edu.andrew.model.Account;
import edu.andrew.service.MoneyTransferService;
import edu.andrew.service.MoneyTransferServiceImpl;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.math.BigDecimal;
import java.util.List;

@Path("/rest/account")
public class MoneyTransferResource {
    private MoneyTransferService mtService;

    public MoneyTransferResource() {
        this.mtService = new MoneyTransferServiceImpl(new AccountRepositoryImpl());
    }

    @GET
    @Produces("application/json")
    public List<Account> getAll() {
        return new AccountRepositoryImpl().getAllAccounts();
    }

    @POST
    @Consumes("application/json")
    public Response moneyTransfer(MoneyTransferRequest request) {
        try {
            Account sender = mtService.findBy(request.getSenderAccount());
            Account receiver = mtService.findBy(request.getReceiverAccount());
            validateRequest(sender, receiver, request.getAmount());
            mtService.transfer(sender, receiver, request.getAmount());
            return Response.ok("Funds successfully transferred.").build();
        } catch (TransferFailedException e) {
            return Response.serverError().entity(e.getMessage()).build();
        }
    }

    private void validateRequest(Account sender, Account receiver, BigDecimal amount) throws TransferFailedException {
        if (sender == null){
            throw new TransferFailedException("Sender account doesn't exist");
        }
        if (receiver == null) {
            throw new TransferFailedException("Receiver account doesn't exist");
        }
        if (sender.getFunds().compareTo(amount) < 0) {
            throw new TransferFailedException("Not enough funds for this transfer");
        }
        if (amount.equals(BigDecimal.ZERO)) {
            throw new TransferFailedException("Amount cannot be zero");
        }
    }
}
