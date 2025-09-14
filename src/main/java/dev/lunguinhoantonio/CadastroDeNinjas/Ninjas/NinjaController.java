package dev.lunguinhoantonio.CadastroDeNinjas.Ninjas;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping
public class NinjaController {

    @GetMapping("/boasvindas")
    public String welcome() {
        return "<span style='color: blue; background-color: black;'><strong>Olá Mundo!</strong></span>";
    }

    @PostMapping("/adicionar")
    public String criarNinja() {
        return "Ninja criado";
    }

    @GetMapping("/todos")
    public String mostrarTodosOsNinjas() {
        return "Mostrar ninjas";
    }

    @GetMapping("/todosID")
    public String mostrarTodosOsNinjasPorId() {
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
