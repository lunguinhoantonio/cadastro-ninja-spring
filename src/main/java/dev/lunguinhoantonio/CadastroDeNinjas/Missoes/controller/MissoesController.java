package dev.lunguinhoantonio.CadastroDeNinjas.Missoes.controller;
import dev.lunguinhoantonio.CadastroDeNinjas.Missoes.dto.MissoesDTO;
import dev.lunguinhoantonio.CadastroDeNinjas.Missoes.service.MissoesService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
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

    @Operation(
        summary = "Listar todas as missões",
        description = "Retorna uma lista completa de todas as missões cadastradas no sistema",
        tags = {"Missões"},
        responses = {
            @ApiResponse(responseCode = "200", description = "Lista de missões retornada com sucesso")
        }
    )
    @GetMapping("/listar")
    public ResponseEntity<List<MissoesDTO>> listarMissoes() {
        List<MissoesDTO> missoes = missoesService.listar();
        return ResponseEntity.ok(missoes);
    }

    @Operation(
        summary = "Buscar missão por ID",
        description = "Retorna os detalhes de uma missão específica através do seu ID",
        tags = {"Missões"},
        responses = {
            @ApiResponse(responseCode = "200", description = "Missão encontrada com sucesso"),
            @ApiResponse(responseCode = "404", description = "Missão não encontrada")
        }
    )
    @GetMapping("/listar/{id}")
    public ResponseEntity<?> listarPorID(
        @Parameter(description = "ID da missão a ser buscada", required = true, example = "1")
        @PathVariable Long id
    ) {
        MissoesDTO missao = missoesService.listarPorID(id);
        return missao != null ?
                ResponseEntity.ok(missao) :
                ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("Missão com o ID " + id + " não encontrado!");
    }

    @Operation(
        summary = "Criar nova missão",
        description = "Cadastra uma nova missão no sistema",
        tags = {"Missões"},
        responses = {
            @ApiResponse(responseCode = "201", description = "Missão criada com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos")
        }
    )
    @PostMapping("/criar")
    public ResponseEntity<String> criarMissao(
        @Parameter(description = "Dados da missão a ser criada", required = true)
        @RequestBody MissoesDTO missao
    ) {
        MissoesDTO missaoCriada = missoesService.criar(missao);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body("Missão \"" + missaoCriada.getNome() + "\" criado com sucesso!");
    }

    @Operation(
        summary = "Atualizar missão completa",
        description = "Atualiza todos os dados de uma missão existente através do ID",
        tags = {"Missões"},
        responses = {
            @ApiResponse(responseCode = "200", description = "Missão atualizada com sucesso"),
            @ApiResponse(responseCode = "404", description = "Missão não encontrada")
        }
    )
    @PutMapping("/alterar/{id}")
    public ResponseEntity<?> alterarMissao(
        @Parameter(description = "ID da missão a ser atualizada", required = true, example = "1")
        @PathVariable Long id,
        @Parameter(description = "Dados atualizados da missão", required = true)
        @RequestBody MissoesDTO missaoAtualizada
    ) {
        MissoesDTO missao = missoesService.alterar(id, missaoAtualizada);
        return missao != null ?
                ResponseEntity.ok(missao) :
                ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("Missão com o ID " + id + " não encontrado!");
    }

    @Operation(
        summary = "Atualizar missão parcialmente",
        description = "Atualiza campos específicos de uma missão existente através do ID",
        tags = {"Missões"},
        responses = {
            @ApiResponse(responseCode = "200", description = "Missão atualizada parcialmente com sucesso"),
            @ApiResponse(responseCode = "404", description = "Missão não encontrada")
        }
    )
    @PatchMapping("/alterar/{id}")
    public ResponseEntity<?> alterarMissaoPatch(
        @Parameter(description = "ID da missão a ser atualizada", required = true, example = "1")
        @PathVariable Long id,
        @Parameter(description = "Mapa de campos a serem atualizados", required = true)
        @RequestBody Map<String, Object> fields
    ) {
        MissoesDTO missao = missoesService.atualizarMissaoPorIDPatch(id, fields);
        if (missao != null) return ResponseEntity.ok(missao);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Missão com o ID " + id + " não encontrado!");
    }

    @Operation(
        summary = "Deletar missão",
        description = "Remove uma missão do sistema através do ID",
        tags = {"Missões"},
        responses = {
            @ApiResponse(responseCode = "200", description = "Missão removida com sucesso"),
            @ApiResponse(responseCode = "404", description = "Missão não encontrada" )
        }
    )
    @DeleteMapping("/deletar/{id}")
    public ResponseEntity<String> deletarMissao(
        @Parameter(description = "ID da missão a ser deletada", required = true, example = "1")
        @PathVariable Long id
    ) {
        if (missoesService.listarPorID(id) != null) {
            String nomeMissao = missoesService.listarPorID(id).getNome();
            missoesService.deletarPorID(id);
            return ResponseEntity.ok("Missão \"" + nomeMissao + "\" removido!");
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body("Missão com o ID " + id + " não encontrado!");
    }
}