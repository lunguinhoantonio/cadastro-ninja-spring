package dev.lunguinhoantonio.CadastroDeNinjas.Missoes.dto;
import dev.lunguinhoantonio.CadastroDeNinjas.Ninjas.model.NinjaModel;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MissoesDTO {
    private Long id;

    @NotBlank(message = "Nome da missão é obrigatório")
    @Size(min = 3, max = 100, message = "Nome deve ter entre 3 e 100 caracteres")
    private String nome;

    @NotBlank(message = "Dificuldade é obrigatória")
    @Pattern(regexp = "^(Fácil|Média|Difícil|S-Rank)$", message = "Dificuldade deve ser: Fácil, Média, Difícil ou S-Rank")
    private String dificuldade;
    private List<NinjaModel> ninjas;
}
