package com.sora.ryokka.service.impl;

import com.sora.ryokka.dto.request.CreateClientRequest;
import com.sora.ryokka.dto.response.ClientDataResponse;
import com.sora.ryokka.exception.ResourceNotFoundException;
import com.sora.ryokka.model.Client;
import com.sora.ryokka.repository.ClientRepository;
import com.sora.ryokka.service.ClientService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ClientServiceImpl implements ClientService {

    private final ClientRepository clientRepository;

    public ClientServiceImpl(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    @Override
    public List<Client> getAllClients() {
        return clientRepository.findAll();
    }

    @Override
    public List<Client> getClientsWithoutProjects() {
        return clientRepository.findAll().stream()
                .filter(client -> client.getProjects() == null || client.getProjects().isEmpty())
                .collect(Collectors.toList());
    }

    @Override
    public Optional<Client> getClientById(Long clientId) {
        return clientRepository.findById(clientId);
    }

    @Override
    public ClientDataResponse createClient(CreateClientRequest createClientRequest) {
        Client newClient = new Client();
        newClient.setFirstName(createClientRequest.firstName());
        newClient.setLastName(createClientRequest.lastName());
        newClient.setContactEmail(createClientRequest.contactEmail());
        newClient.setContactPhone(createClientRequest.contactPhone());

        newClient = clientRepository.save(newClient);

        return new ClientDataResponse(newClient);
    }



    @Override
    public Client updateClient(Long clientId, Client updatedClient) {
        return clientRepository.findById(clientId)
                .map(client -> {
                    client.setFirstName(updatedClient.getFirstName());
                    client.setLastName(updatedClient.getLastName());
                    client.setContactPhone(updatedClient.getContactPhone());
                    client.setContactEmail(updatedClient.getContactEmail());
                    return clientRepository.save(client);
                })
                .orElseThrow(() -> new RuntimeException("Client not found"));
    }

    @Override
    public ResponseEntity<?> deleteClient(Long clientId) {
        if (!clientRepository.existsById(clientId)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Client not found with id: " + clientId);
        }
        clientRepository.deleteById(clientId);
        return ResponseEntity.ok("Client deleted successfully");
    }
}

