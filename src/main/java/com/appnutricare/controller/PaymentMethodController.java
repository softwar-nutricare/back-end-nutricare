package com.appnutricare.controller;

import com.appnutricare.entities.PaymentMethod;
import com.appnutricare.entities.Recipe;
import com.appnutricare.service.IPaymentMethodService;
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
import java.util.Date;
import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/PaymentMethods")
@Api(tags="PaymentMethod", value = "Servicio Web RESTFul de PaymentMethod")
public class PaymentMethodController {

    @Autowired
    private IPaymentMethodService paymentMethodService;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Listar PaymentMethods", notes="Método para listar todos los PaymentMethods")
    @ApiResponses({
            @ApiResponse(code=201, message = "PaymentMethods encontrados"),
            @ApiResponse(code=404, message = "PaymentMethods no encontrados")
    })
    public ResponseEntity<List<PaymentMethod>> findAllPaymentMethod(){
        try{
            List<PaymentMethod> paymentMethods = paymentMethodService.getAll();
            if(paymentMethods.size()>0)
                return new ResponseEntity<List<PaymentMethod>>(paymentMethods, HttpStatus.OK);
            else
                return new ResponseEntity<List<PaymentMethod>>(HttpStatus.NO_CONTENT);
        }catch (Exception e){
            return new ResponseEntity<List<PaymentMethod>>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value="/{id}" , produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Buscar PaymentMethod por Id", notes="Método para listar un PaymentMethod por Id")
    @ApiResponses({
            @ApiResponse(code=201, message = "PaymentMethod encontrado"),
            @ApiResponse(code=404, message = "PaymentMethod no encontrado")
    })
    public ResponseEntity<PaymentMethod>findPaymentMethodById(@PathVariable("id") Integer id){
        try{
            Optional<PaymentMethod> paymentMethod= paymentMethodService.getById(id);
            if(!paymentMethod.isPresent())
                return new ResponseEntity<PaymentMethod>(HttpStatus.NOT_FOUND);
            else
                return new ResponseEntity<PaymentMethod>(paymentMethod.get(),HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<PaymentMethod>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Registro de un PaymentMethod de un Client", notes ="Método que registra un PaymentMethod" )
    @ApiResponses({
            @ApiResponse(code=201, message = "PaymentMethod creado"),
            @ApiResponse(code=404, message = "PaymentMethod no creado")
    })
    public ResponseEntity<PaymentMethod> insertPaymentMethod(@Valid @RequestBody PaymentMethod paymentMethod){
        try{
            PaymentMethod paymentMethodNew = paymentMethodService.save(paymentMethod);
            return ResponseEntity.status(HttpStatus.CREATED).body(paymentMethodNew);
        }catch (Exception e){
            return new ResponseEntity<PaymentMethod>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value="Actualización de datos de PaymentMethod", notes = "Método que actualizar los datos de PaymentMethod")
    @ApiResponses({
            @ApiResponse(code=200, message = "Datos de PaymentMethod actualizados"),
            @ApiResponse(code=404, message = "PaymentMethod no actualizado")
    })
    public ResponseEntity<PaymentMethod> updatePaymentMethod(@PathVariable("id") Integer id, @Valid @RequestBody PaymentMethod paymentMethod){
        try{
            Optional<PaymentMethod> paymentMethodOld = paymentMethodService.getById(id);
            if(!paymentMethodOld.isPresent())
                return new ResponseEntity<PaymentMethod>(HttpStatus.NOT_FOUND);
            else{
                paymentMethod.setId(id);
                paymentMethodService.save(paymentMethod);
                return new ResponseEntity<PaymentMethod>(paymentMethod, HttpStatus.OK);
            }
        }catch (Exception e){
            return new ResponseEntity<PaymentMethod>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value="Eliminación de PaymentMethod por Id", notes = "Método que elimina los datos de un PaymentMethod")
    @ApiResponses({
            @ApiResponse(code=200, message = "Datos de PaymentMethod eliminados"),
            @ApiResponse(code=404, message = "PaymentMethod no eliminados")
    })
    public ResponseEntity<PaymentMethod> deletePaymentMethod(@PathVariable("id") Integer id){
        try{
            Optional<PaymentMethod> paymentMethodDelete = paymentMethodService.getById(id);
            if(paymentMethodDelete.isPresent()){
                paymentMethodService.delete(id);
                return new ResponseEntity<PaymentMethod>(HttpStatus.OK);
            }
            else
                return new ResponseEntity<PaymentMethod>(HttpStatus.NOT_FOUND);
        }catch (Exception e){
            return new ResponseEntity<PaymentMethod>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "/searchPaymentMethodByClientId/{client_id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Buscar PaymentMethod por client id", notes = "Método para encontrar PaymentMethod por client id")
    @ApiResponses({
            @ApiResponse(code = 201, message = "PaymentMethod encontrados"),
            @ApiResponse(code = 404, message = "PaymentMethod no encontrados")
    })
    public ResponseEntity<List<PaymentMethod>> findByClient(@PathVariable("client_id") Integer client_id)
    {
        try{
            List<PaymentMethod> paymentMethods = paymentMethodService.findAllByClient(client_id);
            if(paymentMethods.size()>0)
                return new ResponseEntity<List<PaymentMethod>>(paymentMethods, HttpStatus.OK);
            return new ResponseEntity<List<PaymentMethod>>(HttpStatus.NOT_FOUND);
        }catch (Exception e){
            return new ResponseEntity<List<PaymentMethod>>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
