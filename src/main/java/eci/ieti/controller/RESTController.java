package eci.ieti.controller;

import com.mongodb.client.gridfs.model.GridFSFile;
import eci.ieti.Application;
import eci.ieti.data.TodoRepository;
import eci.ieti.data.model.Todo;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.gridfs.GridFsResource;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

@RequestMapping("api")
@RestController
public class RESTController {
    Logger logger = LoggerFactory.getLogger(RESTController.class);

    //TODO inject components (TodoRepository and GridFsTemplate)
    @Autowired
    GridFsTemplate gridFsTemplate;
  
    @Autowired
    TodoRepository todoRepsitory;

    @RequestMapping("/files/{filename}") 
    public ResponseEntity<InputStreamResource> getFileByName(@PathVariable String filename) throws IOException {
        ResponseEntity response;
        GridFSFile file = gridFsTemplate.findOne(new Query().addCriteria(Criteria.where("filename").is(filename)));
        System.out.println(file);
        if (file == null) {
            response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            GridFsResource resource = gridFsTemplate.getResource(file.getFilename());
            response = ResponseEntity.ok()
                    .contentType(MediaType.valueOf(resource.getContentType()))
                    .body(new InputStreamResource(resource.getInputStream()));
        }
        return response;

    }
    @CrossOrigin("*")
    @PostMapping("/files")
    public String handleFileUpload(@RequestParam("file") MultipartFile file, RedirectAttributes redirectAttributes) throws IOException {

        //TODO implement method
        
        gridFsTemplate.store(file.getInputStream(), file.getOriginalFilename(), file.getContentType());
        logger.info("Imagen cargada de forma exitosa con nombre " + file.getOriginalFilename() );
        return "/files/"+ file.getOriginalFilename();
    }

    @CrossOrigin("*")
    @PostMapping("/todo")
    public Todo createTodo(@RequestBody Todo todo) {
        //TODO implement method
        return null;
    }

    @CrossOrigin("*")
    @GetMapping("/todo")
    public List<Todo> getTodoList() {
        //TODO implement method
        return null;
    }

}
