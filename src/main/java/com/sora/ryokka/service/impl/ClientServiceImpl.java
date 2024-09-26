package com.sora.ryokka.service.impl;

import com.sora.ryokka.model.Client;
import com.sora.ryokka.repository.ClientRepository;
import com.sora.ryokka.service.ClientService;
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
    public Optional<Client> getClientById(int clientId) {
        return clientRepository.findById(clientId);
    }

    @Override
    public Client createClient(Client client) {
        return clientRepository.save(client);
    }

    @Override
    public Client updateClient(int clientId, Client updatedClient) {
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
    public void deleteClient(int clientId) {
        clientRepository.deleteById(clientId);
    }
}

