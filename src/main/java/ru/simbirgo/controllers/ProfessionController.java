package ru.simbirgo.controllers;

import io.swagger.v3.oas.annotations.Operation;
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
import ru.simbirgo.dtos.JwtDTO;
import ru.simbirgo.models.Profession;
import ru.simbirgo.payloads.NewProfessionRequest;
import ru.simbirgo.services.ProfessionService;

@Tag(name="ProfessionController")
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/profession")
public class ProfessionController {
    private final Logger LOGGER = LoggerFactory.getLogger(ProfessionController.class);

    @Autowired
    ProfessionService professionService;

    @Operation(summary="создание профессии")
    @PostMapping("/new")
    @ApiResponse(responseCode = "200", content = {@Content(mediaType = "application/json", schema=@Schema(implementation = Profession.class))})
    public ResponseEntity<?> newPrefession(@RequestBody NewProfessionRequest newProfessionRequest){
        LOGGER.info("NEW PROFESSION");
        Profession newProfession = professionService.createProfession(newProfessionRequest);
        return new ResponseEntity<Profession>(newProfession, HttpStatus.OK);
    }


}
