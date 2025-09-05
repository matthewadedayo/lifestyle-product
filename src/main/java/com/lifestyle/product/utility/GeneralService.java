package com.lifestyle.product.utility;




import jakarta.servlet.http.HttpServletRequest;

public interface GeneralService {

    Response prepareFailedResponse(int code, String message);
    Response prepareSuccessResponse(Object data);
    //RequestExtraInfo getRequestExtraInfo(HttpServletRequest request);
}
