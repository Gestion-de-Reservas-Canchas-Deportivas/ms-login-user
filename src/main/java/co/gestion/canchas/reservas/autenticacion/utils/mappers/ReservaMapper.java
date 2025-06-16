package co.gestion.canchas.reservas.autenticacion.utils.mappers;

import co.gestion.canchas.reservas.autenticacion.dto.ReservaDTO;
import co.gestion.canchas.reservas.autenticacion.entity.ReservaEntity;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ReservaMapper {

    ReservaDTO entityToDto(ReservaEntity  entity);

    ReservaEntity dtoToEntity(ReservaDTO dto);

    List<ReservaDTO> listEntityToListDto(List<ReservaEntity> entities);
}
