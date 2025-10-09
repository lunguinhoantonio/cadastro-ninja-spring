package dev.lunguinhoantonio.CadastroDeNinjas.Missoes.dto;
import dev.lunguinhoantonio.CadastroDeNinjas.Ninjas.model.NinjaModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MissoesDTO {
    private Long id;
    private String nome;
    private String dificuldade;
    private List<NinjaModel> ninjas;
}
