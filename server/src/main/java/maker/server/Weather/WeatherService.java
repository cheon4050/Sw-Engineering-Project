package maker.server.Weather;

import lombok.RequiredArgsConstructor;
import maker.server.Entity.GetClothesResponse;
import maker.server.Entity.GetWeatherResponse;
import maker.server.Entity.Weather;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

@Service
@RequiredArgsConstructor
public class WeatherService {

    public ResponseEntity getWeather() throws Exception {
        Weather weather = new Weather();
        String nx = "60";
        String ny = "127";
        String baseDate = "20220611";
        String baseTime = "0200";
        String type = "json";
        String apiUrl = "http://apis.data.go.kr/1360000/VilageFcstInfoService_2.0/getVilageFcst";
        String serviceKey = "0VS1T%2B%2FVge%2BZHKroSjc8BoCHmXkgDKsOiVQBISwGkaFkVf%2BW3JyRRbtIcbFe1WFXdp%2Bmllvf%2BkKXSFyPRreQrA%3D%3D";

        StringBuilder urlBuilder = new StringBuilder(apiUrl);
        urlBuilder.append("?" + URLEncoder.encode("ServiceKey", "UTF-8") + "=" + serviceKey);
        urlBuilder.append("&" + URLEncoder.encode("nx", "UTF-8") + "=" + URLEncoder.encode(nx, "UTF-8")); //경도
        urlBuilder.append("&" + URLEncoder.encode("ny", "UTF-8") + "=" + URLEncoder.encode(ny, "UTF-8")); //위도
        urlBuilder.append("&" + URLEncoder.encode("base_date", "UTF-8") + "=" + URLEncoder.encode(baseDate, "UTF-8")); /* 조회하고싶은 날짜*/
        urlBuilder.append("&" + URLEncoder.encode("base_time", "UTF-8") + "=" + URLEncoder.encode(baseTime, "UTF-8")); /* 조회하고싶은 시간 AM 02시부터 3시간 단위 */
        urlBuilder.append("&" + URLEncoder.encode("dataType", "UTF-8") + "=" + URLEncoder.encode(type, "UTF-8"));    /* 타입 */
        urlBuilder.append("&" + URLEncoder.encode("pageNo", "UTF-8") + "=" + URLEncoder.encode("1", "UTF-8"));    /* 타입 */
        urlBuilder.append("&" + URLEncoder.encode("numOfRows", "UTF-8") + "=" + URLEncoder.encode("12", "UTF-8"));    /* 타입 */
        /*
         * GET방식으로 전송해서 파라미터 받아오기
         */
        URL url = new URL(urlBuilder.toString());

        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Content-type", "application/json");
        System.out.println("Response code: " + conn.getResponseCode());

        BufferedReader rd;
        if (conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
            rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        } else {
            rd = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
        }

        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = rd.readLine()) != null) {
            sb.append(line);
        }

        rd.close();
        conn.disconnect();
        String result = sb.toString();

        // 데이터를 파싱
        JSONObject jsonObj_1 = new JSONObject(result);
        JSONObject jsonObj_2 = jsonObj_1.getJSONObject("response");
        JSONObject jsonObj_3 = jsonObj_2.getJSONObject("body");
        JSONObject jsonObj_4 = jsonObj_3.getJSONObject("items");
        JSONArray jsonArray = jsonObj_4.getJSONArray("item");


        for (int i = 0; i < jsonArray.length(); i++) {
            jsonObj_4 = jsonArray.getJSONObject(i);
            String category = jsonObj_4.getString("category");
            if (category.equals("PCP") || category.equals("SNO"))
                continue;
            Double fcstValue = jsonObj_4.getDouble("fcstValue");

            switch (category) {
                case "POP":
                    weather.setProbability(fcstValue);
                    break;
                case "REH":
                    weather.setHumidity(fcstValue);
                    break;
                case "SKY":
                    String state = stateToString(fcstValue);
                    weather.setState(state);
                    break;
                case "TMP":
                    weather.setPresent(fcstValue);
                    break;
            }

        }
        GetWeatherResponse getWeatherResponse = new GetWeatherResponse(weather, 200, "날씨 조회 성공");
        return new ResponseEntity(getWeatherResponse, HttpStatus.OK);
    }
    private String stateToString(double value) {
        String result;
        if (0.0 <= value || value <= 5.0)
            result = "맑음";
        else if (6.0 <= value || value <= 8.0)
            result = "구름 많음";
        else
            result = "흐림";
        return result;
    }
}
