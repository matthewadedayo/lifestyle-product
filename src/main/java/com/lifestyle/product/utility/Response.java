package com.lifestyle.product.utility;

import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Response {
    private int responseCode;
    private String responseMessage;
    private Object data;
}
