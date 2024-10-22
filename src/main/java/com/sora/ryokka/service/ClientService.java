package com.sora.ryokka.service;

import com.sora.ryokka.dto.response.ClientDataResponse;
import com.sora.ryokka.model.Client;

import java.util.List;
import java.util.Optional;

public interface ClientService {
    List<Client> getAllClients();
    List<Client> getClientsWithoutProjects();
    Optional<Client> getClientById(Long id);
    ClientDataResponse createClient(Client client);
    Client updateClient(Long id, Client client);
    void deleteClient(Long id);
}
