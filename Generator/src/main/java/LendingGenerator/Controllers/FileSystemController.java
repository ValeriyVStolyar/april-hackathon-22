package LendingGenerator.Controllers;

import LendingGenerator.Services.StorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/files")
public class FileSystemController {

    private final StorageService storageService;

    public FileSystemController(@Autowired StorageService storageService) {
        this.storageService = storageService;
    }

    @CrossOrigin
    @PostMapping(
            value = "/downloadFilledTemplate",
            produces = MediaType.APPLICATION_OCTET_STREAM_VALUE
    )
    public String downloadFilledTemplate(
            @RequestParam String userName, @RequestBody String data
    ) throws IOException {
        return storageService.downloadFilledTemplate(userName, data);
    }

    @CrossOrigin
    @GetMapping(
            value = "/download",
            produces = MediaType.APPLICATION_OCTET_STREAM_VALUE
    )
    public ResponseEntity<InputStreamResource> downloadFile(@RequestParam String userName) throws IOException {
        return storageService.downloadFile(userName);
    }

}
