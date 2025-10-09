package dev.lunguinhoantonio.CadastroDeNinjas.Missoes;
import dev.lunguinhoantonio.CadastroDeNinjas.Ninjas.NinjaDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/missao/ui")
public class MissoesControllerUI {
    private final MissoesService missoesService;

    public MissoesControllerUI(MissoesService missoesService) {
        this.missoesService = missoesService;
    }

    @GetMapping("/listar")
    public String listarMissoes(Model model) {
        List<MissoesDTO> missoes = missoesService.listar();
        model.addAttribute("missoes", missoes);
        return "Missao/listarMissoes";
    }

    @GetMapping("/deletar/{id}")
    public String deletarMissaoPorId(@PathVariable Long id) {
        missoesService.deletarPorID(id);
        return "redirect:/missao/ui/listar";
    }

    @GetMapping("/criar")
    public String mostrarFormularioMissao(Model model) {
        model.addAttribute("missoes", new MissoesDTO());
        return "Missao/adicionarMissao";
    }

    @GetMapping("/alterar/{id}")
    public String exibirFormularioAlterar(@PathVariable Long id, Model model) {
        MissoesDTO missao = missoesService.listarPorID(id);
        if (missao != null) {
            model.addAttribute("missoes", missao);
            return "Missao/alterarMissao";
        }
        model.addAttribute("mensagem", "Missão não encontrada!");
        return "redirect:/missao/ui/listar";
    }

    @PostMapping("/atualizar")
    public String atualizarMissao(@ModelAttribute MissoesDTO missoes, Model model) {
        MissoesDTO missaoAtualizada = missoesService.alterar(missoes.getId(), missoes);
        if (missaoAtualizada != null) {
            model.addAttribute("mensagem", "Missão atualizada com sucesso!");
            return "redirect:/missao/ui/listar";
        }
        model.addAttribute("mensagem", "Erro ao atualizar missão!");
        return "Missao/alterarMissao";
    }

    @PostMapping("/salvar")
    public String salvarMissao(@ModelAttribute MissoesDTO missao, RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute("mensagem", "Missão cadastrada com sucesso!");
        missoesService.criar(missao);
        return "redirect:/missao/ui/listar";
    }
}
