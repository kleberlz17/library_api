package kleberlz.libraryapi.service;

import org.springframework.stereotype.Service;

import kleberlz.libraryapi.repository.LivroRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class LivroService {
	
	private final LivroRepository repository;

}
