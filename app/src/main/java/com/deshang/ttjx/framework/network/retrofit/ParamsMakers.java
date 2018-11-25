package com.deshang.ttjx.framework.network.retrofit;


import com.google.gson.Gson;
import com.deshang.ttjx.framework.network.ParameterKeys;

import java.util.HashMap;
import java.util.Map;

public class ParamsMakers {

    private static final String AUTH = "auth";
    private static final String INFO = "info";

    private ParamsMakers() {
    }

    private static ParamsMakers requestMaker = null;

    /**
     * 得到JsonMaker的实例
     *
     * @return
     */
    public static ParamsMakers getInstance() {
        if (requestMaker == null) {
            requestMaker = new ParamsMakers();
            return requestMaker;
        } else {
            return requestMaker;
        }
    }


    public Map<String, Object> getDoctorTime(String consultType, String doctorId, String free, String scheduleDate) {
        Map<String, Object> paramsMap = new HashMap<>();
        try {
            Map<String, String> tempMap = new HashMap<String, String>();
            paramsMap.put("consultType", consultType);
            paramsMap.put("doctorId", doctorId);
            paramsMap.put("free", free);
            paramsMap.put("scheduleDate", scheduleDate);

            Gson gson = new Gson();
            String info = gson.toJson(tempMap);
            paramsMap.put(ParameterKeys.KEY_INFO, info);
            paramsMap.put(ParameterKeys.KEY_SIGN, "");
            paramsMap.put(ParameterKeys.KEY_TOKEN, "");

        } catch (Exception e) {
            e.printStackTrace();
        }
        return paramsMap;
    }


}
