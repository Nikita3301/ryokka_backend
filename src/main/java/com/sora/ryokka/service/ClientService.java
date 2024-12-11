package com.sora.ryokka.service;

import com.sora.ryokka.dto.request.CreateClientRequest;
import com.sora.ryokka.dto.response.ClientDataResponse;
import com.sora.ryokka.model.Client;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

public interface ClientService {
    List<Client> getAllClients();
    List<Client> getClientsWithoutProjects();
    Optional<Client> getClientById(Long id);
    ClientDataResponse createClient(CreateClientRequest createClientRequest);
    Client updateClient(Long id, Client client);
    ResponseEntity<?> deleteClient(Long id);
}
