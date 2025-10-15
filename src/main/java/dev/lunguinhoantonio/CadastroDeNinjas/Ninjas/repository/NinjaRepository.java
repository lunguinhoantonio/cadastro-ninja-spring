package dev.lunguinhoantonio.CadastroDeNinjas.Ninjas.repository;
import dev.lunguinhoantonio.CadastroDeNinjas.Ninjas.model.NinjaModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NinjaRepository extends JpaRepository<NinjaModel, Long> {
    boolean existsByEmail(String email);
}