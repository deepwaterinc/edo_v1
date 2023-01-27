package com.education.controller;

import com.education.entity.FilePool;
import com.education.model.dto.FilePoolDto;
import com.education.service.filepool.FilePoolService;
import com.education.util.DtoConverter;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.AllArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@Log
@RequestMapping("/api/repository/file_pool")
@AllArgsConstructor
@ApiModel("Controller for FilePool")
public class FilePoolController {
    @ApiModelProperty("service")
    private final FilePoolService FILE_POOL_SERVICE;


    @ApiOperation(value = "Получить хранилище файлов по id", notes = "Returns an address as per the id")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved"),
            @ApiResponse(code = 404, message = "Not found - The file pool was not found")
    })
    @GetMapping("/{id}")
    public ResponseEntity<FilePoolDto> fetchFilePool(@PathVariable("id") Long id) {
        log.info("Request to get file pool by id = " + id);
        FilePoolDto filePoolDto = FILE_POOL_SERVICE.findById(id);
        return filePoolDto != null
                ? new ResponseEntity<>(filePoolDto, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @ApiOperation(value = "Получить список файловых хранилищ", notes = "Находит файловые хранилища по их id. Возвращает списком List<FilePool>")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved"),
            @ApiResponse(code = 404, message = "Not found - The file pools was not found")
    })
    @GetMapping("/findAll")
    public ResponseEntity<List<FilePoolDto>> fetchFindAllById(@RequestParam
                                                              @ApiParam("FilePool list")
                                                              List<Long> ids) {
        log.info("Got request to get file pools by ids");
        List<FilePool> filePools = FILE_POOL_SERVICE.findAllById(ids);
        return filePools != null && !filePools.isEmpty()
                ? new ResponseEntity<>(filePools.
                stream().
                map(DtoConverter::convertToDto).
                collect(Collectors.toList()), HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @ApiOperation(value = "Добавить хранилище файлов")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successfully added")
    })
    @PostMapping("/")
    public ResponseEntity<FilePoolDto> add(@RequestBody
                                           @ApiParam("filePool")
                                           FilePoolDto filePool) {

        filePool.setUploadDate(ZonedDateTime.now());
        log.info("Got request to add new file pool");
        ResponseEntity<FilePoolDto> responseEntity = new ResponseEntity<>(FILE_POOL_SERVICE.add(filePool), HttpStatus.CREATED);
        return responseEntity;
    }

    @ApiOperation(value = "Переместить в архив хранилище файлов")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully moved")
    })
    @PutMapping("/{id}")
    public ResponseEntity<Void> moveToArchived(@PathVariable("id") Long id) {
        log.info("Got request to move file pool to archive");
        FILE_POOL_SERVICE.moveToArchive(id);
        return ResponseEntity.ok().build();
    }

}
