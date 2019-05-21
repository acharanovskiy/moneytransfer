package edu.andrew.rest;

import edu.andrew.dao.AccountRepositoryImpl;
import edu.andrew.model.Account;
import edu.andrew.service.MoneyTransferService;
import edu.andrew.service.MoneyTransferServiceImpl;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;
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
        mtService.transfer(request.getSenderAccount(), request.getReceiverAccount(), request.getAmount());
        return Response.ok().build();
    }
}
