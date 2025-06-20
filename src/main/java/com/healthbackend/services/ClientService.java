package com.healthbackend.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.healthbackend.entities.Client;
import com.healthbackend.repositories.ClientRepository;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ClientService {

    private final ClientRepository clientRepository;

    @Autowired
    public ClientService(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    // ✅ Add new client
    public Client addClient(Client client) {
        return clientRepository.save(client);
    }

    // ✅ Get all clients
    public List<Client> getAllClients() {
        return clientRepository.findAll();
    }

    // ✅ Get client by ID
    public Optional<Client> getClientById(Long id) {
        return clientRepository.findById(id);
    }

    // ✅ Update existing client
    public Client updateClient(Long id, Client updatedClient) {
        return clientRepository.findById(id)
                .map(existingClient -> {
                    existingClient.setTitle(updatedClient.getTitle());
                    existingClient.setFirstName(updatedClient.getFirstName());
                    existingClient.setSurname(updatedClient.getSurname());
                    existingClient.setGender(updatedClient.getGender());
                    existingClient.setDateOfBirth(updatedClient.getDateOfBirth());
                    existingClient.setAddress(updatedClient.getAddress());
                    existingClient.setPostCode(updatedClient.getPostCode());
                    existingClient.setEmail(updatedClient.getEmail());
                    existingClient.setTelephone(updatedClient.getTelephone());
                    existingClient.setMobile(updatedClient.getMobile());
                    return clientRepository.save(existingClient);
                })
                .orElseThrow(() -> new RuntimeException("Client not found with ID: " + id));
    }

    // ✅ Delete client by ID
    public void deleteClient(Long id) {
        if (clientRepository.existsById(id)) {
            clientRepository.deleteById(id);
        } else {
            throw new RuntimeException("Client not found with ID: " + id);
        }
    }
}
