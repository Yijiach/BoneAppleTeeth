package edu.usc.csci.boneapptheteeth.service;

import edu.usc.csci.boneapptheteeth.mvc.dto.Hits;
import edu.usc.csci.boneapptheteeth.mvc.dto.Recipe;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class EdamamApiService {
    private String status;
    private Integer code;

    public String getStatus(){
        return status;
    }

    public void setStatus(String status){
        this.status = status;
    }

    public Integer getCode(){
        return code;
    }

    public void setCode(Integer code){
        this.code = code;
    }


    //returns
    public Recipe getRandomRecipe(){
        RestTemplateBuilder builder = new RestTemplateBuilder();
        RestTemplate restTemplate = builder.build();
//        HttpHeaders headers = new HttpHeaders();
        //headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

//        HttpEntity<String> entity = new HttpEntity<>(null, headers);
//        HashMap<String, String> parameters = new HashMap<>();
//        parameters.put("number","1");
//        parameters.put("apiKey","612a8ebcab0b449b8316b61349cc769e");
        Hits hits = restTemplate.getForObject("https://api.edamam.com/search?q=chicken&app_id=c311f2e8&app_key=f12e909221053a3cb5850cc10379f0da",
                Hits.class);

        return hits.getHits().get(0).getRecipe();
    }
}
