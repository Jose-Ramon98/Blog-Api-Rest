
package com.sistema.blog.controlador;

import javax.validation.Valid;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sistema.blog.dto.PublicacionDTO;
import com.sistema.blog.servicio.PublicacionServicio;

@RestController
@RequestMapping("/api/publicaciones")
public class PublicacionControlador {

	@Autowired
	private PublicacionServicio publicacionServicio;

	@GetMapping
	public java.util.List<PublicacionDTO> listarPublicaciones(
			@RequestParam(value = "numeroDePagina", defaultValue = "0", required = false) int numeroPagina,
			@RequestParam(value = "pageSize", defaultValue = "10", required = false) int medidaDePagina) {
		return publicacionServicio.obtenerTodasLasPublicaciones(numeroPagina,medidaDePagina);

	}

	@GetMapping("/{id}")
	public ResponseEntity<PublicacionDTO> obtenerPublicacionPorId(@PathVariable(name = "id") long id) {
		return ResponseEntity.ok(publicacionServicio.obtenerPublicacionPorId(id));

	}

	@PostMapping
	public ResponseEntity<PublicacionDTO> guardarPublicacion(@Valid @RequestBody PublicacionDTO publicacionDTO) {
		return new ResponseEntity<>(publicacionServicio.crearPublicación(publicacionDTO), HttpStatus.CREATED);
	}

	@PutMapping("/{id}")
	public ResponseEntity<PublicacionDTO> actualizarPublicacionPorId(@RequestBody PublicacionDTO publicacionDTO,
			@PathVariable(name = "id") long id) {
		PublicacionDTO publicacionRespuesta = publicacionServicio.actualizarPublicacion(publicacionDTO, id);
		return new ResponseEntity<>(publicacionRespuesta, HttpStatus.OK);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<String> eliminarPublicacion(@PathVariable(name = "id") long id) {
		publicacionServicio.eliminarPublicacion(id);
		return new ResponseEntity<>("Publicacion eliminada con exito", HttpStatus.OK);
	}

}
