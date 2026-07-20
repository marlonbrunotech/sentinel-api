package br.com.marlon.sentinel.asset;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class AssetService {

    private final AssetRepository repository;

    public AssetService(AssetRepository repository) {
        this.repository = repository;
    }

    public List<Asset> findAll() {
        return repository.findAll();

    }

    public Asset findById(Long id) {

        return repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Asset not found."));
    }

    public Asset save(Asset asset) {
        return repository.save(asset);
    }

    public Asset updateAsset(Long id, Asset updatedAsset) {
        Asset existingAsset = repository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Asset not found."));
        existingAsset.setHostname(updatedAsset.getHostname());
        existingAsset.setIp(updatedAsset.getIp());
        existingAsset.setOperatingSystem(updatedAsset.getOperatingSystem());
        existingAsset.setManufacturer(updatedAsset.getManufacturer());
        existingAsset.setModel(updatedAsset.getModel());
        existingAsset.setResponsible(updatedAsset.getResponsible());
        existingAsset.setStatus(updatedAsset.getStatus());
        existingAsset.setLocation(updatedAsset.getLocation());
        existingAsset.setLastLoggedUser(updatedAsset.getLastLoggedUser());
        existingAsset.setPurchaseDate(updatedAsset.getPurchaseDate());
        existingAsset.setLastSeen(updatedAsset.getLastSeen());

        return repository.save(existingAsset);

    }

    public void deleteById(Long id){
        Asset existingAsset = repository.findById(id).orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "Asset not found."));
        repository.delete(existingAsset);
    }

}
