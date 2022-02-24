package com.inventario.app.service;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.inventario.app.entity.Producto;

public interface ProductoService {
	
	public Iterable<Producto> findAll();
	
	public Page<Producto> findAll(Pageable pageable);
	
	public Optional<Producto> findById(Long id);
	
	public Producto save(Producto producto);
	
	public void deleteById(Long id);

}
