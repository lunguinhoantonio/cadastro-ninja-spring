package dev.lunguinhoantonio.CadastroDeNinjas.Ninjas.controller;
import dev.lunguinhoantonio.CadastroDeNinjas.Ninjas.dto.NinjaDTO;
import dev.lunguinhoantonio.CadastroDeNinjas.Ninjas.service.NinjaService;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/ninjas/ui")
public class NinjaControllerUI {
    private final NinjaService ninjaService;

    public NinjaControllerUI(NinjaService ninjaService) {
        this.ninjaService = ninjaService;
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
        if (ninja !=null) {
            model.addAttribute("ninja", ninja);
            return "Ninja/detalhesNinja";
        } else {
            model.addAttribute("mensagem", "Ninja não encontrado");
            return "Ninja/listarNinjas";
        }
    }

    @GetMapping("/criar")
    public String mostrarFormularioAdicionarNinja(Model model) {
        model.addAttribute("ninja", new NinjaDTO());
        return "Ninja/adicionarNinja";
    }

    @PostMapping("/salvar")
    public String salvarNinja(@ModelAttribute NinjaDTO ninja, Model model) {
        try {
            NinjaDTO ninjaSalvo = ninjaService.criarNinja(ninja);

            if (ninjaSalvo == null) {
                model.addAttribute("mensagem", "Erro: Este email já está cadastrado!");
                model.addAttribute("ninja", ninja);
                return "Ninja/adicionarNinja";
            }

            model.addAttribute("mensagem", "Ninja cadastrado com sucesso!");
            return "redirect:/ninjas/ui/listar";

        } catch (DataIntegrityViolationException e) {
            model.addAttribute("mensagem", "Erro: Este email já está cadastrado!");
            model.addAttribute("ninja", ninja);
            return "Ninja/adicionarNinja";
        } catch (Exception e) {
            model.addAttribute("mensagem", "Erro ao cadastrar ninja: " + e.getMessage());
            model.addAttribute("ninja", ninja);
            return "Ninja/adicionarNinja";
        }
    }
}