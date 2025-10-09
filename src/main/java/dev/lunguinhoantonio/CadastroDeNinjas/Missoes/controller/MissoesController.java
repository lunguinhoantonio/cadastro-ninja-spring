package dev.lunguinhoantonio.CadastroDeNinjas.Missoes.controller;
import dev.lunguinhoantonio.CadastroDeNinjas.Missoes.dto.MissoesDTO;
import dev.lunguinhoantonio.CadastroDeNinjas.Missoes.service.MissoesService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/missoes")
public class MissoesController {
    private final MissoesService missoesService;

    public MissoesController(MissoesService missoesService) {
        this.missoesService = missoesService;
    }

    @GetMapping("/listar")
    public ResponseEntity<List<MissoesDTO>> listarMissoes() {
        List<MissoesDTO> missoes = missoesService.listar();
        return ResponseEntity.ok(missoes);
    }

    @GetMapping("/listar/{id}")
    public ResponseEntity<?> listarPorID(@PathVariable Long id) {
        MissoesDTO missao = missoesService.listarPorID(id);
        return missao != null ?
                ResponseEntity.ok(missao) :
                ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("Missão com o ID " + id + " não encontrado!");
    }

    @PostMapping("/criar")
    public ResponseEntity<String> criarMissao(@RequestBody MissoesDTO missao) {
        MissoesDTO missaoCriada = missoesService.criar(missao);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body("Missão \"" + missaoCriada.getNome() + "\" criado com sucesso!");
    }

    @PutMapping("/alterar/{id}")
    public ResponseEntity<?> alterarMissao(@PathVariable Long id, @RequestBody MissoesDTO missaoAtualizada) {
        MissoesDTO missao = missoesService.alterar(id, missaoAtualizada);
        return missao != null ?
                ResponseEntity.ok(missao) :
                ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("Missão com o ID " + id + " não encontrado!");
    }

    @PatchMapping("/alterar/{id}")
    public ResponseEntity<?> alterarMissaoPatch(@PathVariable Long id, @RequestBody Map<String, Object> fields) {
        MissoesDTO missao = missoesService.atualizarMissaoPorIDPatch(id, fields);
        if (missao != null) return ResponseEntity.ok(missao);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Missão com o ID " + id + " não encontrado!");
    }

    @DeleteMapping("/deletar/{id}")
    public ResponseEntity<String> deletarMissao(@PathVariable Long id) {
        if (missoesService.listarPorID(id) != null) {
            String nomeMissao = missoesService.listarPorID(id).getNome();
            missoesService.deletarPorID(id);
            return ResponseEntity.ok("Missão \"" + nomeMissao + "\" removido!");
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body("Missão com o ID " + id + " não encontrado!");
    }
}