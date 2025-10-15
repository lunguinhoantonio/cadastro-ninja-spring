package dev.lunguinhoantonio.CadastroDeNinjas.Ninjas.controller;
import dev.lunguinhoantonio.CadastroDeNinjas.Ninjas.dto.NinjaDTO;
import dev.lunguinhoantonio.CadastroDeNinjas.Ninjas.service.NinjaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
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

    @Operation(
        summary = "Mensagem de boas-vindas",
        description = "Retorna uma mensagem de boas vindas estilizada",
        tags = {"Ninjas"}
    )
    @GetMapping("/boasvindas")
    public String welcome() {
        return "<span style='color: blue; background-color: black;'><strong>Olá Mundo!</strong></span>";
    }

    @Operation(
        summary = "Criar um novo ninja",
        description = "Cadastra um novo ninja no sistema",
        tags = {"Ninjas"},
        responses = {
            @ApiResponse(responseCode = "201", description = "Ninja criado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos")
        }
    )
    @PostMapping("/criar")
    public ResponseEntity<String> criarNinja(
        @Parameter(description = "Dados do ninja a ser criado", required = true)
        @RequestBody NinjaDTO ninja
    ) {
        NinjaDTO novoNinja = ninjaService.criarNinja(ninja);
        return ResponseEntity.status(HttpStatus.CREATED).body("Ninja criado com sucesso: " + novoNinja.getNome() + " (ID): " + novoNinja.getId());
    }

    @Operation(
        summary = "Listar todos os ninjas",
        description = "Retorna uma lista completa de todos os ninjas cadastrados no sistema",
        tags = {"Ninjas"},
        responses = {
            @ApiResponse(responseCode = "200", description = "Lista de ninjas retoirnada com sucesso")
        }
    )
    @GetMapping("/listar")
    public ResponseEntity<List<NinjaDTO>> listarNinjas() {
        List<NinjaDTO> ninjas = ninjaService.listarNinjas();
        return ResponseEntity.ok(ninjas);
    }

    @Operation(
        summary = "Buscar ninjas por ID",
        description = "Retorna os detalhes de um ninja específico através de seu ID",
        tags = {"Ninjas"},
        responses = {
            @ApiResponse(responseCode = "302", description = "Ninja encontrado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Ninja não encontrado")
        }
    )
    @GetMapping("/listar/{id}")
    public ResponseEntity<?> listarNinjasPorId(
        @Parameter(description = "ID do ninja a ser buscado", required = true, example = "1")
        @PathVariable Long id
    ) {
        NinjaDTO ninja = ninjaService.listarNinjasPorID(id);
        if (ninja != null) {
            return ResponseEntity.status(HttpStatus.FOUND).body(ninja);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Ninja com o ID " + id + " não encontrado!");
    }

    @Operation(
        summary = "Atualizar ninja por completo",
        description = "Atualiza todos os dados de um ninja existente de uma vez através do ID",
        tags = {"Ninjas"},
        responses = {
            @ApiResponse(responseCode = "200", description = "Ninja atualizado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Ninja não encontrado")
        }
    )
    @PutMapping("/alterar/{id}")
    public ResponseEntity<?> alterarNinjaPorId(
        @Parameter(description = "ID do ninja a ser atualizado", required = true, example = "1")
        @PathVariable Long id,
        @Parameter(description = "Dados atualizados do ninja", required = true)
        @RequestBody NinjaDTO ninjaAtualizado
    ) {
        NinjaDTO ninja = ninjaService.atualizarNinja(id, ninjaAtualizado);
        if (ninja != null) {
            return ResponseEntity.ok(ninja);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Ninja com o ID " + id + " não encontrado!");
    }
    @Operation(
        summary = "Atualizar ninja parcialmente",
        description = "Atualiza campos específicos de um ninja existente através do ID",
        tags = {"Ninjas"},
        responses = {
            @ApiResponse(responseCode = "200", description = "Ninja atualizado parcialmente com sucesso"),
            @ApiResponse(responseCode = "404", description = "Ninja não encontrado")
        }
    )
    @PatchMapping("/alterar/{id}")
    public ResponseEntity<?> partialUpdate(
        @Parameter(description = "ID do ninja a ser atualizado", required = true, example = "1")
        @PathVariable Long id,
        @Parameter(description = "Mapa de campos a serem atualizados", required = true)
        @RequestBody Map<String, Object> fields
    ) {
        NinjaDTO ninja = ninjaService.atualizarNinjaPorIdPatch(id, fields);
        if (ninja != null) return ResponseEntity.ok(ninja);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Ninja com o ID " + id + " não encontrado!");
    }

    @Operation(
        summary = "Deletar ninja",
        description = "Remove um ninja do sistema através do ID",
        tags = {"Ninjas"},
        responses = {
            @ApiResponse(responseCode = "200", description = "Ninja deletado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Ninja não encontrado")
        }
    )
    @DeleteMapping("/deletar/{id}")
    public ResponseEntity<String> deletarNinjaPorId(
        @Parameter(description = "ID do ninja a ser deletado", required = true, example = "1")
        @PathVariable Long id
    ) {
        if (ninjaService.listarNinjasPorID(id) != null) {
            ninjaService.deletarNinjaPorId(id);
            return ResponseEntity.ok("Ninja com o ID " + id + " deletado com sucesso!");
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Ninja com o ID " + id + " não encontrado!");
    }
}
