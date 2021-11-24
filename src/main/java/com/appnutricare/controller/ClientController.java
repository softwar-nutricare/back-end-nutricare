package com.appnutricare.controller;

import com.appnutricare.entities.Client;
import com.appnutricare.entities.ClientFavoriteRecipes;
import com.appnutricare.entities.ClientFavoriteRecipesFK;
import com.appnutricare.entities.Recipe;
import com.appnutricare.repository.IClientFavoriteRecipesRepository;
import com.appnutricare.service.IClientService;
import com.appnutricare.service.IRecipeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/clients")
@Api(tags = "Client", value = "Servicio Web RESTful de Clients")
public class ClientController {

    @Autowired
    private IClientService clientService;
    @Autowired
    private IClientFavoriteRecipesRepository clientFavoriteRecipesRepository;
    @Autowired
    private IRecipeService recipeService;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Listar Clients", notes = "Método para listar todos los clients")
    @ApiResponses({
            @ApiResponse(code = 201, message = "Clients encontrados"),
            @ApiResponse(code = 404, message = "Clients no encontrados")
    })
    public ResponseEntity<List<Client>> findAll() {
        try {
            List<Client> clients = new ArrayList<>();
            clients = clientService.getAll();
            if (clients.isEmpty())
                return new ResponseEntity<List<Client>>(HttpStatus.NOT_FOUND);
            return new ResponseEntity<List<Client>>(clients, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<List<Client>>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Buscar Client por Id", notes = "Método para encontrar un Client por Id")
    @ApiResponses({
            @ApiResponse(code = 201, message = "Client encontrado"),
            @ApiResponse(code = 404, message = "Client no encontrado")
    })
    public ResponseEntity<Client> findById(@PathVariable("id") Integer id)
    {
        try{
            Optional<Client> client = clientService.getById(id);
            if(!client.isPresent())
                return new ResponseEntity<Client>(HttpStatus.NOT_FOUND);
            return new ResponseEntity<Client>(client.get(), HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<Client>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "/searchByUsername/{username}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Buscar Client por username", notes = "Método para encontrar un Client por username")
    @ApiResponses({
            @ApiResponse(code = 201, message = "Client encontrado"),
            @ApiResponse(code = 404, message = "Client no encontrado")
    })
    public ResponseEntity<Client> findByUsername(@PathVariable("username") String username)
    {
        try{
            Client client = clientService.findByUsername(username);
            if(client == null)
                return new ResponseEntity<Client>(HttpStatus.NOT_FOUND);
            return new ResponseEntity<Client>(client, HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<Client>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "/searchByFirstName/{firstname}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Buscar Clients por firstname", notes = "Método para encontrar Clients por firstname")
    @ApiResponses({
            @ApiResponse(code = 201, message = "Clients encontrados"),
            @ApiResponse(code = 404, message = "Clients no encontrados")
    })
    public ResponseEntity<List<Client>> findByFirstName(@PathVariable("firstname") String firstname)
    {
        try {
            List<Client> clients = new ArrayList<>();
            clients = clientService.findByFirstName(firstname);
            if(clients.isEmpty())
                return new ResponseEntity<List<Client>>(HttpStatus.NOT_FOUND);
            return new ResponseEntity<List<Client>>(clients, HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<List<Client>>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "/searchByLastName/{lastname}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Buscar Clients por lastname", notes = "Método para encontrar Clients por lastname")
    @ApiResponses({
            @ApiResponse(code = 201, message = "Clients encontrados"),
            @ApiResponse(code = 404, message = "Clients no encontrados")
    })
    public ResponseEntity<List<Client>> findByLastName(@PathVariable("lastname") String lastname)
    {
        try {
            List<Client> clients = new ArrayList<>();
            clients = clientService.findByLastName(lastname);
            if(clients.isEmpty())
                return new ResponseEntity<List<Client>>(HttpStatus.NOT_FOUND);
            return new ResponseEntity<List<Client>>(clients, HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<List<Client>>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "/searchByFirstNameAndLastName/{firstname}/{lastname}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Buscar Clients por firstname y lastname", notes = "Método para encontrar Clients por firstname y lastname")
    @ApiResponses({
            @ApiResponse(code = 201, message = "Clients encontrados"),
            @ApiResponse(code = 404, message = "Clients no encontrados")
    })
    public ResponseEntity<List<Client>> findByFirstNameAndLastName(@PathVariable("firstname") String firstname, @PathVariable("lastname") String lastname)
    {
        try {
            List<Client> clients = new ArrayList<>();
            clients = clientService.findByFirstNameAndLastName(firstname, lastname);
            if(clients.isEmpty())
                return new ResponseEntity<List<Client>>(HttpStatus.NOT_FOUND);
            return new ResponseEntity<List<Client>>(clients, HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<List<Client>>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Registro de Clients", notes = "Método para agregar un Client")
    @ApiResponses({
            @ApiResponse(code = 201, message = "Client agregado"),
            @ApiResponse(code = 404, message = "Client no fue agregado")
    })
    public ResponseEntity<Client> insertCustomer(@Valid @RequestBody Client client)
    {
        try{
            Client newClient = clientService.save(client);
            return ResponseEntity.status(HttpStatus.CREATED).body(newClient);
        }catch (Exception e){
            return new ResponseEntity<Client>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Actualización de datos de Client", notes = "Método que actualiza los datos de un Client")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Client actualizado"),
            @ApiResponse(code = 404, message = "Client no fue encontrado")
    })
    public ResponseEntity<Client> updateClient(
            @PathVariable("id") Integer id, @Valid @RequestBody Client client){
        try {
            Optional<Client> foundClient = clientService.getById(id);
            if(!foundClient.isPresent())
                return new ResponseEntity<Client>(HttpStatus.NOT_FOUND);
            client.setId(id);
            clientService.save(client);
            return new ResponseEntity<Client>(client, HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<Client>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Eliminación de un Client", notes = "Método para eliminar un Client")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Client eliminado"),
            @ApiResponse(code = 404, message = "Client no encontrado")
    })
    public ResponseEntity<Client> deleteCustomer(@PathVariable("id") Integer id)
    {
        try{
            Optional<Client> deletedCustomer = clientService.getById(id);
            if(!deletedCustomer.isPresent())
                return new ResponseEntity<Client>(HttpStatus.NOT_FOUND);
            clientService.delete(id);
            return new ResponseEntity<Client>(HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<Client>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping(value = "/{recipe_id}/{client_id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Adición de Recipe favorita a la lista de favoritos de un Client", notes = "Método que añade una Recipe favorita a la lista de favoritos de un Client")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Recipe añadida a la lista de favoritos del Client"),
            @ApiResponse(code = 404, message = "Recipe o Client no encontrado")
    })
    public ResponseEntity<ClientFavoriteRecipes> addFavoriteRecipe(@PathVariable("recipe_id") Integer recipe_id,
                                                    @PathVariable("client_id") Integer client_id,
                                                    @Valid @RequestBody Client client,
                                                    @RequestParam("date") String date){
        try {
            Date currentDate = ParseDate(date);
            Optional<Recipe> foundRecipe = recipeService.getById(recipe_id);
            Optional<Client> foundClient = clientService.getById(client_id);
            if(!foundClient.isPresent())
                return new ResponseEntity<ClientFavoriteRecipes>(HttpStatus.NOT_FOUND);
            if(!foundRecipe.isPresent())
                return new ResponseEntity<ClientFavoriteRecipes>(HttpStatus.NOT_FOUND);
            ClientFavoriteRecipesFK newFKS = new ClientFavoriteRecipesFK(client_id, recipe_id);
            ClientFavoriteRecipes clientFavoriteRecipes = new ClientFavoriteRecipes(newFKS, foundClient.get(), foundRecipe.get(), currentDate);

            clientFavoriteRecipesRepository.save(clientFavoriteRecipes);
            return ResponseEntity.status(HttpStatus.CREATED).body(clientFavoriteRecipes);
        }catch (Exception e){
            return new ResponseEntity<ClientFavoriteRecipes>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "/findClientFavoriteRecipes/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Buscar Recipes favoritos de un Client", notes = "Método para listar Recipes favoritos de un Clients")
    @ApiResponses({
            @ApiResponse(code = 201, message = "Recipes encontrados"),
            @ApiResponse(code = 404, message = "Recipes no encontrados")
    })
    public ResponseEntity<List<Recipe>> findClientFavoriteRecipes(@PathVariable("id") Integer id)
    {
        try {
            List<Recipe> recipes = new ArrayList<>();
            recipes = clientFavoriteRecipesRepository.findByClient(id);
            if(recipes.isEmpty())
                return new ResponseEntity<List<Recipe>>(HttpStatus.NOT_FOUND);
            return new ResponseEntity<List<Recipe>>(recipes, HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<List<Recipe>>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping(value = "/{recipe_id}/{client_id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Eliminación de un Recipe de la lista de favoritos de un Client", notes = "Método para eliminar un Recipe de la lista de favoritos de un Client")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Recipe eliminado"),
            @ApiResponse(code = 404, message = "Recipe no encontrado")
    })
    public ResponseEntity<Client> deleteClientFavoriteRecipe(@PathVariable("recipe_id") Integer recipe_id,
                                                             @PathVariable("client_id") Integer client_id)
    {
        try{
            ClientFavoriteRecipesFK newFKS = new ClientFavoriteRecipesFK(client_id, recipe_id);
            Optional<ClientFavoriteRecipes> clientFavoriteRecipes = clientFavoriteRecipesRepository.findById(newFKS);
            if(!clientFavoriteRecipes.isPresent())
                return new ResponseEntity<Client>(HttpStatus.NOT_FOUND);
            clientFavoriteRecipesRepository.delete(clientFavoriteRecipes.get());
            return new ResponseEntity<Client>(HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<Client>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public static Date ParseDate(String date){
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date result = null;
        try{
            result = format.parse(date);
        }catch (Exception e){
        }
        return result;
    }

}