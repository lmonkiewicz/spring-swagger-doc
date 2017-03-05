package com.lmonkiewicz.example.springSwaggerDoc;

import io.swagger.annotations.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

/**
 * Created by lmonkiewicz on 2017-02-10.
 *
 * Example Rest Controller that offers basic CRUD operations
 */
@RestController
@RequestMapping("/notes")
@Api(value = "Example of MVC controller with Swagger documentation", description = "Note operation")
public class NotesController {


    private Integer idGenerator = 1;

    private final Map<Integer, Note> data = new LinkedHashMap<>();

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ApiOperation("Find Note by given Id")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Note with given id has been found and returned", response = Note.class),
            @ApiResponse(code = 404, message = "Note with given id does not exist")
    })
    public ResponseEntity<Note> getNote(@ApiParam(value = "Id of a Note", required = true)
                                            @PathVariable(name = "id") Integer id){
        if (data.containsKey(id)){
            return ResponseEntity.ok(data.get(id));
        }
        else {
            return ResponseEntity.notFound().build();
        }
    }

    @RequestMapping(value ="/", method = RequestMethod.GET)
    @ApiOperation("List all notes")
    @ApiResponse(code = 200, message = "List of all notes", responseContainer = "List", response = Note.class)
    public ResponseEntity<List<Note>> listNotes(){
        final List<Note> result = new ArrayList<>();
        result.addAll(data.values());

        return ResponseEntity.ok(result);
    }


    @RequestMapping(value = "/", method = RequestMethod.PUT)
    @ApiOperation("Create new note")
    @ApiResponses({
            @ApiResponse(code = 201, message = "Note created and its ID returned", response = Integer.class),
            @ApiResponse(code = 400, message = "Note with no content cannot be created")
    })
    public ResponseEntity<Integer> createNote(@RequestBody Note requestData){
        if (requestData.getContent() == null){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        else {
            final Integer id = generateId();
            requestData.setId(id);
            data.put(id, requestData);

            return ResponseEntity.status(HttpStatus.CREATED).body(id);
        }
    }
    @RequestMapping(value ="/{id}", method = RequestMethod.POST)
    @ApiOperation("Update note with given ID")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Note has beed updated"),
            @ApiResponse(code = 400, message = "Note ID is different than ID in path"),
            @ApiResponse(code = 404, message = "Note with given ID has not been found")
    })
    public ResponseEntity<Void> updateNote(@ApiParam(value = "Id of a Note", required = true)
                                               @PathVariable(name = "id") Integer id,
                                           @ApiParam(value = "Note content", required = true)
                                                @RequestBody Note requestData){
        if (data.containsKey(id)){
            if (Objects.equals(id, requestData.getId())){
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
            }
            data.put(id, requestData);
            return ResponseEntity.status(HttpStatus.OK).build();
        }
        else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    @ApiOperation("Delete note with given ID")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Note has been deleted successfuly"),
            @ApiResponse(code = 404, message = "Note with given ID has not been found")
    })
    public ResponseEntity<Void> deleteNote(@ApiParam(value = "Id of a Note", required = true)
                                               @PathVariable(name="id") Integer id){
        if (data.containsKey(id)){
            data.remove(id);
            return ResponseEntity.status(HttpStatus.OK).build();
        }
        else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    private Integer generateId(){
        return idGenerator++;
    }


}
