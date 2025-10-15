package dev.lunguinhoantonio.CadastroDeNinjas.Missoes.repository;
import dev.lunguinhoantonio.CadastroDeNinjas.Missoes.model.MissoesModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MissoesRepository extends JpaRepository<MissoesModel, Long> {
}