package co.gestion.canchas.reservas.autenticacion.utils.mappers;

import co.gestion.canchas.reservas.autenticacion.dto.TipoCanchaDTO;
import co.gestion.canchas.reservas.autenticacion.entity.TipoCanchaEntity;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface TipoCanchaMapper {

    TipoCanchaDTO entityToDto(TipoCanchaEntity entity);

    TipoCanchaEntity dtoToEntity(TipoCanchaDTO dto);

    List<TipoCanchaDTO> listEntityToDto(List<TipoCanchaEntity> entities);

    List<TipoCanchaDTO> listDtoToEntity(List<TipoCanchaDTO> dtos);
}
