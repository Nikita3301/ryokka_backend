package com.sora.ryokka.service;

import com.sora.ryokka.model.Client;
import java.util.List;
import java.util.Optional;

public interface ClientService {
    List<Client> getAllClients();
    Optional<Client> getClientById(int id);
    Client createClient(Client client);
    Client updateClient(int id, Client client);
    void deleteClient(int id);
}
