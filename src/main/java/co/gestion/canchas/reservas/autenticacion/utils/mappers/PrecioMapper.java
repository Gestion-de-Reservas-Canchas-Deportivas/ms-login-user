package co.gestion.canchas.reservas.autenticacion.utils.mappers;


import co.gestion.canchas.reservas.autenticacion.dto.PrecioDTO;
import co.gestion.canchas.reservas.autenticacion.entity.PrecioEntity;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface PrecioMapper {

    PrecioDTO entityToDto(PrecioEntity e);

    PrecioEntity dtoToEntity(PrecioDTO d);

    List<PrecioDTO> listEntityToListDto(List<PrecioEntity> e);
}