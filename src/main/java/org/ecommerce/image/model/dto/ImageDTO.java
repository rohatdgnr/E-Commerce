package org.ecommerce.image.model.dto;

import lombok.Data;

@Data
public class ImageDTO {  //Todo change to record
    private Long id;
    private String fileName;
    private String downloadUrl;
}
