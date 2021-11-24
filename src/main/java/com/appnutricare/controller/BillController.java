package com.appnutricare.controller;

import com.appnutricare.entities.Bill;
import com.appnutricare.entities.PaymentMethod;
import com.appnutricare.entities.Recipe;
import com.appnutricare.service.IBillService;
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
@RequestMapping("/api/Bills")
@Api(tags="Bill", value = "Servicio Web RESTFul de Bill")
public class BillController {

    @Autowired
    private IBillService billService;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Listar Bills", notes="Método para listar todos los Bills")
    @ApiResponses({
            @ApiResponse(code=201, message = "Bills encontrados"),
            @ApiResponse(code=404, message = "Bills no encontrados")
    })
    public ResponseEntity<List<Bill>> findAllBill(){
        try{
            List<Bill> bills = billService.getAll();
            if(bills.size()>0)
                return new ResponseEntity<List<Bill>>(bills, HttpStatus.OK);
            else
                return new ResponseEntity<List<Bill>>(HttpStatus.NO_CONTENT);
        }catch (Exception e){
            return new ResponseEntity<List<Bill>>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value="/{id}" , produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Buscar Bill por Id", notes="Método para listar un Bill por Id")
    @ApiResponses({
            @ApiResponse(code=201, message = "Bill encontrado"),
            @ApiResponse(code=404, message = "Bill no encontrado")
    })
    public ResponseEntity<Bill>findBillById(@PathVariable("id") Integer id){
        try{
            Optional<Bill> bill= billService.getById(id);
            if(!bill.isPresent())
                return new ResponseEntity<Bill>(HttpStatus.NOT_FOUND);
            else
                return new ResponseEntity<Bill>(bill.get(),HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<Bill>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Registro de un Bill de un Client", notes ="Método que registra un Bill" )
    @ApiResponses({
            @ApiResponse(code=201, message = "Bill creado"),
            @ApiResponse(code=404, message = "Bill no creado")
    })
    public ResponseEntity<Bill> insertBill(@Valid @RequestBody Bill bill){
        try{
            Bill billNew = billService.save(bill);
            return ResponseEntity.status(HttpStatus.CREATED).body(billNew);
        }catch (Exception e){
            return new ResponseEntity<Bill>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value="Actualización de datos de Bill", notes = "Método que actualizar los datos de Bill")
    @ApiResponses({
            @ApiResponse(code=200, message = "Datos de Bill actualizados"),
            @ApiResponse(code=404, message = "Bill no actualizado")
    })
    public ResponseEntity<Bill> updateBill(@PathVariable("id") Integer id, @Valid @RequestBody Bill bill){
        try{
            Optional<Bill> billOld = billService.getById(id);
            if(!billOld.isPresent())
                return new ResponseEntity<Bill>(HttpStatus.NOT_FOUND);
            else{
                bill.setId(id);
                billService.save(bill);
                return new ResponseEntity<Bill>(bill, HttpStatus.OK);
            }
        }catch (Exception e){
            return new ResponseEntity<Bill>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value="Eliminación de Bill por Id", notes = "Método que elimina los datos de un Bill")
    @ApiResponses({
            @ApiResponse(code=200, message = "Datos de Bill eliminados"),
            @ApiResponse(code=404, message = "Bill no eliminados")
    })
    public ResponseEntity<Bill> deleteBill(@PathVariable("id") Integer id){
        try{
            Optional<Bill> billDelete = billService.getById(id);
            if(billDelete.isPresent()){
                billService.delete(id);
                return new ResponseEntity<Bill>(HttpStatus.OK);
            }
            else
                return new ResponseEntity<Bill>(HttpStatus.NOT_FOUND);
        }catch (Exception e){
            return new ResponseEntity<Bill>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "/searchBillByClientId/{client_id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Buscar Bill por client id", notes = "Método para encontrar Bill por client id")
    @ApiResponses({
            @ApiResponse(code = 201, message = "Bill encontrados"),
            @ApiResponse(code = 404, message = "Bill no encontrados")
    })
    public ResponseEntity<List<Bill>> findByClient(@PathVariable("client_id") Integer client_id)
    {
        try{
            List<Bill> bills = billService.findAllByClient(client_id);
            if(bills.size()>0)
                return new ResponseEntity<List<Bill>>(bills, HttpStatus.OK);
            return new ResponseEntity<List<Bill>>(HttpStatus.NOT_FOUND);
        }catch (Exception e){
            return new ResponseEntity<List<Bill>>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "/searchBetweenDates", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Buscar Bill entre fechas", notes = "Método para listar Bill entre fechas")
    @ApiResponses({
            @ApiResponse(code=201, message = "Bill encontrados"),
            @ApiResponse(code=404, message = "Bill no encontrados")
    }) //Al requestparam le puedes decir que sea opcional y no necesita estar en el URL
    public ResponseEntity<List<Bill>> findBillByBillDateBetweenDates(@RequestParam("date1") String date1_string,
                                                                           @RequestParam("date2") String date2_string){
        try{
            Date checking_date = ParseDate(date1_string);
            Date checkout_date = ParseDate(date2_string);
            List<Bill> bills = billService.findBetweenDates(checking_date, checkout_date);
            if(bills!=null && bills.size()>0)
                return new ResponseEntity<List<Bill>>(bills, HttpStatus.OK);
            else
                return new ResponseEntity<List<Bill>>(bills, HttpStatus.NOT_FOUND);
        }catch (Exception e){
            return new ResponseEntity<List<Bill>>(HttpStatus.INTERNAL_SERVER_ERROR);
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