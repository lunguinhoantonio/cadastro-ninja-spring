package dev.lunguinhoantonio.CadastroDeNinjas.Ninjas.controller;
import dev.lunguinhoantonio.CadastroDeNinjas.Missoes.dto.MissoesDTO;
import dev.lunguinhoantonio.CadastroDeNinjas.Missoes.service.MissoesService;
import dev.lunguinhoantonio.CadastroDeNinjas.Ninjas.dto.NinjaDTO;
import dev.lunguinhoantonio.CadastroDeNinjas.Ninjas.service.NinjaService;
import jakarta.validation.Valid;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/ninjas/ui")
public class NinjaControllerUI {
    private final NinjaService ninjaService;
    private final MissoesService missoesService;

    public NinjaControllerUI(NinjaService ninjaService, MissoesService missoesService) {
        this.ninjaService = ninjaService;
        this.missoesService = missoesService;
    }

    @GetMapping("/listar")
    public String listarNinjas(Model model) {
        List<NinjaDTO> ninjas = ninjaService.listarNinjas();
        model.addAttribute("ninjas", ninjas);
        return "Ninja/listarNinjas";
    }

    @GetMapping("/deletar/{id}")
    public String deletarNinjaPorId(@PathVariable Long id) {
        ninjaService.deletarNinjaPorId(id);
        return "redirect:/ninjas/ui/listar";
    }

    @GetMapping("/listar/{id}")
    public String listarNinjasPorId(@PathVariable Long id, Model model) {
        NinjaDTO ninja = ninjaService.listarNinjasPorID(id);
        if (ninja != null) {
            model.addAttribute("ninja", ninja);
            return "Ninja/detalhesNinja";
        } else {
            model.addAttribute("mensagem", "Ninja não encontrado");
            return "Ninja/listarNinjas";
        }
    }

    @GetMapping("/alterar/{id}")
    public String exibirFormularioAlterarNinja(@PathVariable Long id, Model model) {
        NinjaDTO ninjaDTO = ninjaService.listarNinjasPorID(id);
        if (ninjaDTO != null) {
            model.addAttribute("ninja", ninjaDTO);
            List<MissoesDTO> todasMissoes = missoesService.listar();
            model.addAttribute("todasMissoes", todasMissoes);
            return "Ninja/alterarNinja";
        }
        model.addAttribute("mensagem", "Missão não encontrada!");
        return "redirect:/ninjas/ui/listar";
    }

    @PostMapping("/atualizar")
    public String atualizarNinja(@ModelAttribute NinjaDTO ninjaDTO, Model model) {
        try {
            NinjaDTO ninjaAtualizado = ninjaService.atualizarNinja(ninjaDTO.getId(), ninjaDTO);

            if (ninjaAtualizado != null) {
                model.addAttribute("mensagem", "Ninja atualizado com sucesso!");
                return "redirect:/ninjas/ui/listar";
            }

            model.addAttribute("mensagem", "Erro ao atualizar ninja!");
            return "Ninja/alterarNinja";

        } catch (DataIntegrityViolationException e) {
            model.addAttribute("mensagem", "Erro: Este email já está cadastrado!");
            model.addAttribute("ninja", ninjaDTO);

            List<MissoesDTO> todasMissoes = missoesService.listar();
            model.addAttribute("todasMissoes", todasMissoes);

            return "Ninja/alterarNinja";
        }
    }

    @GetMapping("/criar")
    public String mostrarFormularioAdicionarNinja(Model model) {
        model.addAttribute("ninja", new NinjaDTO());
        return "Ninja/adicionarNinja";
    }

    @PostMapping("/salvar")
    public String salvarNinja(@Valid @ModelAttribute("ninja") NinjaDTO ninja, BindingResult result, Model model) {
        if (result.hasErrors()) return "Ninja/adicionarNinja";
        try {
            NinjaDTO ninjaSalvo = ninjaService.criarNinja(ninja);

            if (ninjaSalvo == null) {
                model.addAttribute("mensagem", "Ocorreu um erro!");
                return "Ninja/adicionarNinja";
            }

            model.addAttribute("mensagem", "Ninja cadastrado com sucesso!");
            return "redirect:/ninjas/ui/listar";

        } catch (DataIntegrityViolationException e) {
            model.addAttribute("mensagem", "Ocorreu um erro!");
            return "Ninja/adicionarNinja";
        } catch (Exception e) {
            model.addAttribute("mensagem", "Erro ao cadastrar ninja: " + e.getMessage());
            return "Ninja/adicionarNinja";
        }
    }
}