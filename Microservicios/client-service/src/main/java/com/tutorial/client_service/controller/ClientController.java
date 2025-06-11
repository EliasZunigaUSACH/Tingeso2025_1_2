package com.tutorial.client_service.controller;

import com.tutorial.client_service.entity.Client;
import com.tutorial.client_service.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/clients")
@CrossOrigin("*")
public class ClientController {

    @Autowired
    ClientService clientService;

    @GetMapping("/")
    public ResponseEntity<List<Client>> getAll() {
        List<Client> clients = clientService.getAll();
        if(clients.isEmpty())
            return ResponseEntity.noContent().build();
        return ResponseEntity.ok(clients);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Client> getById(@PathVariable("id") int id) {
        Client client = clientService.getClientById(id);
        if(client == null)
            return ResponseEntity.notFound().build();
        return ResponseEntity.ok(client);
    }

    @PostMapping("/")
    public ResponseEntity<Client> save(@RequestBody Client client) {
        Client clientNew = clientService.save(client);
        return ResponseEntity.ok(clientNew);
    }

    @PutMapping("/")
    public ResponseEntity<Client> update(@RequestBody Client client) {
        Client clientNew = clientService.update(client);
        return ResponseEntity.ok(clientNew);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> delete(@PathVariable("id") int id) {
        boolean result = clientService.delete(id);
        return ResponseEntity.ok(result);
    }
}
