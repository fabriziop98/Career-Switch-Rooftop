/**
 * @author fpratici
 */
package com.fabrizio.rooftopchallenge.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import com.fabrizio.rooftopchallenge.dto.BlocksResponseDTO;
import com.fabrizio.rooftopchallenge.exception.WebException;
import com.fabrizio.rooftopchallenge.service.BlocksService;
import com.fabrizio.rooftopchallenge.utils.CallApiUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

@Service
public class BlocksServiceImpl implements BlocksService {

    @Autowired
    private CallApiUtils callApiUtils;

    @Autowired
    private Environment environment;

    @Override
    public String check(String[] blocks, String token) throws WebException {

        if(blocks == null ) throw new WebException("The given blocks are not valid");
        List<String> blocksArray = Arrays.asList(blocks);
        blocksArray = new ArrayList<>(blocksArray);
        if(blocksArray == null || blocksArray.isEmpty()) throw new WebException("The given blocks are not valid");

        String checkApiUrl = environment.getProperty("check-blocks").concat(token);

        List<String> orderedBlocks = new ArrayList<>();
        //We asume that the first element is ordered
        orderedBlocks.add(blocksArray.get(0));
        blocksArray.remove(0);

        do {
            int blockIndex = 0;
            for (String block : blocksArray) {

                // For the last block, we asume that it's ordered.
                if (blocksArray.size() == 1) {
                    orderedBlocks.add(block);
                    blocksArray.remove(blockIndex);
                    break;
                }

                // Create temporary array with blocks to check
                List<String> pairOfBlocks = new ArrayList<>();
                pairOfBlocks.add(orderedBlocks.get(orderedBlocks.size() - 1));
                pairOfBlocks.add(block);
                Map<String, Object> request = new HashMap<>();
                request.put("blocks", pairOfBlocks);

                // endpoint created to check for correct order of two blocks
                Map<String, Object> checkResponse = callApiUtils.postMapObjectWithQueryParams(checkApiUrl, request);
                boolean isOrdered = Boolean.valueOf(checkResponse.get("message").toString());

                if (isOrdered) {
                    orderedBlocks.add(block);
                    blocksArray.remove(blockIndex);
                    break;
                }
                blockIndex++;
            }
        } while (blocksArray.size() > 0);
       
        return orderedBlocks.stream().collect(Collectors.joining());
    }

    @Override
    public BlocksResponseDTO getBlocks(String token) throws WebException {

        BlocksResponseDTO dto = new BlocksResponseDTO();

        //Call /blocks API with valid token and get raw response.
        String blocksApiUrl = environment.getProperty("get-blocks").concat(token);
        Map<String, Object> blocksResponse = callApiUtils.getMapObjectWithQueryParams(blocksApiUrl); 

        //Check for valid response 
        if(blocksResponse != null && blocksResponse.get("data") != null){
           dto = convertRawResponseToBlocksResponseDTO(blocksResponse);
        }

        return dto;
    }

    @Override
    public BlocksResponseDTO convertRawResponseToBlocksResponseDTO(Map<String, Object> blocksResponse){
        BlocksResponseDTO dto = new BlocksResponseDTO();
        dto.setChunkSize(Double.valueOf(blocksResponse.get("chunkSize").toString()).intValue());
        dto.setLength(Double.valueOf(blocksResponse.get("length").toString()).intValue());
        //As 'data' response is an array, we access and cast its content to an arraylist
        List<?> dataObjects = new ArrayList<>();
        if (blocksResponse.get("data") instanceof Collection) {
        	dataObjects = new ArrayList<>((Collection<?>) blocksResponse.get("data"));
        }
        List<String> dataStringContent = dataObjects.stream()
            .map(object -> Objects.toString(object, null))
            .collect(Collectors.toList());
        dto.setData(dataStringContent);
        return dto;
    }

}