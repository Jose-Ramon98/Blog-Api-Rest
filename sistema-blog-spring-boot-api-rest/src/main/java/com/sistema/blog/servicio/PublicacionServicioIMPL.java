package com.sistema.blog.servicio;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.sistema.blog.dto.PublicacionDTO;
import com.sistema.blog.entidades.Publicacion;
import com.sistema.blog.excepciones.ResourceNotFoundException;
import com.sistema.blog.repositorio.PublicacionRepositorio;

@Service
public class PublicacionServicioIMPL implements PublicacionServicio {

	@Autowired
	private PublicacionRepositorio publicacionRepositorio;

	@Override
	public PublicacionDTO crearPublicación(PublicacionDTO publicacionDTO) {
		Publicacion publicacion = mapearEntidad(publicacionDTO);
		Publicacion nuevaPublicacion = publicacionRepositorio.save(publicacion);

		PublicacionDTO publicacionRespuesta = mapearDTO(nuevaPublicacion);
		return publicacionRespuesta;
	}

	@Override
	public List<PublicacionDTO> obtenerTodasLasPublicaciones(int numeroDePagina, int medidaDePagina) {
		Pageable pageable = PageRequest.of(numeroDePagina, medidaDePagina) ;
		
		Page<Publicacion> publicaciones = publicacionRepositorio.findAll(pageable);
		
		List<Publicacion> listaDePublicaciones = publicaciones.getContent();
		return listaDePublicaciones.stream().map(publicacion -> mapearDTO(publicacion)).collect(Collectors.toList());

	}

	// Convertir entidad a DTO
	private PublicacionDTO mapearDTO(Publicacion publicacion) {
		PublicacionDTO publicacionDTO = new PublicacionDTO();
		publicacionDTO.setId(publicacion.getId());
		publicacionDTO.setTitulo(publicacion.getTitulo());
		publicacionDTO.setDescripcion(publicacion.getDescripcion());
		publicacionDTO.setContenido(publicacion.getContenido());
		return publicacionDTO;
	}

	// Convertir de DTO a entidad
	private Publicacion mapearEntidad(PublicacionDTO publicacionDTO) {
		Publicacion publicacion = new Publicacion();
		publicacion.setId(publicacionDTO.getId());
		publicacion.setTitulo(publicacionDTO.getTitulo());
		publicacion.setDescripcion(publicacionDTO.getDescripcion());
		publicacion.setContenido(publicacionDTO.getContenido());
		return publicacion;
	}

	@Override
	public PublicacionDTO obtenerPublicacionPorId(long id) {
		Publicacion publicacion = publicacionRepositorio.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("publicacion", "id", id));
		return mapearDTO(publicacion);
	}

	@Override
	public PublicacionDTO actualizarPublicacion(PublicacionDTO publicacionDTO, long id) {
		Publicacion publicacion = publicacionRepositorio.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Publicacion", "id", id));

		publicacion.setTitulo(publicacionDTO.getTitulo());
		publicacion.setDescripcion(publicacionDTO.getDescripcion());
		publicacion.setContenido(publicacionDTO.getContenido());

		Publicacion publicacionActualizada = publicacionRepositorio.save(publicacion);
		return mapearDTO(publicacionActualizada);
	}
	@Override
	public void eliminarPublicacion(long id) {
	    Publicacion publicacion = publicacionRepositorio.findById(id)
	            .orElseThrow(() -> new ResourceNotFoundException("Publicacion", "id", id));
	    publicacionRepositorio.delete(publicacion);
	}
}




