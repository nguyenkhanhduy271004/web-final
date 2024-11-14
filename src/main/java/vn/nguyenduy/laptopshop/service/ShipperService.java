package vn.nguyenduy.laptopshop.service;

import java.util.List;

import org.springframework.stereotype.Service;

import vn.nguyenduy.laptopshop.domain.Shipper;
import vn.nguyenduy.laptopshop.repository.ShipperRepository;

@Service
public class ShipperService {

    private final ShipperRepository shipperRepository;

    public ShipperService(
            ShipperRepository shipperRepository) {
        this.shipperRepository = shipperRepository;
    }

    public List<Shipper> getAllShippers() {
        return this.shipperRepository.findAll();
    }

    public Shipper getShipperById(Long id) {
        return this.shipperRepository.findById(id).orElse(null);
    }

}
