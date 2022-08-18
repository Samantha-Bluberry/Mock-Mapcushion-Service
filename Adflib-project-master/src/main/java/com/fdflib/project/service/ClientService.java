package com.fdflib.project.service;

import com.fdflib.project.model.Client;
import com.fdflib.model.entity.FdfEntity;
import com.fdflib.model.util.SqlStatement;
import com.fdflib.model.util.WhereClause;
import com.fdflib.service.impl.FdfCommonServices;

import java.util.List;

public class ClientService extends FdfCommonServices {

    public Client saveClient(Client client) {
        if(client != null) {
            //make sure required elements are present
            if (client.clientOrgName != "" ) {
                return this.save(Client.class, client).current;
            }
        }
        return null;
    }

    public Client getClientById(long id) {
        return getClientWithHistoryById(id).current;

    }

    public FdfEntity<Client> getClientWithHistoryById(long id) {
        FdfEntity<Client> client = new FdfEntity<>();

        // get the test
        if(id >= 0) {
            client = this.getEntityById(Client.class, id);
        }

        return client;
    }

    public List<Client> getListOfClients(){
        WhereClause idClause = new WhereClause();
        idClause.name = "id";
        idClause.operator = WhereClause.Operators.NOT_EQUAL;
        idClause.value = ""+-1;
        idClause.valueDataType = Long.class;

        List<Client> clientList = SqlStatement.build().where(idClause).run(Client.class);
        return clientList;
    }
}
