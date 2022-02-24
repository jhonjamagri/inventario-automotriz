package com.inventario.app.controller;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.inventario.app.entity.Producto;
import com.inventario.app.service.ProductoService;

@RestController
@RequestMapping("/api/productos")
public class ProductoController {
	
	@Autowired
	private ProductoService productoService;
	
	//crear un nuevo producto
	
	@PostMapping
	public ResponseEntity<?> create (@RequestBody Producto producto){
		return ResponseEntity.status(HttpStatus.CREATED).body(productoService.save(producto));
	}
	
	// leer o selecionar producto
	
	@GetMapping("/{id}")
	public ResponseEntity<?> read (@PathVariable (value = "id") Long productoId){
		Optional<Producto> oProducto = productoService.findById(productoId);
		
		if(!oProducto.isPresent()) {
			return ResponseEntity.notFound().build();
		}
		
		return ResponseEntity.ok(oProducto);
		
	}
	
	//Actualisar o editar producto
	
	@PutMapping("/{id}")
	public ResponseEntity<?> update (@RequestBody Producto productoDetails, @PathVariable(value = "id") Long productoId) {
		Optional<Producto> producto = productoService.findById(productoId);
		
		if(!producto.isPresent()) {
			return ResponseEntity.notFound().build();
		}
		
		producto.get().setNombre(productoDetails.getNombre());
		producto.get().setNombreRegistro(productoDetails.getNombreRegistro());
		producto.get().setReferencia(productoDetails.getReferencia());
		producto.get().setMarca(productoDetails.getMarca());
		producto.get().setCantidad(productoDetails.getCantidad());
		producto.get().setEnabled(productoDetails.getEnabled());
		
		return ResponseEntity.status(HttpStatus.CREATED).body(productoService.save(producto.get()));
		
	}
	
	//Eliminar un producto
	
	@DeleteMapping("/{id}")
	
	public ResponseEntity<?> delete (@PathVariable(value = "id") Long productoId) {
		
		if(!productoService.findById(productoId).isPresent()) {
			
			return ResponseEntity.notFound().build();
			
		}
		
		productoService.deleteById(productoId);
		return ResponseEntity.ok().build();
		
	}
	
	// leer todos los productos 

	@GetMapping
	public List<Producto> readAll () {
		
		List<Producto> productos = StreamSupport
				.stream(productoService.findAll().spliterator(), false)
				.collect(Collectors.toList());
		
		return productos;
	}
	
	
	
}
