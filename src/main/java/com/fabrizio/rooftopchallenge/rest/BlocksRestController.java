/**
 * @author fpratici
 */
package com.fabrizio.rooftopchallenge.rest;

import java.util.Map;

import com.fabrizio.rooftopchallenge.dto.BlocksResponseDTO;
import com.fabrizio.rooftopchallenge.exception.WebException;
import com.fabrizio.rooftopchallenge.service.BlocksService;
import com.fabrizio.rooftopchallenge.service.TokenService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/blocks")
public class BlocksRestController {

    @Autowired
    private BlocksService blocksService;

    @Autowired
    private Environment environment;

    @Autowired
    private TokenService tokenService;

    @GetMapping
    public ResponseEntity<?> getBlocks() throws WebException {
        //First, check for valid token authentication. If it's not valid, exception is thrown.
        Map<String,String> token = tokenService.getRooftopToken(environment.getProperty("user-email"));

        //Get blocks for given token and convert from arrayList to array
        return new ResponseEntity<>(blocksService.getBlocks(token.get("token")), HttpStatus.OK);
    }

    @GetMapping("/check")
    public ResponseEntity<?> checkAndGetBlocks() throws WebException {

        //First, check for valid token authentication. If it's not valid, exception is thrown.
        Map<String,String> token = tokenService.getRooftopToken(environment.getProperty("user-email"));

        //Get blocks for given token and convert from arrayList to array
        BlocksResponseDTO dto = blocksService.getBlocks(token.get("token"));
        String[] blockArray = new String[dto.getData().size()];
        blockArray = dto.getData().toArray(blockArray);

        return new ResponseEntity<>(blocksService.check(blockArray, token.get("token")), HttpStatus.OK);
    }

    @PostMapping("/check")
    public ResponseEntity<?> checkBlocks(@RequestBody String[] blocks) throws WebException {

        //First, check for valid token authentication. If it's not valid, exception is thrown.
        Map<String,String> token = tokenService.getRooftopToken(environment.getProperty("user-email"));

        return new ResponseEntity<>(blocksService.check(blocks, token.get("token")), HttpStatus.OK);
    }
    
}