package dev.lunguinhoantonio.CadastroDeNinjas.Missoes;

import dev.lunguinhoantonio.CadastroDeNinjas.Ninjas.NinjaModel;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "tb_missoes")
public class MissoesModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    private String dificuldade;
    @OneToMany(mappedBy = "missoes")
    private List<NinjaModel> ninjas;
}
