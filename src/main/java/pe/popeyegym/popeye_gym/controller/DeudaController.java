package pe.popeyegym.popeye_gym.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import pe.popeyegym.popeye_gym.model.Cliente;
import pe.popeyegym.popeye_gym.model.Deuda;
import pe.popeyegym.popeye_gym.repository.ClienteRepository;
import pe.popeyegym.popeye_gym.repository.DeudaRepository;

import java.util.Optional;

@Controller
@RequestMapping("/deudas")
public class DeudaController {

    @Autowired
    private DeudaRepository deudaRepository;

    @Autowired
    private ClienteRepository clienteRepository;

    @GetMapping("/editar/{clienteid}")
    String editarDeuda(@PathVariable Integer clienteid, Model model){

        Optional<Deuda> deudaFromDB = deudaRepository.findByClienteClienteid(clienteid);

        if (deudaFromDB.isPresent()){
            model.addAttribute("deuda", deudaFromDB.get());
            return "deuda";
        } else {
            return "redirect:/clientes";
        }
    }

    @PostMapping("/editar/{clienteid}")
    String actualizarDeuda(@PathVariable Integer clienteid, Deuda deuda, RedirectAttributes ra){

        Cliente cliente = clienteRepository.findById(clienteid).orElseThrow(()-> new IllegalArgumentException("Cliente no encontrado"));

        Optional<Deuda> deudaOptional = deudaRepository.findByClienteClienteid(clienteid);

        Deuda newdeuda;

        if (deudaOptional.isPresent()) {
            newdeuda = deudaOptional.get();
            newdeuda.setCantidad(deuda.getCantidad());
            newdeuda.setEstado(deuda.getEstado());
        } else {
            newdeuda = deuda;
            newdeuda.setCliente(cliente);
        }

        deudaRepository.save(newdeuda);
        ra.addFlashAttribute("msgExito", "Deuda actualizada");

        return "redirect:/clientes";
    }
}
