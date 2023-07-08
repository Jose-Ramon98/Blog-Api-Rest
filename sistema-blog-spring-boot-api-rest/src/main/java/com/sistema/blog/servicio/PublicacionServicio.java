package com.sistema.blog.servicio;

import com.sistema.blog.dto.PublicacionDTO;


public interface PublicacionServicio {

	public PublicacionDTO crearPublicación (PublicacionDTO publicacionDTO);
	
	public java.util.List<PublicacionDTO> obtenerTodasLasPublicaciones(int numeroDePagina, int medidaDePagina);
	
	public PublicacionDTO obtenerPublicacionPorId(long id);
	
	public PublicacionDTO actualizarPublicacion(PublicacionDTO publicacionDTO, long id);
	
	 public void eliminarPublicacion(long id);
	 
	 
	 
	 
	 
	 
	 
	 
	 
}
