package br.com.marlon.sentinel.asset;

import br.com.marlon.sentinel.asset.dto.AssetResponse;
import br.com.marlon.sentinel.asset.dto.CreateAssetRequest;
import br.com.marlon.sentinel.asset.dto.UpdateAssetRequest;
import br.com.marlon.sentinel.exception.AssetNotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import tools.jackson.databind.ObjectMapper;

import javax.xml.stream.Location;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@WebMvcTest(AssetController.class)
class AssetControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private AssetService service;

    @Test
    void shouldReturnAssetWhenIdExists() throws Exception{

        Long id = 10L;

        AssetResponse response = new AssetResponse();
        response.setId(id);
        response.setHostname("PC-Teste");
        response.setIp("222.222.222.33");
        response.setManufacturer("Dell");

        when(service.findById(id)).thenReturn(response);

        mockMvc.perform(get("/assets/{id}", id)).andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(10))
                .andExpect(jsonPath("$.hostname").value("PC-Teste"))
                .andExpect(jsonPath("$.ip").value("222.222.222.33"))
                .andExpect(jsonPath("$.manufacturer").value("Dell"));

    }

    @Test
    void shouldReturnNotFoundWhenAssetDoesNotExist() throws Exception{

        Long id = 11L;

        when(service.findById(id)).thenThrow(new AssetNotFoundException("Asset not found."));

        mockMvc.perform(get("/assets/{id}", id)).andExpect(status().isNotFound())
                .andExpect(jsonPath("$.status").value(404))
                .andExpect(jsonPath("$.message").value("Asset not found."));

    }

    @Test
    void shouldReturnAssetList() throws Exception{

        AssetResponse response1 = new AssetResponse();
        AssetResponse response2 = new AssetResponse();

        response1.setId(1L);
        response1.setHostname("pc01");
        response2.setId(2L);
        response2.setHostname("pc02");

        List<AssetResponse> responses = new ArrayList<>();
        responses.add(response1);
        responses.add(response2);

        when(service.findAll()).thenReturn(responses);

        mockMvc.perform(get("/assets")).andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].hostname").value("pc01"))
                .andExpect(jsonPath("$[1].id").value(2))
                .andExpect(jsonPath("$[1].hostname").value("pc02"));

    }

    @Test
    void shouldCreateAsset() throws Exception{

        CreateAssetRequest request = new CreateAssetRequest();

        request.setHostname("pc01");
        request.setIp("222.222.222.33");
        request.setOperatingSystem("macos");
        request.setManufacturer("apple");
        request.setModel("m3");
        request.setResponsible("marlon");
        request.setStatus(AssetStatus.ACTIVE);
        request.setLocation("home-office");
        request.setPurchaseDate(LocalDate.of(2026, 5, 2));

        AssetResponse response = new AssetResponse();

        response.setId(50L);
        response.setHostname("pc01");
        response.setIp("222.222.222.33");
        response.setOperatingSystem("macos");
        response.setManufacturer("apple");
        response.setModel("m3");
        response.setResponsible("marlon");
        response.setStatus(AssetStatus.ACTIVE);
        response.setLocation("home-office");
        response.setPurchaseDate(LocalDate.of(2026, 5, 2));

        when(service.save(any(CreateAssetRequest.class))).thenReturn(response);

        mockMvc.perform(post("/assets").contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(header().string("Location", "/assets/50"))
                .andExpect(jsonPath("$.id").value(50))
                .andExpect(jsonPath("$.hostname").value("pc01"))
                .andExpect(jsonPath("$.ip").value("222.222.222.33"))
                .andExpect(jsonPath("$.status").value("ACTIVE"));

    }

    @Test
    void shouldReturnBadRequestWhenCreatingInvalidAsset () throws Exception{

        CreateAssetRequest request = new CreateAssetRequest();

        request.setHostname("");
        request.setIp("222.222.222.33");
        request.setOperatingSystem("macos");
        request.setManufacturer("apple");
        request.setModel("m3");
        request.setResponsible("marlon");
        request.setStatus(AssetStatus.ACTIVE);
        request.setLocation("home-office");
        request.setPurchaseDate(LocalDate.of(2026, 5, 2));

        mockMvc.perform(post("/assets").contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value(400))
                .andExpect(jsonPath("$.message").value("Validation error"))
                .andExpect(jsonPath("$.errors.hostname").exists());

    }

    @Test
    void shouldUpdateAssetWhenIdExists() throws Exception{

        Long id = 10L;

        UpdateAssetRequest request = new UpdateAssetRequest();

        request.setHostname("pc01");
        request.setIp("222.222.222.33");
        request.setOperatingSystem("macos");
        request.setManufacturer("apple");
        request.setModel("m3");
        request.setResponsible("marlon");
        request.setStatus(AssetStatus.ACTIVE);
        request.setLocation("home-office");
        request.setPurchaseDate(LocalDate.of(2026, 5, 2));

        AssetResponse response = new AssetResponse();

        response.setId(id);
        response.setHostname("pc01");
        response.setIp("222.222.222.33");
        response.setOperatingSystem("macos");
        response.setManufacturer("apple");
        response.setModel("m3");
        response.setResponsible("marlon");
        response.setStatus(AssetStatus.ACTIVE);
        response.setLocation("home-office");
        response.setPurchaseDate(LocalDate.of(2026, 5, 2));

        when(service.updateAsset(eq(id), any(UpdateAssetRequest.class))).thenReturn(response);

        mockMvc.perform(put("/assets/{id}", id).contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(10))
                .andExpect(jsonPath("$.hostname").value("pc01"))
                .andExpect(jsonPath("$.ip").value("222.222.222.33"))
                .andExpect(jsonPath("$.status").value("ACTIVE"));

    }

    @Test
    void    shouldReturnNotFoundWhenUpdatingNonexistentAsset() throws  Exception{

        Long id = 10L;

        UpdateAssetRequest request = new UpdateAssetRequest();

        request.setHostname("pc01");
        request.setIp("222.222.222.33");
        request.setOperatingSystem("macos");
        request.setManufacturer("apple");
        request.setModel("m3");
        request.setResponsible("marlon");
        request.setStatus(AssetStatus.ACTIVE);
        request.setLocation("home-office");
        request.setPurchaseDate(LocalDate.of(2026, 5, 2));

        when(service.updateAsset(eq(id), any(UpdateAssetRequest.class))).thenThrow(new AssetNotFoundException("Asset not found."));

        mockMvc.perform(put("/assets/{id}", id).contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.status").value(404))
                .andExpect(jsonPath("$.message").value("Asset not found."));

    }

    @Test
    void shouldDeleteAssetWhenIdExists() throws Exception{

        Long id = 10L;

        mockMvc.perform(delete("/assets/{id}", id)).andExpect(status().isNoContent());

        verify(service).deleteById(id);
    }

    @Test
    void shouldReturnNotFoundWhenDeletingNonexistingAsset() throws Exception{

        Long id = 10L;
        doThrow(new AssetNotFoundException("Asset with id " + id + " was not found.")).when(service).deleteById(id);

        mockMvc.perform(delete("/assets/{id}", id)).andExpect(status().isNotFound())
                .andExpect(jsonPath("$.status").value(404))
                .andExpect(jsonPath("$.message")
                        .value("Asset with id " + id + " was not found."));


    }

    @Test
    void shouldReturnBadRequestWhenJsonIsMalformed() throws Exception{

        String malformedJson = """
        {
          "hostname": "pc01"
        """;

        mockMvc.perform(post("/assets").contentType(MediaType.APPLICATION_JSON).content(malformedJson))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value(400))
                .andExpect(jsonPath("$.message").value("Invalid request body"));
    }




}
