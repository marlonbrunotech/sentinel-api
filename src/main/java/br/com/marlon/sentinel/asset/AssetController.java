package br.com.marlon.sentinel.asset;

import br.com.marlon.sentinel.asset.dto.AssetResponse;
import br.com.marlon.sentinel.asset.dto.CreateAssetRequest;
import br.com.marlon.sentinel.asset.dto.UpdateAssetRequest;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/assets")
public class AssetController {

    private final AssetService service;

    public AssetController(AssetService service) {
        this.service = service;
    }

    @GetMapping
    public List<AssetResponse> findAll(){
        return service.findAll();
    }

    @GetMapping("/{id}")
    public AssetResponse findById(@PathVariable Long id){
        return service.findById(id);
    }

    @PostMapping
    public AssetResponse save(@Valid @RequestBody CreateAssetRequest request){
        return service.save(request);
    }

    @PutMapping("/{id}")
    public AssetResponse update(@PathVariable Long id, @Valid @RequestBody UpdateAssetRequest request){
        return service.updateAsset(id, request);
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable Long id){
        service.deleteById(id);
    }

}
