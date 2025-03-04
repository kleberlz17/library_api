package kleberlz.libraryapi.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import kleberlz.libraryapi.model.Client;
import kleberlz.libraryapi.repository.ClientRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ClientService {
	
	private final ClientRepository repository;
	private final PasswordEncoder encoder;
	
	public Client salvar(Client client) {
		var senhaCriptografada = encoder.encode(client.getClientSecret());
		client.setClientSecret(senhaCriptografada);
		return repository.save(client);
	}
	
	public Client obterPorClientID(String clientId) {
		return repository.findByClientId(clientId);
	}

}
