/**
 * @author fpratici
 */
package com.fabrizio.rooftopchallenge.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BlocksResponseDTO {

    private List<String> data;
    private Integer chunkSize;
    private Integer length;
    private String message;

    
}