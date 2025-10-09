package dev.lunguinhoantonio.CadastroDeNinjas.Missoes.service;
import com.fasterxml.jackson.databind.ObjectMapper;
import dev.lunguinhoantonio.CadastroDeNinjas.Missoes.dto.MissoesDTO;
import dev.lunguinhoantonio.CadastroDeNinjas.Missoes.mapper.MissoesMapper;
import dev.lunguinhoantonio.CadastroDeNinjas.Missoes.model.MissoesModel;
import dev.lunguinhoantonio.CadastroDeNinjas.Missoes.repository.MissoesRepository;
import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;
import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class MissoesService {
    private final MissoesRepository missoesRepository;
    private final MissoesMapper missoesMapper;

    public MissoesService(MissoesRepository missoesRepository, MissoesMapper missoesMapper) {
        this.missoesRepository = missoesRepository;
        this.missoesMapper = missoesMapper;
    }

    public List<MissoesDTO> listar() {
        List<MissoesModel> ninjas = missoesRepository.findAll();
        return ninjas.stream()
                .map(missoesMapper::map)
                .collect(Collectors.toList());
    }

    public MissoesDTO listarPorID(Long id) {
        Optional<MissoesModel> missaoPorID = missoesRepository.findById(id);
        return missaoPorID.map(missoesMapper::map).orElse(null);
    }

    public MissoesDTO criar(MissoesDTO missoesDTO) {
        MissoesModel missao = missoesMapper.map(missoesDTO);
        missao = missoesRepository.save(missao);
        return missoesMapper.map(missao);
    }

    public MissoesDTO alterar(Long id, MissoesDTO missoesDTO) {
        Optional<MissoesModel> missaoExistente = missoesRepository.findById(id);
        if (missaoExistente.isPresent()) {
            MissoesModel missaoAtualizada = missoesMapper.map(missoesDTO);
            missaoAtualizada.setId(id);
            MissoesModel missaoSalva = missoesRepository.save(missaoAtualizada);
            return missoesMapper.map(missaoSalva);
        }
        return null;
    }

    public MissoesDTO atualizarMissaoPorIDPatch(Long id, Map<String, Object> fields) {
        MissoesModel missoesModel = findOne(id);
        merge(fields, missoesModel);
        missoesModel = missoesRepository.save(missoesModel);
        return missoesMapper.map(missoesModel);
    }

    private void merge(Map<String, Object> fields, MissoesModel missoesModel) {
        ObjectMapper objectMapper = new ObjectMapper();
        MissoesModel missoesModelConvert = objectMapper.convertValue(fields, MissoesModel.class);
        fields.forEach((nomeAtributo, valorAtributo) -> {
            Field field = ReflectionUtils.findField(MissoesModel.class, nomeAtributo);
            if (field != null) {
                field.setAccessible(true);
                ReflectionUtils.setField(field, missoesModel, valorAtributo);
                Object newValue = ReflectionUtils.getField(field, missoesModelConvert);
                ReflectionUtils.setField(field, missoesModel, newValue);
                field.setAccessible(false);
            }
        });
    }

    private MissoesModel findOne(Long id) {
        Optional<MissoesModel> ninja = missoesRepository.findById(id);
        return ninja.orElse(null);
    }

    public void deletarPorID(Long id) {
        missoesRepository.deleteById(id);
    }
}
