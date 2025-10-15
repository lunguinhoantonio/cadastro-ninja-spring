package dev.lunguinhoantonio.CadastroDeNinjas.Ninjas.dto;
import dev.lunguinhoantonio.CadastroDeNinjas.Missoes.model.MissoesModel;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NinjaDTO {
    private Long id;

    @NotNull(message = "Idade é obrigatória")
    @Min(value = 1, message = "Idade mínima é 1 ano")
    @Max(value = 120, message = "Idade máxima é 120 anos")
    private int idade;

    @NotBlank(message = "Nome é obrigatório")
    @Size(min = 2, max = 100, message = "Nome deve ter entre 2 e 100 caracteres")
    private String nome;

    @NotBlank(message = "Email é obrigatório")
    @Email(message = "Email inválido")
    private String email;

    @NotBlank(message = "URL da imagem é obrigatória")
    @Size(max = 255, message = "a URL deve ter até 255 caracteres!")
    private String imgUrl;

    @Size(min = 4, max = 50, message = "Rank deve ter entre 4 e 50 caracteres")
    private String rank;

    private MissoesModel missoes;
}