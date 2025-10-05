package dev.lunguinhoantonio.CadastroDeNinjas.Missoes;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class MissoesService {
    private final MissoesRepository missoesRepository;

    public MissoesService(MissoesRepository missoesRepository) {
        this.missoesRepository = missoesRepository;
    }

    public List<MissoesModel> listar() {
        return missoesRepository.findAll();
    }

    public MissoesModel listarPorID(Long id) {
        Optional<MissoesModel> missao = missoesRepository.findById(id);
        return missao.orElse(null);
    }

    public MissoesModel criar(MissoesModel missao) {
        return missoesRepository.save(missao);
    }

    public MissoesModel alterar(Long id, MissoesModel missaoAtualizado) {
        if (missoesRepository.existsById(id)) {
            missaoAtualizado.setId(id);
            return missoesRepository.save(missaoAtualizado);
        }
        return null;
    }

    public void deletarPorID(Long id) {
        missoesRepository.deleteById(id);
    }
}
