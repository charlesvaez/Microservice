package com.hgsoft.springcloud.webflux.controller;

import com.hgsoft.springcloud.webflux.domain.City;
import com.hgsoft.springcloud.webflux.handler.CityHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping(value = "/city")
public class CityWebFluxController {

    @Autowired
    private CityHandler cityHandler;

    @GetMapping(value = "/{id}")
    public Mono<City> findCityById(@PathVariable("id") Long id) {
        return cityHandler.findCityById(id);
    }
    //http://127.0.0.1:8080/city
    @GetMapping()
    public Flux<City> findAllCity() {
        return cityHandler.findAllCity();
    }

    //http://127.0.0.1:8899/city
    /*
    {
       "id":2,
       "provinceId":3,
       "cityName":"温岭",
       "description":"温岭是个靠海的好地方"
    }
    */
    @PostMapping()
    public Mono<Long> saveCity(@RequestBody City city) {
        return cityHandler.save(city);
    }

    @PutMapping()
    public Mono<Long> modifyCity(@RequestBody City city) {
        return cityHandler.modifyCity(city);
    }

    @DeleteMapping(value = "/{id}")
    public Mono<Long> deleteCity(@PathVariable("id") Long id) {
        return cityHandler.deleteCity(id);
    }
}
