package br.com.marlon.sentinel.asset;

import br.com.marlon.sentinel.asset.dto.AssetResponse;
import br.com.marlon.sentinel.asset.dto.CreateAssetRequest;
import br.com.marlon.sentinel.asset.dto.UpdateAssetRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/assets")
@Tag(name = "Assets", description = "Operations for managing IT assets")
public class AssetController {

    private final AssetService service;

    public AssetController(AssetService service) {
        this.service = service;
    }

    @GetMapping
    @Operation(summary = "List all assets", description = "Returns all assets registered in the inventory")
    @ApiResponse(responseCode = "200",
    description = "Assets retrieved successfully")
    public List<AssetResponse> findAll(){
        return service.findAll();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Find an asset by ID", description = "Returns the asset registered with the specified ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200",
            description = "Asset found successfully"),
            @ApiResponse(responseCode = "404",
            description = "Asset not found" )
    })
    public AssetResponse findById(@PathVariable Long id){
        return service.findById(id);
    }

    @PostMapping
    @Operation(summary = "Register a new asset", description = "Registers a new asset in the inventory")
    @ApiResponses({
            @ApiResponse(responseCode = "200",
            description = "Asset registered successfully"),
            @ApiResponse(responseCode = "400",
            description = "Invalid request data")
    })
    public AssetResponse save(@Valid @RequestBody CreateAssetRequest request){
        return service.save(request);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update an existing asset", description = "Updates the asset registered with the specified ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200",
            description = "Asset updated successfully"),
            @ApiResponse(responseCode = "400",
            description = "Invalid request data"),
            @ApiResponse(responseCode = "404",
            description = "Asset not found")
    })
    public AssetResponse update(@PathVariable Long id, @Valid @RequestBody UpdateAssetRequest request){
        return service.updateAsset(id, request);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete an asset", description = "Deletes the asset registered with the specified ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200",
            description = "Asset deleted successfully"),
            @ApiResponse(responseCode = "404",
            description = "Asset not found")
    })
    public void deleteById(@PathVariable Long id){
        service.deleteById(id);
    }

}
