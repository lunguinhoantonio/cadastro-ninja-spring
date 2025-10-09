package dev.lunguinhoantonio.CadastroDeNinjas.Ninjas.controller;
import dev.lunguinhoantonio.CadastroDeNinjas.Ninjas.dto.NinjaDTO;
import dev.lunguinhoantonio.CadastroDeNinjas.Ninjas.service.NinjaService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/ninjas")
public class NinjaController {

    private final NinjaService ninjaService;

    public NinjaController(NinjaService ninjaService) {
        this.ninjaService = ninjaService;
    }

    @GetMapping("/boasvindas")
    public String welcome() {
        return "<span style='color: blue; background-color: black;'><strong>Olá Mundo!</strong></span>";
    }

    @PostMapping("/criar")
    public ResponseEntity<String> criarNinja(@RequestBody NinjaDTO ninja) {
        NinjaDTO novoNinja = ninjaService.criarNinja(ninja);
        return ResponseEntity.status(HttpStatus.CREATED).body("Ninja criado com sucesso: " + novoNinja.getNome() + " (ID): " + novoNinja.getId());
    }

    @GetMapping("/listar")
    public ResponseEntity<List<NinjaDTO>> listarNinjas() {
        List<NinjaDTO> ninjas = ninjaService.listarNinjas();
        return ResponseEntity.ok(ninjas);
    }

    @GetMapping("/listar/{id}")
    public ResponseEntity<?> listarNinjasPorId(@PathVariable Long id) {
        NinjaDTO ninja = ninjaService.listarNinjasPorID(id);
        if (ninja != null) {
            return ResponseEntity.status(HttpStatus.FOUND).body(ninja);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Ninja com o ID " + id + " não encontrado!");
    }

    @PutMapping("/alterar/{id}")
    public ResponseEntity<?> alterarNinjaPorId(@PathVariable Long id, @RequestBody NinjaDTO ninjaAtualizado) {
        NinjaDTO ninja = ninjaService.atualizarNinja(id, ninjaAtualizado);
        if (ninja != null) {
            return ResponseEntity.ok(ninja);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Ninja com o ID " + id + " não encontrado!");
    }

    @PatchMapping("/alterar/{id}")
    public ResponseEntity<?> partialUpdate(@PathVariable Long id, @RequestBody Map<String, Object> fields) {
        NinjaDTO ninja = ninjaService.atualizarNinjaPorIdPatch(id, fields);
        if (ninja != null) return ResponseEntity.ok(ninja);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Ninja com o ID " + id + " não encontrado!");
    }

    @DeleteMapping("/deletar/{id}")
    public ResponseEntity<String> deletarNinjaPorId(@PathVariable Long id) {
        if (ninjaService.listarNinjasPorID(id) != null) {
            ninjaService.deletarNinjaPorId(id);
            return ResponseEntity.ok("Ninja com o ID " + id + " deletado com sucesso!");
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Ninja com o ID " + id + " não encontrado!");
    }
}
