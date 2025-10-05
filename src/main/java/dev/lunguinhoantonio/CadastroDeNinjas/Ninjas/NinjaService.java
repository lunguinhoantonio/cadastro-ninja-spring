package dev.lunguinhoantonio.CadastroDeNinjas.Ninjas;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.lunguinhoantonio.CadastroDeNinjas.Missoes.MissoesModel;
import dev.lunguinhoantonio.CadastroDeNinjas.Missoes.MissoesRepository;
import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class NinjaService {

    private final NinjaRepository ninjaRepository;
    private final MissoesRepository missoesRepository;
    private final NinjaMapper ninjaMapper;

    public NinjaService(NinjaRepository ninjaRepository, MissoesRepository missoesRepository, NinjaMapper ninjaMapper) {
        this.ninjaRepository = ninjaRepository;
        this.missoesRepository = missoesRepository;
        this.ninjaMapper = ninjaMapper;
    }

    public List<NinjaDTO> listarNinjas() {
        List<NinjaModel> ninjas = ninjaRepository.findAll();
        return ninjas.stream()
                .map(ninjaMapper::map)
                .collect(Collectors.toList());
    }

    public NinjaDTO listarNinjasPorID(Long id) {
        Optional<NinjaModel> ninjaPorID = ninjaRepository.findById(id);
        return ninjaPorID.map(ninjaMapper::map).orElse(null);
    }

    public NinjaDTO criarNinja(NinjaDTO ninjaDTO) {
        NinjaModel ninja = ninjaMapper.map(ninjaDTO);
        ninja = ninjaRepository.save(ninja);
        return ninjaMapper.map(ninja);
    }

    public void deletarNinjaPorId(Long id) {
        ninjaRepository.deleteById(id);
    }

    public NinjaDTO atualizarNinja(Long id, NinjaDTO ninjaDTO) {
        Optional<NinjaModel> ninjaExistente = ninjaRepository.findById(id);
        if (ninjaExistente.isPresent()) {
            NinjaModel ninjaAtualizado = ninjaMapper.map(ninjaDTO);
            ninjaAtualizado.setId(id);
            NinjaModel ninjaSalvo = ninjaRepository.save(ninjaAtualizado);
            return ninjaMapper.map(ninjaSalvo);
        }
        return null;
    }

    private void merge(Map<String, Object> fields, NinjaModel ninjaModel) {
        ObjectMapper objectMapper = new ObjectMapper();
        NinjaModel ninjaModelConvert = objectMapper.convertValue(fields, NinjaModel.class);
        fields.forEach((nomeAtributo, valorAtributo) -> {
            Field field = ReflectionUtils.findField(NinjaModel.class, nomeAtributo);
            if (field != null) {
                field.setAccessible(true);
                ReflectionUtils.setField(field, ninjaModel, valorAtributo);

                Object newValue = ReflectionUtils.getField(field, ninjaModelConvert);

                ReflectionUtils.setField(field, ninjaModel, newValue);
                field.setAccessible(false);
            }
        });
    }

    public NinjaDTO atualizarNinjaPorIdPatch(Long id, Map<String, Object> fields) {
        /*NinjaModel ninjaModel = findOne(id);
        merge(fields, ninjaModel);
        //Optional<MissoesModel> missao = missoesRepository.findById(ninjaModel.getMissoes().getId());
        Optional<MissoesModel> missao = missoesRepository.findById(ninjaModel.getId());
        if (missao.isPresent()) ninjaModel.setMissoes(missao.orElse(null));
        ninjaModel = ninjaRepository.save(ninjaModel);
        return ninjaMapper.map(ninjaModel);*/
        NinjaModel ninjaToUpdate = ninjaRepository.findById(id).orElseThrow(() -> new RuntimeException("Ninja não encontrado com id: " + id));
        fields.forEach((nome, valor) -> {
            switch (nome) {
                case "nome":
                    ninjaToUpdate.setNome((String) valor);
                    break;
                case "email":
                    ninjaToUpdate.setEmail((String) valor);
                    break;
                case "missoes":
                    if (valor instanceof Map) {
                        Map<String, Object> missoesMap = (Map<String, Object>) valor;
                        Integer missoesIdInt = (Integer) missoesMap.get("id");
                        if (missoesIdInt != null) {
                            Long missoesId = missoesIdInt.longValue();
                            MissoesModel missao = missoesRepository.findById(missoesId)
                                    .orElseThrow(() -> new RuntimeException("Missão não encontrada com id: " + missoesId));
                            ninjaToUpdate.setMissoes(missao);
                        }
                    }
                    break;
                case "idade":
                    ninjaToUpdate.setIdade((int) valor);
                    break;
                case "imgUrl":
                    ninjaToUpdate.setImgUrl((String) valor);
                    break;
                case "rank":
                    ninjaToUpdate.setRank((String) valor);
                    break;
            }
        });
        return ninjaMapper.map(ninjaRepository.save(ninjaToUpdate));
    }

    private NinjaModel findOne(Long id) {
        Optional<NinjaModel> ninja = ninjaRepository.findById(id);
        return ninja.orElse(null);
    }
}