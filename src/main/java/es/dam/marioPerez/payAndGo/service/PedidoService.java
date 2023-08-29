package es.dam.marioPerez.payAndGo.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.dam.marioPerez.payAndGo.dto.PedidoDto;
import es.dam.marioPerez.payAndGo.model.ActualizacionPedido;
import es.dam.marioPerez.payAndGo.model.Mesa;
import es.dam.marioPerez.payAndGo.model.Pedido;
import es.dam.marioPerez.payAndGo.model.ProductosPedido;
import es.dam.marioPerez.payAndGo.model.Usuario;
import es.dam.marioPerez.payAndGo.repository.ActualizacionPedidoRepository;
import es.dam.marioPerez.payAndGo.repository.MesaRepository;
import es.dam.marioPerez.payAndGo.repository.PedidoRepository;
import es.dam.marioPerez.payAndGo.repository.ProductoRepository;
import es.dam.marioPerez.payAndGo.repository.ProductosPedidoRepository;
import es.dam.marioPerez.payAndGo.repository.UsuarioRepository;
import es.dam.marioPerez.payAndGo.utils.ActualizacionEnum;
import es.dam.marioPerez.payAndGo.utils.DtoTransformer;
import es.dam.marioPerez.payAndGo.utils.UsuarioRol;

@Service
public class PedidoService {

	private static final Logger LOGGER = LogManager.getLogger(PedidoService.class);

	@Autowired
	private ProductosPedidoRepository productosPedidoRepository; 
	
	@Autowired
	private PedidoRepository pedidoRepository;
	
	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@Autowired
	private ActualizacionPedidoRepository actualizacionPedidoRepository;
	
	@Autowired
	private MesaRepository mesaRepository;
	
	@Autowired
	private ProductoRepository productoRepository;
	
	public Pedido crearPedido(long usuarioId, Pedido pedido) {
        
		Optional<Usuario> usuarioOpt = usuarioRepository.findById(usuarioId);
		
		if (usuarioOpt.isEmpty()) {
			throw new RuntimeException("El usuario no existe");
		}
		Usuario usuario = usuarioOpt.get();
				
		if (usuario.getRol().equals(UsuarioRol.CLIENTE)) {
			pedido.setAsignadoCamarero(false);
		}else {
			pedido.setAsignadoCamarero(true);
		}
		pedido.setFechaApertura(LocalDateTime.now());
		pedido.setPagado(false);
		pedido.setAnulado(false);
		
		Pedido pedidoBD = pedidoRepository.save(pedido);
		
		float importe = 0;
		for (ProductosPedido prodPedido: pedido.getProductosPedidos()) {	
			prodPedido.setPedido(pedidoBD);
			prodPedido.setHora(LocalDateTime.now());
			productosPedidoRepository.save(prodPedido);
			
			importe = importe + prodPedido.getProducto().getPrecio() * prodPedido.getCantidad();
		}
		pedidoBD.setImporte(importe);

		ActualizacionPedido actualizacion = new ActualizacionPedido();
		actualizacion.setFecha(LocalDateTime.now());
		actualizacion.setPedido(pedidoBD);
		actualizacion.setUsuario(usuario);
		actualizacion.setMotivo(ActualizacionEnum.CREAR);
		
		actualizacionPedidoRepository.save(actualizacion);
		return pedidoBD;
	}
	
	public List<PedidoDto> obtenerTodosLosPedidos (){
		LOGGER.trace("Accediendo a la lectura de Pedidos");
		
		List<Pedido> pedidos = pedidoRepository.findPedidosActivos();
		
		List<PedidoDto> dtos = new ArrayList<PedidoDto>();
		for (Pedido pedido: pedidos) {
			dtos.add(DtoTransformer.pedidoToDto(pedido));
		}
		return dtos;
	}
	
	public PedidoDto obtenerPedidoPorId(long id) {
		LOGGER.trace("Accediendo a la lectura de Pedidos por id");
		
		Optional<Pedido> pedidoOpt = pedidoRepository.findById(id);
		
		if (!pedidoOpt.isEmpty()) {
			return DtoTransformer.pedidoToDto(pedidoOpt.get());
		}else {
			return null;
		}
	}
	
	public PedidoDto obtenerPedidoPorMesaId(long mesaId) {
		LOGGER.trace("Accediendo a la lectura de Pedidos por mesa id");
		
		Optional<Mesa> mesaOpt = mesaRepository.findById(mesaId);
		
		if (mesaOpt.isEmpty()) {
			throw new RuntimeException("No se ha ecnontrado la mesa");
		}
		Mesa mesa = mesaOpt.get();
		
		Optional<Pedido> pedidoOpt = pedidoRepository.findByMesaActivos(mesa, Date.from(LocalDate.now().atStartOfDay().toInstant(ZoneOffset.UTC)));
		
		if (pedidoOpt.isEmpty()) {
			Pedido pedido = new Pedido();
			pedido.setId(-1);
			pedido.setMesa(mesa);
			pedido.setAnulado(false);
			return DtoTransformer.pedidoToDto(pedido);
		}else {
			return DtoTransformer.pedidoToDto(pedidoOpt.get());
		}
	}
	
	public List<PedidoDto> findPedidoClientes(){
		LOGGER.trace("Accediendo a la lectura de Pedidos de clientes sin atender");
		
		List<Pedido> pedidos =  pedidoRepository.findPedidosClientes(Date.from(LocalDate.now().atStartOfDay().toInstant(ZoneOffset.UTC)));
		
		List<PedidoDto> dtos = new ArrayList<PedidoDto>();
		for (Pedido pedido: pedidos) {
			dtos.add(DtoTransformer.pedidoToDto(pedido));
		}
		return dtos;

	}
	
	public Pedido asignarCamarero(long id) {
		LOGGER.trace("Accediendo a asignar camarero a pedido");

		Optional<Pedido> pedidoOpt = pedidoRepository.findById(id);
		
		if (pedidoOpt.isEmpty()) {
			throw new RuntimeException("no se ha podido encontrar el pedido");
		}
		
		Pedido pedido = pedidoOpt.get();		
		
		if (pedido.isAsignadoCamarero()==true) {
			throw new RuntimeException("El pedido ya ha sido asignado");
		}
			
		pedido.setAsignadoCamarero(true);
		return pedidoRepository.save(pedido);

	}
	
	public Pedido actualizarPedido(long usuarioId, long pedidoId, Pedido pedido) {
	
		LOGGER.trace("Accediendo a actualizar pedido");

		Optional<Pedido> pedidoOpt = pedidoRepository.findById(pedidoId);
		if (pedidoOpt.isEmpty()) {
			throw new RuntimeException("El pedido no existe");
		}
		Pedido pedidoBD = pedidoOpt.get();
		
		Optional<Usuario> usuarioOpt = usuarioRepository.findById(usuarioId);
		if (usuarioOpt.isEmpty()) {
			throw new RuntimeException("El usuario no existe");
		}
		
		Usuario usuario = usuarioOpt.get();
		
		if (usuario.getRol().equals(UsuarioRol.CLIENTE)) {
			pedidoBD.setAsignadoCamarero(false);
		}
					
		float importe = usuario.getRol()==UsuarioRol.CAMARERO? 0 : pedidoBD.getImporte();
		
		for (ProductosPedido prodPedido: pedido.getProductosPedidos()) {	
			prodPedido.setPedido(pedidoBD);
			prodPedido.setHora(LocalDateTime.now());
			productosPedidoRepository.save(prodPedido);
			
			importe = importe + prodPedido.getProducto().getPrecio() * prodPedido.getCantidad();
		}
		pedidoBD.setImporte(importe);
		
		ActualizacionPedido ap = new ActualizacionPedido();
		ap.setFecha(LocalDateTime.now());
		ap.setPedido(pedidoBD);
		ap.setUsuario(usuario);
		ap.setMotivo(UsuarioRol.CLIENTE.equals(usuario.getRol()) ? ActualizacionEnum.ACTUALIZARCLIENTE : ActualizacionEnum.ACTUALIZARCAMARERO );

		pedidoBD.getActualizaciones().add(ap);
		
		return pedidoRepository.save(pedidoBD);
	}
	
	public Pedido pagarPedido(Long usuarioId, Pedido pedido) {
		
		LOGGER.trace("Accediendo a pagar pedido");
		
		Optional<Pedido> pedidoOpt = pedidoRepository.findById(pedido.getId());
		
		if (pedidoOpt.isEmpty()) {
			throw new RuntimeException("El pedido no existe");
		}
		
		Optional<Usuario> usuarioOpt = usuarioRepository.findById(usuarioId);
		if (usuarioOpt.isEmpty()) {
			throw new RuntimeException("El usuario no existe");
		}
		
		Pedido pedidoBD = pedidoOpt.get();
		pedidoBD.setPagado(true);
		pedidoBD.setFechaCierre(LocalDateTime.now());
		
		
		float importe = 0;

		for (ProductosPedido prodPedido: pedido.getProductosPedidos()) {	
			prodPedido.setPedido(pedidoBD);
			if (prodPedido.getProductoPedidoid()==0) {
				prodPedido.setHora(LocalDateTime.now());

			}
			productosPedidoRepository.save(prodPedido);
			
			importe = importe + prodPedido.getProducto().getPrecio() * prodPedido.getCantidad();
		}
		pedidoBD.setImporte(importe);
		
		
				
		Usuario usuario = usuarioOpt.get();
		
		ActualizacionPedido ap = new ActualizacionPedido();
		ap.setFecha(LocalDateTime.now());
		ap.setMotivo(ActualizacionEnum.PAGAR);
		ap.setPedido(pedidoBD);
		ap.setUsuario(usuario);
		pedidoBD.getActualizaciones().add(ap);
		
		return pedidoRepository.save(pedidoBD);
	}
	
	public Pedido anularPedido(Long usuarioId, Long pedidoId) {
		
		LOGGER.trace("Accediendo a anular pedido");
		
		Optional<Pedido> pedidoOpt = pedidoRepository.findById(pedidoId);
		
		if (pedidoOpt.isEmpty()) {
			throw new RuntimeException("El pedido no existe");
		}
		
		Optional<Usuario> usuarioOpt = usuarioRepository.findById(usuarioId);
		if (usuarioOpt.isEmpty()) {
			throw new RuntimeException("El usuario no existe");
		}
		
		Pedido pedidoBD = pedidoOpt.get();
		pedidoBD.setAnulado(true);
		pedidoBD.setFechaCierre(LocalDateTime.now());
		
				
		Usuario usuario = usuarioOpt.get();
		
		ActualizacionPedido ap = new ActualizacionPedido();
		ap.setFecha(LocalDateTime.now());
		ap.setMotivo(ActualizacionEnum.ANULAR);
		ap.setPedido(pedidoBD);
		ap.setUsuario(usuario);
		pedidoBD.getActualizaciones().add(ap);
		
		return pedidoRepository.save(pedidoBD);
	}
	

}
