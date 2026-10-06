package nl.novi.vinylshop.controllers;

import nl.novi.vinylshop.dtos.album.AlbumResponseDTO;
import nl.novi.vinylshop.dtos.profile.ProfileAlbumRequestDTO;
import nl.novi.vinylshop.dtos.profile.ProfileResponseDTO;
import nl.novi.vinylshop.services.ProfileService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController()
@RequestMapping("/profiles")
public class ProfileController {

    private final ProfileService profileService;

    public ProfileController(ProfileService profileService) {this.profileService = profileService;}

    @GetMapping()
    public ResponseEntity<List<ProfileResponseDTO>> getProfiles(Authentication authentication) {

        Set<String> authorities = AuthorityUtils.authorityListToSet(authentication.getAuthorities());;

        if(authorities.contains("ROLE_ADMIN")){
            List<ProfileResponseDTO> profileDtos = profileService.findAllProfiles();
            return ResponseEntity.ok(profileDtos);
        } else if (authorities.contains("ROLE_USER")){
            ProfileResponseDTO profileDto = profileService.findOrCreateProfile(authentication);
            return ResponseEntity.ok(List.of(profileDto));
        } else {
            return ResponseEntity.status(403).build();
        }

    }

    @GetMapping("/albums")
    public ResponseEntity<List<AlbumResponseDTO>> getAlbum(Authentication authentication){
        String kcid = authentication.getName();
        return ResponseEntity.ok(profileService.getAlbumForProfile(kcid));
    }

    @PostMapping("/albums")
    public ResponseEntity<Void> linkAlbum(Authentication authentication, @RequestBody ProfileAlbumRequestDTO dto){
        profileService.linkAlbum(authentication, dto.getAlbumId());
        return ResponseEntity.ok().build();
    }
}