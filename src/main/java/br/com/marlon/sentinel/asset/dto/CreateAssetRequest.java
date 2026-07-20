package br.com.marlon.sentinel.asset.dto;

import br.com.marlon.sentinel.asset.AssetStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreateAssetRequest {

    private String hostname;
    private String ip;
    private String operatingSystem;
    private String manufacturer;
    private String model;
    private String responsible;
    private AssetStatus status;
    private String location;
    private String lastLoggedUser;
    private LocalDate purchaseDate;
    private LocalDateTime lastSeen;
}
