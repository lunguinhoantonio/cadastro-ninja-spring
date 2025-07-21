package dev.lunguinhoantonio.CadastroDeNinjas.Ninjas;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping
public class NinjaController {

    @GetMapping("/")
    public String welcome() {
        return "<span style='color: blue; background-color: black;'><strong>Olá Mundo!</strong></span>";
    }
}
