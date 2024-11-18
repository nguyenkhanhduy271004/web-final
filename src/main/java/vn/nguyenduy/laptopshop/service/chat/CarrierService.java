package vn.nguyenduy.laptopshop.service.chat;

import vn.nguyenduy.laptopshop.domain.Carrier;
import vn.nguyenduy.laptopshop.repository.CarrierRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CarrierService {

    @Autowired
    private CarrierRepository carrierRepository;

    public List<Carrier> getAllCarriers() {
        return carrierRepository.findAll();
    }

    public Carrier saveCarrier(Carrier carrier) {
        return carrierRepository.save(carrier);
    }

    public Carrier updateCarrier(Long id, Carrier carrierDetails) {
        Carrier carrier = carrierRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Carrier not found"));

        carrier.setName(carrierDetails.getName());
        carrier.setShippingFee(carrierDetails.getShippingFee());
        return carrierRepository.save(carrier);
    }

    public void deleteCarrier(Long id) {
        carrierRepository.deleteById(id);
    }
}
