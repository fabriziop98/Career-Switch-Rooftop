/**
 * @author fpratici
 */
package com.fabrizio.rooftopchallenge.service;

import java.util.Map;

import com.fabrizio.rooftopchallenge.dto.BlocksResponseDTO;
import com.fabrizio.rooftopchallenge.exception.WebException;

public interface BlocksService {

    public String check(String[] blocks, String token) throws WebException;

    public BlocksResponseDTO getBlocks(String token) throws WebException;

    public BlocksResponseDTO convertRawResponseToBlocksResponseDTO(Map<String, Object> blocksResponse);

}