package es.dam.marioPerez.payAndGo.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import es.dam.marioPerez.payAndGo.model.ActualizacionPedido;
import es.dam.marioPerez.payAndGo.model.Mesa;
import es.dam.marioPerez.payAndGo.model.Pedido;
import es.dam.marioPerez.payAndGo.model.ProductosPedido;
import es.dam.marioPerez.payAndGo.model.Usuario;
import es.dam.marioPerez.payAndGo.repository.ActualizacionPedidoRepository;
import es.dam.marioPerez.payAndGo.repository.PedidoRepository;
import es.dam.marioPerez.payAndGo.repository.UsuarioRepository;
import es.dam.marioPerez.payAndGo.utils.ActualizacionEnum;
import es.dam.marioPerez.payAndGo.utils.UsuarioRol;

public class PedidoService {

	private static final Logger LOGGER = LogManager.getLogger(PedidoService.class);

	@Autowired
	private PedidoRepository pedidoRepository;
	
	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@Autowired
	private ActualizacionPedidoRepository actualizacionPedidoRepository;
	
	public Pedido crearPedido(long usuarioId, Pedido pedido) {
        
		Optional<Usuario> usuarioOpt = usuarioRepository.findById(usuarioId);
		
		if (usuarioOpt.isEmpty()) {
			throw new RuntimeException("El usuario no existe");
		}
		Usuario usuario = usuarioOpt.get();
		
		if (usuario.getRol().equals(UsuarioRol.CLIENTE)) {
			pedido.setAsignadoCamarero(false);
		}
		
		pedido.setFechaApertura(LocalDateTime.now());
		
		List<ProductosPedido> productosPedido = pedido.getProductosPedidos();
		
		float importe = 0;
		for (ProductosPedido prodPedido: productosPedido) {
			importe = importe + prodPedido.getProducto().getPrecio() * prodPedido.getCantidad();
		}
		pedido.setImporte(importe);
		pedido.setPagado(false);
		pedido.setAnulado(false);
		
		Pedido pedidoBD = pedidoRepository.save(pedido);
		
		ActualizacionPedido actualizacion = new ActualizacionPedido();
		actualizacion.setFecha(LocalDateTime.now());
		actualizacion.setPedido(pedidoBD);
		actualizacion.setUsuario(usuario);
		actualizacion.setMotivo(ActualizacionEnum.CREAR);
		
		actualizacionPedidoRepository.save(actualizacion);
		
		
		return pedidoBD;
	}
	
	public Page<Pedido> obtenerTodosLosPedidos (Pageable pageable){
		LOGGER.trace("Accediendo a la lectura de Pedidos");
		
		return pedidoRepository.findAll(pageable);
	}
	
	public Optional<Pedido> obtenerPedidoPorId(long id) {
		LOGGER.trace("Accediendo a la lectura de Pedidos por id");
		
		return pedidoRepository.findById(id);
	}
	
	public Pedido obtenerPedidoPorMesaId(Mesa mesa) {
		LOGGER.trace("Accediendo a la lectura de Pedidos por mesa id");
		
		Optional<Pedido> pedidoOpt = pedidoRepository.findByMesaActivos(mesa, LocalDateTime.now().toLocalDate());
		
		if (pedidoOpt.isEmpty()) {
			return null;
		}else {
			return pedidoOpt.get();
		}
	}
	
	public List<Pedido> findPedidoClientes(){
		LOGGER.trace("Accediendo a la lectura de Pedidos de clientes sin atender");
		
		return pedidoRepository.findPedidosClientes(LocalDateTime.now().toLocalDate());

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
			
		pedidoBD.setProductosPedidos(pedido.getProductosPedidos());
		
		float importe = 0;
		
		for (ProductosPedido prodPedido: pedidoBD.getProductosPedidos()) {
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
		Pedido pedidoBD = pedidoOpt.get();
		pedidoBD.setPagado(true);
		pedidoBD.setFormaPago(pedido.getFormaPago());
		pedidoBD.setFechaCierre(LocalDateTime.now());
		
		Optional<Usuario> usuarioOpt = usuarioRepository.findById(usuarioId);
		if (usuarioOpt.isEmpty()) {
			throw new RuntimeException("El usuario no existe");
		}
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
		Pedido pedidoBD = pedidoOpt.get();
		pedidoBD.setAnulado(true);
		pedidoBD.setFechaCierre(LocalDateTime.now());

		
		Optional<Usuario> usuarioOpt = usuarioRepository.findById(usuarioId);
		if (usuarioOpt.isEmpty()) {
			throw new RuntimeException("El usuario no existe");
		}
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
