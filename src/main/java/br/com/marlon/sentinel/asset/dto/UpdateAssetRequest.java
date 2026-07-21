package br.com.marlon.sentinel.asset.dto;

import br.com.marlon.sentinel.asset.AssetStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UpdateAssetRequest {

    @NotBlank
    private String hostname;

    @NotBlank
    private String ip;

    @NotBlank
    private String operatingSystem;

    @NotBlank
    private String manufacturer;

    @NotBlank
    private String model;

    @NotBlank
    private String responsible;

    @NotNull
    private AssetStatus status;

    @NotBlank
    private String location;

    private String lastLoggedUser;

    @NotNull
    private LocalDate purchaseDate;

    private LocalDateTime lastSeen;
}
