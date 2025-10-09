package dev.lunguinhoantonio.CadastroDeNinjas.Ninjas.dto;
import dev.lunguinhoantonio.CadastroDeNinjas.Missoes.model.MissoesModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NinjaDTO {
    private Long id;
    private int idade;
    private String nome;
    private String email;
    private String imgUrl;
    private String rank;
    private MissoesModel missoes;
}