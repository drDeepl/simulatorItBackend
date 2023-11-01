package ru.simbirgo.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.simbirgo.config.jwt.JwtUtils;
import ru.simbirgo.dtos.JwtDTO;
import ru.simbirgo.models.Profession;
import ru.simbirgo.payloads.NewProfessionRequest;
import ru.simbirgo.services.ProfessionService;

import java.util.List;

@Tag(name="ProfessionController")
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/profession")
public class ProfessionController {
    private final Logger LOGGER = LoggerFactory.getLogger(ProfessionController.class);

    @Autowired
    ProfessionService professionService;

    @Autowired
    JwtUtils jwtUtils;

    @Operation(summary="получение всех профессий")
    @GetMapping("")
    @ApiResponse(responseCode = "200", content = {@Content(mediaType = "application/json", array=@ArraySchema(schema=@Schema(implementation = Profession.class)))})
    public List<Profession> getProfessions(){
        LOGGER.info("GET PROFESSIONS");
        return professionService.getProfessions();
    }

    @Operation(summary="создание профессии")
    @PostMapping("/new")
    @ApiResponse(responseCode = "200", content = {@Content(mediaType = "application/json", schema=@Schema(implementation = Profession.class))})
    public ResponseEntity<?> newPrefession(@RequestBody NewProfessionRequest newProfessionRequest){
        LOGGER.info("NEW PROFESSION");
        Profession newProfession = professionService.createProfession(newProfessionRequest);
        return new ResponseEntity<Profession>(newProfession, HttpStatus.OK);
    }

    @Operation(summary = "редактирование профессии")
    @PutMapping("/update/{professionId}")
    @ApiResponse(responseCode = "200", content = {@Content(mediaType = "application/json", schema=@Schema(implementation = Profession.class))})
    public ResponseEntity<?> updateProfessionById(@PathVariable("professionId") Long id, @RequestBody NewProfessionRequest newProfessionRequest, HttpServletRequest httpServletRequest){
        LOGGER.info("UPDATE PROFESSION BY ID");
        String token = httpServletRequest.getHeader("Authorization").substring(7);
        System.out.println(jwtUtils.getIsAdmin(token));
        return null;
    }



}
