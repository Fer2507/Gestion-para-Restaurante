package itch.fonda.mapper;

import itch.fonda.dto.TipoDto;
import itch.fonda.entity.TipoEntity;
 
public class TipoMapper {
	public static TipoDto mapToTipoDto(TipoEntity tipoEntity) 
	{
        return new TipoDto( 
        	tipoEntity.getId(),
            tipoEntity.getNombreTipo(),
            tipoEntity.getDescripcionTipo()
        );
    }

    public static TipoEntity mapToTipo(TipoDto tipoDto) 
    {
        return new TipoEntity(
        	tipoDto.getId(),
        	tipoDto.getNombreTipo(),
            tipoDto.getDescripcionTipo()
        );
    }  
}
