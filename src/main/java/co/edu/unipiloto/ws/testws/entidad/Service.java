package co.edu.unipiloto.ws.testws.entidad;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 *
 * author SR MOON
 */
@Path("services")
public class Service {

    private static Map<Integer, Person> persons = new HashMap<Integer, Person>();

    static {
        for (int i = 0; i < 10; i++) {
            Person person = new Person();
            int id = i + 1;
            person.setId(id);
            person.setFullname("My wonderfull Person " + id);
            int age = (new Random().nextInt(40) + 1);
            person.setAge(age);
            int salario_minimo = (new Random().nextInt(1200000) + 800000);
            person.setSalario((age * salario_minimo) / 3);
            persons.put(id, person);
        }
    }

    @GET
    @Path("/getPersonByIdXML/{id}")
    @Produces(MediaType.APPLICATION_XML)
    public Person getPersonByIdXML(@PathParam("id") int id) {
        return persons.get(id);
    }

    @GET
    @Path("/getPersonByIdJson/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Person getPersonByIdJson(@PathParam("id") int ld) {
        return persons.get(ld);
    }

    @GET
    @Path("/getAllPersonsInXML")
    @Produces(MediaType.APPLICATION_XML)
    public List<Person> getAllPersonsInXML() {
        return new ArrayList<Person>(persons.values());
    }

    @GET
    @Path("/getAllPersonsInJson")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Person> getAllPersonsInJson() {
        return new ArrayList<Person>(persons.values());
    }
/*---------------Adicionar un nuevo servicio REST que permita conocer el salario promedio en formato XML------------*/
    @GET
    @Path("/getSalarioPromedioXML")
    @Produces(MediaType.APPLICATION_XML)
    public Response getSalarioPromedioXML() {
        double totalSalario = 0;
        int totalPersonas = 0;
        for (Person person : persons.values()) {
            totalSalario += person.getSalario();
            totalPersonas++;
        }
        double salarioPromedio = totalSalario / totalPersonas;
        String xmlResponse = "<SalarioPromedio><promedio>" + salarioPromedio + "</promedio></SalarioPromedio>";
        return Response.ok(xmlResponse, MediaType.APPLICATION_XML).build();
    }
    /*----------- Adicionar un servicio REST para mostrar la suma de los salarios de las personas en formato JSON. -------------*/
    @GET
    @Path("/getSalarioSumaJSON")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getSalarioSumaJSON() {
        double totalSalario = 0;

        for (Person person : persons.values()) {
            totalSalario += person.getSalario();
        }

        String jsonResponse = "{\"SalarioSuma\":" + totalSalario + "}";

        return Response.ok(jsonResponse, MediaType.APPLICATION_JSON).build();
    }
    
    /*----------- Adicionar una nueva Persona a la lista ---------------*/
    @POST
    @Path("/addPerson")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addPerson(Person newPerson) {
        // Genera un nuevo ID para la persona
        int newId = generateNewId();
        newPerson.setId(newId);

        // Agrega la nueva persona a la lista
        persons.put(newId, newPerson);

        // Retorna la nueva persona con el ID asignado
        return Response.status(Response.Status.CREATED).entity(newPerson).build();
    }

    private int generateNewId() {
        // Genera un nuevo ID único para la persona (puede usar una lógica personalizada aquí)
        // Por ejemplo, puede usar un contador o un generador de IDs único.
        // En este ejemplo, se usa un valor aleatorio para simplificar.
        return new Random().nextInt(1000) + 100;
    }
  
}
