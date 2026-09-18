package example.Activity2;

import java.net.URI;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;


@Service 
public class DustService {
            @Value ("${api.public-data.service-key}")
            private String serviceKey;
    public String getDustData(){
// &year=2020&itemCode=PM10
        String url = "https://apis.data.go.kr/B552584/UlfptcaAlarmInqireSvc/getUlfptcaAlarmInfo"
                + "?serviceKey=" + serviceKey
                + "&returnType=json"
                + "&numOfRows=15"
                + "&pageNo=1"
                +"&year=2020"
                +"&itemCode=PM10";
        RestTemplate restTemplate = new RestTemplate();
        String response = 
        restTemplate.getForObject(URI.create(url),
        String.class);
        return response;
    }
}
