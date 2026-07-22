package br.com.marlon.sentinel.asset;

import br.com.marlon.sentinel.asset.dto.AssetResponse;
import br.com.marlon.sentinel.asset.dto.CreateAssetRequest;
import br.com.marlon.sentinel.asset.dto.UpdateAssetRequest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import br.com.marlon.sentinel.exception.AssetNotFoundException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@ExtendWith(MockitoExtension.class)
class AssetServiceTest {

    @Mock
    private AssetRepository repository;

    @InjectMocks
    private AssetService service;

    @Test
    void shouldReturnAssetWhenIdExists(){

        Long id = 1L;

        Asset asset = new Asset();
        asset.setId(1L);
        asset.setHostname("NOTEBOOK-01");
        asset.setIp("222.222.111.22");

        when(repository.findById(id)).thenReturn(Optional.of(asset));

        AssetResponse response = service.findById(id);

        assertNotNull(response);
        assertEquals(id, response.getId());
        assertEquals("NOTEBOOK-01", response.getHostname());
        assertEquals("222.222.111.22", response.getIp());


    }

    @Test
    void shouldThrowExceptionWhenAssetDoesNotExist(){

        Long id = 99L;

        when(repository.findById(id)).thenReturn(Optional.empty());
        assertThrows(AssetNotFoundException.class, ()-> service.findById(id));
    }


    @Test
    void shouldReturnAllAssets(){

        Asset asset1 = new Asset();
        asset1.setId(20L);
        asset1.setHostname("NOTEBOOK-01");

        Asset asset2 = new Asset();
        asset2.setId(21L);
        asset2.setHostname("NOTEBOOK-02");

        List<Asset> assets = new ArrayList<>();
        assets.add(asset1);
        assets.add(asset2);

        when(repository.findAll()).thenReturn(assets);

        List<AssetResponse> response = service.findAll();

        assertNotNull(response);
        assertEquals(2, response.size());
        assertEquals(20L, response.get(0).getId());
        assertEquals("NOTEBOOK-01", response.get(0).getHostname());
        assertEquals(21L, response.get(1).getId());
        assertEquals("NOTEBOOK-02", response.get(1).getHostname());

    }

    @Test
    void shouldSaveAsset(){


        String hostname = "NOTEBOOK-01";
        String ip = "222.222.111.22";
        String operatingSystem = "Windows 10";
        String manufacturer = "Dell";
        String model = "Inspiron";
        String responsible = "marlon";
        AssetStatus status = AssetStatus.ACTIVE;
        String location = "home office";
        LocalDate purchaseDate = LocalDate.of(2026, 2, 10);

        CreateAssetRequest request = new CreateAssetRequest();
        request.setHostname(hostname);
        request.setIp(ip);
        request.setOperatingSystem(operatingSystem);
        request.setManufacturer(manufacturer);
        request.setModel(model);
        request.setResponsible(responsible);
        request.setStatus(status);
        request.setLocation(location);
        request.setPurchaseDate(purchaseDate);

        Asset savedAsset = new Asset();
        savedAsset.setId(30L);
        savedAsset.setHostname(hostname);
        savedAsset.setIp(ip);
        savedAsset.setOperatingSystem(operatingSystem);
        savedAsset.setManufacturer(manufacturer);
        savedAsset.setModel(model);
        savedAsset.setResponsible(responsible);
        savedAsset.setStatus(status);
        savedAsset.setLocation(location);
        savedAsset.setPurchaseDate(purchaseDate);

        when(repository.save(any(Asset.class))).thenReturn(savedAsset);

        AssetResponse response = service.save(request);
        ArgumentCaptor<Asset> assetArgumentCaptor = ArgumentCaptor.forClass(Asset.class);
        verify(repository).save(assetArgumentCaptor.capture());
        Asset capturedAsset = assetArgumentCaptor.getValue();

        assertNull(capturedAsset.getId());
        assertEquals(hostname, capturedAsset.getHostname());
        assertEquals(ip, capturedAsset.getIp());
        assertEquals(operatingSystem, capturedAsset.getOperatingSystem());
        assertEquals(manufacturer, capturedAsset.getManufacturer());
        assertEquals(model, capturedAsset.getModel());
        assertEquals(responsible, capturedAsset.getResponsible());
        assertEquals(status, capturedAsset.getStatus());
        assertEquals(location, capturedAsset.getLocation());
        assertEquals(purchaseDate, capturedAsset.getPurchaseDate());

        assertNotNull(response);
        assertEquals(30L, response.getId());
        assertEquals(hostname, response.getHostname());
        assertEquals(ip, response.getIp());
        assertEquals(operatingSystem, response.getOperatingSystem());
        assertEquals(manufacturer, response.getManufacturer());
        assertEquals(model, response.getModel());
        assertEquals(responsible, response.getResponsible());
        assertEquals(status, response.getStatus());
        assertEquals(location, response.getLocation());
        assertEquals(purchaseDate, response.getPurchaseDate());

    }

    @Test
    void shouldUpdateAssetWhenIdExists(){

        Long id = 40L;
        String hostname = "NOTEBOOK-01";
        String ip = "222.222.111.22";
        String operatingSystem = "Windows 10";
        String manufacturer = "Dell";
        String model = "Inspiron";
        String responsible = "marlon";
        AssetStatus status = AssetStatus.ACTIVE;
        String location = "home office";
        LocalDate purchaseDate = LocalDate.of(2026, 2, 10);

        Asset existingAsset = new Asset();

        existingAsset.setId(id);
        existingAsset.setHostname(hostname);
        existingAsset.setIp(ip);
        existingAsset.setOperatingSystem(operatingSystem);
        existingAsset.setManufacturer(manufacturer);
        existingAsset.setModel(model);
        existingAsset.setResponsible(responsible);
        existingAsset.setStatus(status);
        existingAsset.setLocation(location);
        existingAsset.setPurchaseDate(purchaseDate);


        UpdateAssetRequest request = new UpdateAssetRequest();

        request.setHostname("NOTEBOOK-02");
        request.setIp(ip);
        request.setOperatingSystem(operatingSystem);
        request.setManufacturer(manufacturer);
        request.setModel(model);
        request.setResponsible(responsible);
        request.setStatus(AssetStatus.INACTIVE);
        request.setLocation(location);
        request.setPurchaseDate(purchaseDate);

        when(repository.findById(id)).thenReturn(Optional.of(existingAsset));

        when(repository.save(any(Asset.class))).thenAnswer(invocation -> invocation.getArgument(0));

        AssetResponse response = service.updateAsset(id, request);

        ArgumentCaptor<Asset> assetArgumentCaptor = ArgumentCaptor.forClass(Asset.class);
        verify(repository).save(assetArgumentCaptor.capture());
        Asset capturedAsset = assetArgumentCaptor.getValue();

        assertEquals(id, capturedAsset.getId());
        assertEquals("NOTEBOOK-02", capturedAsset.getHostname());
        assertEquals(ip, capturedAsset.getIp());
        assertEquals(operatingSystem, capturedAsset.getOperatingSystem());
        assertEquals(manufacturer, capturedAsset.getManufacturer());
        assertEquals(model, capturedAsset.getModel());
        assertEquals(responsible, capturedAsset.getResponsible());
        assertEquals(AssetStatus.INACTIVE, capturedAsset.getStatus());
        assertEquals(location, capturedAsset.getLocation());
        assertEquals(purchaseDate, capturedAsset.getPurchaseDate());

        assertNotNull(response);
        assertEquals(id, response.getId());
        assertEquals("NOTEBOOK-02", response.getHostname());
        assertEquals(AssetStatus.INACTIVE, response.getStatus());

    }

    @Test
    void shouldThrowAssetNotFoundExceptionWhenUpdatingNonexistentAsset(){

        Long id = 900L;
        UpdateAssetRequest request = new UpdateAssetRequest();

        when(repository.findById(id)).thenReturn(Optional.empty());
        assertThrows(AssetNotFoundException.class, () -> service.updateAsset(id, request));

        verify(repository, never()).save(any(Asset.class));
    }

    @Test
    void shouldDeleteAssetWhenIdExists(){

        Long id = 10L;

        String hostname = "NOTEBOOK-01";
        String ip = "222.222.111.22";
        String operatingSystem = "Windows 10";
        String manufacturer = "Dell";
        String model = "Inspiron";
        String responsible = "marlon";
        AssetStatus status = AssetStatus.ACTIVE;
        String location = "home office";
        LocalDate purchaseDate = LocalDate.of(2026, 2, 10);

        Asset existingAsset = new Asset();

        existingAsset.setId(id);

        when(repository.findById(id)).thenReturn(Optional.of(existingAsset));
        service.deleteById(id);
        verify(repository).delete(existingAsset);

    }

    @Test
    void shouldThrowAssetNotFoundExceptionWhenDeletingNonexistentAsset(){

        Long id = 900L;

        when(repository.findById(id)).thenReturn(Optional.empty());
        assertThrows(AssetNotFoundException.class, () -> service.deleteById(id));

        verify(repository, never()).delete(any(Asset.class));
    }


}
