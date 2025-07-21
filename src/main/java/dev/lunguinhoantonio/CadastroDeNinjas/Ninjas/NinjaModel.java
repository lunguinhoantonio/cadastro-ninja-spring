package dev.lunguinhoantonio.CadastroDeNinjas.Ninjas;

import dev.lunguinhoantonio.CadastroDeNinjas.Missoes.MissoesModel;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "tb_cadastro")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class NinjaModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private int idade;
    private String nome;
    private String email;
    @ManyToOne
    @JoinColumn(name = "missoes_id")
    private MissoesModel missoes;
}
