package common;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.IDIOM.pages.ClientDTO;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;

public class ClientRestApiUtil extends BaseClass {

  static RestTemplate restTemplate = new RestTemplate();

  @SuppressWarnings("unchecked")
  public static List<String> getClientList(String userName, String password) {
    List<String> clientList = new ArrayList<String>();
    List<String> subClientList = new ArrayList<String>();
    HttpHeaders headers = new HttpHeaders();
    headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
    headers.setContentType(MediaType.APPLICATION_JSON);
    String request = "{\"username\":\"" + userName + "\",\"password\":\"" + password + "\"}";
    HttpEntity<String> entity = new HttpEntity<String>(request, headers);
    ResponseEntity<String> responseEntity = restTemplate.exchange(
        "http://172.19.50.32:8080/idiom-services/security/auth", HttpMethod.POST, entity,
        String.class);
    JsonElement jelement = new JsonParser().parse(responseEntity.getBody());
    JsonObject jobject = jelement.getAsJsonObject();
    String userId = jobject.get("id").toString();
    Map<String, String> parameters = new HashMap<String, String>();
    parameters.put("userId", userId.toString());
    String clientListUrl = buildPathVariablesToUri(property.getProperty("clientListUrl"),
        parameters);
    headers.add("Cookie", responseEntity.getHeaders().getFirst("Set-Cookie"));
    entity = new HttpEntity<String>(request, headers);
    ResponseEntity<String> clientListRespEntity = restTemplate.exchange(clientListUrl,
        HttpMethod.GET, entity, String.class);
    Gson gson = new Gson();
    List<ClientDTO> clientDTOList = (List<ClientDTO>) gson.fromJson(clientListRespEntity.getBody(),
        new TypeToken<List<ClientDTO>>() {
        }.getType());
    for (ClientDTO clientDTO : clientDTOList) {
      if (clientDTO.getHasChild()) {
        subClientList = getSubClientList(clientDTO.getId(), entity, new ArrayList<String>());
      } else {
        clientList.add(clientDTO.getName());
      }
    }
    clientList.addAll(subClientList);
    return clientList;
  }

  @SuppressWarnings("unchecked")
  private static List<String> getSubClientList(Integer clientId, HttpEntity<String> entity,
      List<String> subClientList) {
    Map<String, String> parametersSubClients = new HashMap<String, String>();
    parametersSubClients.put("clientId", clientId.toString());
    String subClientListUrl = buildPathVariablesToUri(property.getProperty("subClientListUrl"),
        parametersSubClients);
    ResponseEntity<String> clientListRespEntity = restTemplate.exchange(subClientListUrl,
        HttpMethod.GET, entity, String.class);
    Gson gson = new Gson();
    List<ClientDTO> subclientDTOList = (List<ClientDTO>) gson.fromJson(
        clientListRespEntity.getBody(), new TypeToken<List<ClientDTO>>() {
        }.getType());
    for (ClientDTO clientDTO : subclientDTOList) {
      if (clientDTO.getHasChild()) {
        getSubClientList(clientDTO.getId(), entity, subClientList);
      } else {
        subClientList.add(clientDTO.getName());
      }
    }
    return subClientList;
  }

  public static String buildPathVariablesToUri(String url, Map<String, String> parameters) {
    UriComponentsBuilder urlBuilder = UriComponentsBuilder.fromHttpUrl(url);
    return urlBuilder.buildAndExpand(parameters).toUriString();
  }

}
