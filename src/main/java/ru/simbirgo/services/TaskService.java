package ru.simbirgo.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.simbirgo.exceptions.AppException;
import ru.simbirgo.exceptions.ProfessionNotExists;
import ru.simbirgo.models.Profession;
import ru.simbirgo.models.Task;
import ru.simbirgo.payloads.NewTaskRequest;
import ru.simbirgo.repositories.AnswerRepository;
import ru.simbirgo.repositories.DialogueRepository;
import ru.simbirgo.repositories.DialogueTextRepository;
import ru.simbirgo.repositories.TaskRepository;

import java.util.List;

@Service
public class TaskService {
    private final Logger LOGGER = LoggerFactory.getLogger(TaskService.class);

    @Autowired
    TaskRepository taskRepository;
    @Autowired
    ProfessionService professionService;

    public Task addNewTask(NewTaskRequest newTaskRequest) throws ProfessionNotExists{
        LOGGER.info("ADD NEW TASK");
        Task newTask = new Task();
        LOGGER.info(newTaskRequest.getProfessionId().toString());
        Profession profession = professionService.getProfessionById(newTaskRequest.getProfessionId());
        newTask.setDescription(newTaskRequest.getDescription());
        newTask.setProfession(profession);
        return taskRepository.save(newTask);

    }

    public List<Task> getTasks(){
        LOGGER.info("GET TASKS");
        return taskRepository.findAll();
    }

    public List<Task> getTasksByProfessionId(Long professionId){
        LOGGER.info("GET TASKS BY PROFESSION ID");
        return taskRepository.findByProfessionId(professionId);
    }

    public boolean deleteById(Long taskId){
        LOGGER.info("DELETE BY ID");
        try{
            taskRepository.deleteById(taskId);
            return true;

        }
        catch (RuntimeException RE){
            LOGGER.error(RE.getMessage());
            return false;
        }

    }

}
