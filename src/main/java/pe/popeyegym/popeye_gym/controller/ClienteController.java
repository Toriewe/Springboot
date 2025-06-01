package pe.popeyegym.popeye_gym.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import pe.popeyegym.popeye_gym.model.Cliente;
import pe.popeyegym.popeye_gym.model.Deuda;
import pe.popeyegym.popeye_gym.model.Membresia;
import pe.popeyegym.popeye_gym.repository.ClienteRepository;
import pe.popeyegym.popeye_gym.repository.DeudaRepository;


import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/clientes")
public class ClienteController {

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private DeudaRepository deudaRepository;

    @GetMapping("")
    String index(@RequestParam(defaultValue = "0") int page, Model model){

        PageRequest pageable = PageRequest.of(page, 10, Sort.by("clienteid").descending());
        Page<Cliente> clientePage = clienteRepository.findAll(pageable);

        model.addAttribute("clientesPage", clientePage);
        model.addAttribute("paginaActual", page);
        return "index";

    }

    @GetMapping("/nuevo")
    String nuevo(Model model){

        Cliente cliente = new Cliente();
        cliente.setMembresia(new Membresia());

        model.addAttribute("cliente", cliente);
        model.addAttribute("tiposMembresia", List.of("1 MES", "3 MESES", "6 MESES"));
        return "nuevo";
    }

    @PostMapping("/nuevo")
    String guardar(Model model, Cliente cliente){

        cliente.setFeinscripcion(LocalDate.now());

        if (cliente.getMembresia() != null){
            cliente.getMembresia().setCliente(cliente);
        }

        Cliente clienteGuardado = clienteRepository.save(cliente);

        Deuda deuda = new Deuda();
        deuda.setEstado(false);
        deuda.setCantidad(0);
        deuda.setCliente(clienteGuardado);

        deudaRepository.save(deuda);
        return "redirect:/clientes";
    }

    @GetMapping("/editar/{clienteid}")
    String editar(Model model, @PathVariable Integer clienteid){

        Cliente cliente = clienteRepository.findById(clienteid)
                .orElseThrow(() -> new IllegalArgumentException("Cliente no encontrado"));

        model.addAttribute("tiposMembresia", List.of("1 MES", "3 MESES", "6 MESES"));

        // Si el cliente no tiene membresia, creamos una vacía para evitar null en la vista
        if (cliente.getMembresia() == null) {
            cliente.setMembresia(new Membresia());
        } else {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

            if (cliente.getMembresia().getFeinicio() != null) {
                String feinicioStr = cliente.getMembresia().getFeinicio().format(formatter);
                model.addAttribute("feinicioStr", feinicioStr);
            }

            if (cliente.getMembresia().getFefin() != null) {
                String fefinStr = cliente.getMembresia().getFefin().format(formatter);
                model.addAttribute("fefinStr", fefinStr);
            }
        }

        model.addAttribute("cliente", cliente);

        return "editar";
    }

    @PostMapping("/editar/{clienteid}")
    public String actualizar(Model model,
                             @PathVariable Integer clienteid,
                             @RequestParam("nombre") String nombre,
                             @RequestParam("apellido") String apellido,
                             @RequestParam("telef") Integer telef,
                             @RequestParam("email") String email,
                             @RequestParam("dni") Integer dni,
                             @RequestParam("detalles") String detalles,
                             @RequestParam("estado") Boolean estado,
                             @RequestParam("membresia.tipo") String tipo,
                             @RequestParam("membresia.feinicio") String feinicioStr,
                             @RequestParam("membresia.fefin") String fefinStr,
                             @RequestParam("renovacion") Integer renovacion,
                             RedirectAttributes ra) {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

        LocalDate feinicio;
        LocalDate fefin;

        try {
            feinicio = LocalDate.parse(feinicioStr, formatter);
            fefin = LocalDate.parse(fefinStr, formatter);
        } catch (DateTimeParseException e) {
            ra.addFlashAttribute("msgError", "Formato de fecha inválido. Use dd-MM-yyyy");
            return "redirect:/clientes/editar/" + clienteid;
        }

        Cliente c = clienteRepository.findById(clienteid)
                .orElseThrow(() -> new IllegalArgumentException("Cliente no encontrado"));

        c.setNombre(nombre);
        c.setApellido(apellido);
        c.setTelef(telef);
        c.setEmail(email);
        c.setDni(dni);
        c.setDetalles(detalles);
        c.setEstado(estado);
        c.setRenovacion(renovacion);

        if (c.getMembresia() == null) {
            c.setMembresia(new Membresia());
        }

        c.getMembresia().setTipo(tipo);
        c.getMembresia().setFeinicio(feinicio);
        c.getMembresia().setFefin(fefin);
        c.getMembresia().setCliente(c);

        clienteRepository.save(c);
        ra.addFlashAttribute("msgExito", "Cliente actualizado");
        return "redirect:/clientes";
    }

    @PostMapping("/eliminar/{clienteid}")
    String eliminar(@PathVariable Integer id, RedirectAttributes ra){

        clienteRepository.deleteById(id);
        ra.addFlashAttribute("msgExito", "Cliente eliminado");
        return "redirect:/clientes";
    }

    @GetMapping("/clientes/buscar")
    String buscarDNI(@RequestParam("dni") Integer dni, Model model){
        Optional<Cliente> cliente = clienteRepository.findByDni(dni);

        if (cliente.isPresent()){
            List<Cliente> resultado = new ArrayList<>();
            resultado.add(cliente.get());
            model.addAttribute("clientes", resultado);
        } else {
            model.addAttribute("clientes", new ArrayList<>());
            model.addAttribute("msgError", "No se encontro cliente");
        }

        return "clientes";
    }

}
