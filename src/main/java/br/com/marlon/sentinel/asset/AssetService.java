package br.com.marlon.sentinel.asset;

import br.com.marlon.sentinel.asset.dto.AssetResponse;
import br.com.marlon.sentinel.asset.dto.CreateAssetRequest;
import br.com.marlon.sentinel.asset.dto.UpdateAssetRequest;
import br.com.marlon.sentinel.exception.AssetNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AssetService {

    private final AssetRepository repository;

    public AssetService(AssetRepository repository) {
        this.repository = repository;
    }

    public List<AssetResponse> findAll() {
        return repository.findAll().stream().map(asset -> toResponse(asset)).toList();
    }

    public AssetResponse findById(Long id) {

        Asset asset = repository.findById(id)
                .orElseThrow(() -> new AssetNotFoundException("Asset with id " + id + " was not found."));

        return toResponse(asset);
    }

    public AssetResponse save(CreateAssetRequest request) {

        Asset asset = new Asset();

        asset.setHostname(request.getHostname());
        asset.setIp(request.getIp());
        asset.setOperatingSystem(request.getOperatingSystem());
        asset.setManufacturer(request.getManufacturer());
        asset.setModel(request.getModel());
        asset.setResponsible(request.getResponsible());
        asset.setStatus(request.getStatus());
        asset.setLocation(request.getLocation());
        asset.setLastLoggedUser(request.getLastLoggedUser());
        asset.setPurchaseDate(request.getPurchaseDate());
        asset.setLastSeen(request.getLastSeen());

        Asset savedAsset = repository.save(asset);
        return toResponse(savedAsset);
    }

    public AssetResponse updateAsset(Long id, UpdateAssetRequest request) {
        Asset existingAsset = repository.findById(id).orElseThrow(() -> new AssetNotFoundException("Asset with id " + id + " was not found."));
        existingAsset.setHostname(request.getHostname());
        existingAsset.setIp(request.getIp());
        existingAsset.setOperatingSystem(request.getOperatingSystem());
        existingAsset.setManufacturer(request.getManufacturer());
        existingAsset.setModel(request.getModel());
        existingAsset.setResponsible(request.getResponsible());
        existingAsset.setStatus(request.getStatus());
        existingAsset.setLocation(request.getLocation());
        existingAsset.setLastLoggedUser(request.getLastLoggedUser());
        existingAsset.setPurchaseDate(request.getPurchaseDate());
        existingAsset.setLastSeen(request.getLastSeen());

        Asset updatedAsset = repository.save(existingAsset);
        return toResponse(updatedAsset);

    }

    public void deleteById(Long id){
        Asset existingAsset = repository.findById(id).orElseThrow(()-> new AssetNotFoundException("Asset with id " + id + " was not found."));
        repository.delete(existingAsset);
    }

    private AssetResponse toResponse(Asset asset){

        return new AssetResponse(
                asset.getId(),
                asset.getHostname(),
                asset.getIp(),
                asset.getOperatingSystem(),
                asset.getManufacturer(),
                asset.getModel(),
                asset.getResponsible(),
                asset.getStatus(),
                asset.getLocation(),
                asset.getLastLoggedUser(),
                asset.getPurchaseDate(),
                asset.getLastSeen()
        );
    }

}
