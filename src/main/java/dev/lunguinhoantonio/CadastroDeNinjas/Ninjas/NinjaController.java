package dev.lunguinhoantonio.CadastroDeNinjas.Ninjas;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/ninjas")
public class NinjaController {

    private NinjaService ninjaService;

    public NinjaController(NinjaService ninjaService) {
        this.ninjaService = ninjaService;
    }

    @GetMapping("/boasvindas")
    public String welcome() {
        return "<span style='color: blue; background-color: black;'><strong>Olá Mundo!</strong></span>";
    }

    @PostMapping("/criar")
    public String criarNinja() {
        return "Ninja criado";
    }

    @GetMapping("/listar")
    public List<NinjaModel> listarNinjas() {
        return ninjaService.listarNinjas();
    }

    @GetMapping("/listarID")
    public String listarNinjasPorId() {
        return "Mostrar ninja por ID";
    }

    @PutMapping("/alterar")
    public String alterarNinjaPorId() {
        return "Alterar ninja por ID";
    }

    @DeleteMapping("/deletarID")
    public String deletarNinjaPorId() {
        return "Ninja deletado por ID";
    }
}
