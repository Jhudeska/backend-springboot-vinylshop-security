package nl.novi.vinylshop.mappers;

import nl.novi.vinylshop.dtos.profile.ProfileResponseDTO;
import nl.novi.vinylshop.entities.BaseEntity;
import nl.novi.vinylshop.entities.ProfileEntity;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ProfileDTOMapper {

    public ProfileResponseDTO mapToDto(ProfileEntity entity){
        ProfileResponseDTO dto = new ProfileResponseDTO();
        if(entity.getAlbums()!=null) {
            dto.setAlbums(entity.getAlbums()
                    .stream()
                    .map(BaseEntity::getId)
                    .toList());
        }
        dto.setId(entity.getId());
        dto.setKcid(entity.getKcid());

        return dto;
    }

    public List<ProfileResponseDTO> toDto(List<ProfileEntity> entity){
        return entity.stream().map(this::toDto).toList();
    }
}
