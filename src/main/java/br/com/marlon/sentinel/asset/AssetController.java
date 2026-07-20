package br.com.marlon.sentinel.asset;

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
    public List<Asset> findAll(){
        return service.findAll();
    }

    @GetMapping("/{id}")
    public Asset findById(@PathVariable Long id){
        return service.findById(id);
    }

    @PostMapping
    public Asset save(@RequestBody Asset asset){
        return service.save(asset);
    }

    @PutMapping("/{id}")
    public Asset update(@PathVariable Long id, @RequestBody Asset asset){
        return service.updateAsset(id, asset);
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable Long id){
        service.deleteById(id);
    }

}
