package ru.simbirgo.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.simbirgo.dtos.MessageDTO;
import ru.simbirgo.exceptions.AppException;
import ru.simbirgo.exceptions.ProfessionNotExists;
import ru.simbirgo.models.Dialogue;
import ru.simbirgo.models.Task;
import ru.simbirgo.payloads.NewTaskRequest;
import ru.simbirgo.services.CharacterService;
import ru.simbirgo.services.DialogueService;
import ru.simbirgo.services.TaskService;

import java.util.List;

@Tag(name="TaskController")
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/task")
public class TaskController {
    private final Logger LOGGER = LoggerFactory.getLogger(TaskController.class);

    @Autowired
    TaskService taskService;

    @Autowired
    CharacterService characterService;

    @Operation(summary = "добавление задания для профессии")
    @ApiResponse(responseCode = "200", content = {@Content(mediaType = "application/json", schema=@Schema(implementation = Task.class))})
    @PostMapping("/new")
    public ResponseEntity<?> addNewTaskForProfession(@RequestBody NewTaskRequest newTaskRequest){
        LOGGER.info("ADD NEW TASK FOR PROFESSION");
        System.out.println(newTaskRequest);
        try{
            Task newTask = taskService.addNewTask(newTaskRequest);
            return new ResponseEntity<>(newTask, HttpStatus.OK);
        }
        catch (java.util.NoSuchElementException NE){
            return  new ResponseEntity<>(new AppException(HttpStatus.NOT_FOUND.value(), "персонаж для которого добавляется задание не найден"), HttpStatus.NOT_FOUND);
        }
    }

    @Operation(summary="получение всех заданий")
    @ApiResponse(responseCode = "200", content = {@Content(mediaType = "application/json", array=@ArraySchema(schema=@Schema(implementation = Task.class)))})
    @GetMapping("")
    public ResponseEntity<?> getTasks(){
        LOGGER.info("GET TASKS");
        List<Task> tasks = taskService.getTasks();
        return new ResponseEntity<>(tasks, HttpStatus.OK);
    }

    @Operation(summary="получение всех заданий для профессии по professionId")
    @ApiResponse(responseCode = "200", content = {@Content(mediaType = "application/json", array=@ArraySchema(schema=@Schema(implementation = Task.class)))})
    @GetMapping("/{professionId}")
    public ResponseEntity<?> getTasksForProfession(@PathVariable("professionId") Long professionId){
        LOGGER.info("GET TASKS FOR PROFESSION");
        List<Task> tasks = taskService.getTasksByProfessionId(professionId);
        return new ResponseEntity<>(tasks, HttpStatus.OK);
    }

    @Operation(summary = "удаление задания по taskId")
    @ApiResponse(responseCode = "200", content = {@Content(mediaType = "application/json", schema=@Schema(implementation = MessageDTO.class))})
    @DeleteMapping("/{taskId}")
    public MessageDTO deleteTaskById(@PathVariable("taskId") Long taskId){
        LOGGER.info("DELETE TASK BY ID");
        boolean isDelete = taskService.deleteById(taskId);
        String msg =  isDelete ? "удаление прошло успешно" : "то-то пошло не так";
        return new MessageDTO(msg);
    }

}
