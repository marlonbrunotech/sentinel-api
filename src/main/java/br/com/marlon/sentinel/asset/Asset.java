package br.com.marlon.sentinel.asset;

import jakarta.persistence.*;
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
@Entity
@Table(name = "assets")
public class Asset {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String hostname;
    private String ip;

    @Column(name = "operating_system")
    private String operatingSystem;

    private String manufacturer;
    private String model;
    private String responsible;

    @Enumerated(EnumType.STRING)
    private AssetStatus status;

    private String location;

    @Column(name = "last_logged_user")
    private String lastLoggedUser;

    @Column(name = "purchase_date")
    private LocalDate purchaseDate;

    @Column(name = "last_seen")
    private LocalDateTime lastSeen;


}
