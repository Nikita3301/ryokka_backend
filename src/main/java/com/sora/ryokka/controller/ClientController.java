package com.sora.ryokka.controller;

import com.sora.ryokka.dto.request.CreateClientRequest;
import com.sora.ryokka.dto.response.ClientDataResponse;
import com.sora.ryokka.exception.ResourceNotFoundException;
import com.sora.ryokka.model.Client;
import com.sora.ryokka.service.ClientService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/clients")
public class ClientController {

    private final ClientService clientService;

    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }

    @PostMapping
    public ResponseEntity<ClientDataResponse> createClient(@RequestBody CreateClientRequest createClientRequest) {
        ClientDataResponse newClientResponse = clientService.createClient(createClientRequest);
        return new ResponseEntity<>(newClientResponse, HttpStatus.CREATED);
    }

    @PutMapping("/{clientId}")
    public ResponseEntity<ClientDataResponse> updateClient(@PathVariable Long clientId, @RequestBody Client client) {
        Client updatedClient = clientService.updateClient(clientId, client);
        ClientDataResponse response = new ClientDataResponse(updatedClient);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/{clientId}")
    public ResponseEntity<ClientDataResponse> getClientById(@PathVariable Long clientId) {
        Client client = clientService.getClientById(clientId).orElseThrow(() -> new ResourceNotFoundException("Client not found"));
        ClientDataResponse response = new ClientDataResponse(client);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<ClientDataResponse>> getAllClients() {
        List<Client> clients = clientService.getAllClients();
        List<ClientDataResponse> responses = clients.stream().map(ClientDataResponse::new).collect(Collectors.toList());
        return new ResponseEntity<>(responses, HttpStatus.OK);
    }

    @GetMapping("/without-projects")
    public ResponseEntity<List<ClientDataResponse>> getClientsWithoutProjects() {
        List<Client> clientsWithoutProjects = clientService.getClientsWithoutProjects();
        List<ClientDataResponse> response = clientsWithoutProjects.stream().map(ClientDataResponse::new).collect(Collectors.toList());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/{clientId}")
    public ResponseEntity<?> deleteClient(@PathVariable Long clientId) {
        return clientService.deleteClient(clientId);
    }
}

