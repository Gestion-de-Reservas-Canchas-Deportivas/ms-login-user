package co.gestion.canchas.reservas.autenticacion.utils.mappers;

import co.gestion.canchas.reservas.autenticacion.dto.HorarioDTO;
import co.gestion.canchas.reservas.autenticacion.entity.HorarioEntity;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface HorarioMapper {

    HorarioDTO entityToDto(HorarioEntity entity);

    HorarioEntity dtoToEntity(HorarioDTO dto);

    List<HorarioDTO> listEntityToListDto(List<HorarioEntity> entities);
}