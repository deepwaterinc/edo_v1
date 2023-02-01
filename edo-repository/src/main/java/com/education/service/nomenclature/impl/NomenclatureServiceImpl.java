package com.education.service.nomenclature.impl;

import com.education.converter.NomenclatureToDtoConverter;
import com.education.entity.Nomenclature;
import com.education.model.dto.NomenclatureDto;
import com.education.repository.NomenclatureRepository;
import com.education.service.nomenclature.NomenclatureService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Представляет реализацию операций над номенклатурой
 *
 * @author Иван Кузнецов
 * @version 1.0
 * @since 1.0
 */
@Service
@RequiredArgsConstructor
public class NomenclatureServiceImpl implements NomenclatureService {

    /**
     * Репозиторий для связи с БД
     */
    private final NomenclatureRepository nomenclatureRepository;

    /**
     * Сохраняет номенклатуру в БД
     * @param nomenclature NomenclatureDto
     * @return NomenclatureDto
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public NomenclatureDto save(NomenclatureDto nomenclature) {
        Nomenclature newNomenclature = NomenclatureToDtoConverter.convertToNomenclature(nomenclature);
        return NomenclatureToDtoConverter.convertToDto(nomenclatureRepository.saveAndFlush(newNomenclature));
    }

    /**
     * Переводит номенклатуру в архив
     * @param id Long
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public void moveToArchive(Long id) {
        nomenclatureRepository.moveToArchive(id);
    }

    /**
     * Предоставляет NomenclatureDto номенклатуры из БД по id
     * @param id Long
     * @return NomenclatureDto
     */
    @Transactional(readOnly = true, rollbackFor = Exception.class)
    @Override
    public NomenclatureDto findById(Long id) {
        Nomenclature nomenclature = nomenclatureRepository.findById(id).orElse(null);
        return nomenclature != null ? NomenclatureToDtoConverter.convertToDto(nomenclature) : null;
    }

    /**
     * Предоставляет список NomenclatureDto номенклатур из БД по id
     * @param list List of id
     * @return List of NomenclatureDto
     */
    @Transactional(readOnly = true, rollbackFor = Exception.class)
    @Override
    public List<NomenclatureDto> findAllById(Iterable<Long> list) {
        List<Nomenclature> nomenclatures = nomenclatureRepository.findAllById(list);
        if (nomenclatures.isEmpty()) {
            return null;
        }
        return nomenclatures
                .stream()
                .map(NomenclatureToDtoConverter::convertToDto)
                .collect(Collectors.toList());
    }

    /**
     * Предоставляет не заархивированное NomenclatureDto номенклатуры из БД по id
     * @param id Long
     * @return NomenclatureDto
     */
    @Transactional(readOnly = true, rollbackFor = Exception.class)
    @Override
    public NomenclatureDto findByIdNotArchived(Long id) {
        Nomenclature nomenclature = nomenclatureRepository.findByIdNotArchived(id).orElse(null);
        return nomenclature != null ? NomenclatureToDtoConverter.convertToDto(nomenclature) : null;
    }

    /**
     * Предоставляет список не заархивированных NomenclatureDto номенклатур из БД по id
     * @param list List of id
     * @return List of NomenclatureDto
     */
    @Transactional(readOnly = true, rollbackFor = Exception.class)
    @Override
    public List<NomenclatureDto> findAllByIdNotArchived(Iterable<Long> list) {
        List<Nomenclature> nomenclatures = nomenclatureRepository.findAllByIdNotArchived(list);
        if (nomenclatures.isEmpty()) {
            return null;
        }
        return nomenclatures
                .stream()
                .map(NomenclatureToDtoConverter::convertToDto)
                .collect(Collectors.toList());
    }
}