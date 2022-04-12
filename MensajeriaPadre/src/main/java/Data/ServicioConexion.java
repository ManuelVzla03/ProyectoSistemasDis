/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/WebServices/JerseyClient.java to edit this template
 */
package Data;

import javax.ws.rs.client.WebTarget;

/**
 * Jersey REST client generated for REST resource:DataResource [data]<br>
 * USAGE:
 * <pre>
 *        ServicioConexion client = new ServicioConexion();
 *        Object response = client.XXX(...);
 *        // do whatever with response
 *        client.close();
 * </pre>
 *
 * @author orlandocamacho
 */
public class ServicioConexion {

    private javax.ws.rs.client.WebTarget webTarget;
    private javax.ws.rs.client.Client client;
    private static final String BASE_URI = "http://localhost:8888/moodle311/webservice/rest/server.php?wstoken=57a4f2f9b6a755a2885b9b8f25c32cfc&wsfunction=core_course_get_contents&moodlewsrestformat=json&courseid=4";
    public ServicioConexion() {
        client = javax.ws.rs.client.ClientBuilder.newClient();
        webTarget = client.target(BASE_URI).path("data");
    }

    public void putJson(Object requestEntity) throws javax.ws.rs.ClientErrorException {
        webTarget.request(javax.ws.rs.core.MediaType.APPLICATION_JSON).put(javax.ws.rs.client.Entity.entity(requestEntity, javax.ws.rs.core.MediaType.APPLICATION_JSON));
    }

    public <T> T getJson(Class<T> responseType) throws javax.ws.rs.ClientErrorException {
        WebTarget resource = webTarget;
        return resource.request(javax.ws.rs.core.MediaType.APPLICATION_JSON).get(responseType);
    }

    public void close() {
        client.close();
    }
    
}
