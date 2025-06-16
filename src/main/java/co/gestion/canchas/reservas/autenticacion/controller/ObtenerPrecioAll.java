package co.gestion.canchas.reservas.autenticacion.controller;

import co.gestion.canchas.reservas.autenticacion.dto.PrecioDTO;
import co.gestion.canchas.reservas.autenticacion.service.PrecioService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/precios")
@RequiredArgsConstructor
public class ObtenerPrecioAll {

    private final PrecioService service;

    @GetMapping("/all")
    public ResponseEntity<List<PrecioDTO>> listAll() {
        return ResponseEntity.ok(service.findAll());
    }
}

