package vn.nguyenduy.laptopshop.service;

import org.springframework.stereotype.Service;
import vn.nguyenduy.laptopshop.domain.Shop;
import vn.nguyenduy.laptopshop.domain.User;
import vn.nguyenduy.laptopshop.repository.ShopRepository;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

@Service
public class ShopService {

    private final ShopRepository shopRepository;

    public ShopService(ShopRepository shopRepository) {
        this.shopRepository = shopRepository;
    }

    public void saveShop(Shop shop) {
        this.shopRepository.save(shop);
    }

    public Shop findByOwner(User user) {
        return this.shopRepository.findByOwner(user);
    }

    public Shop findById(long shopId) {
        return this.shopRepository.findById(shopId);
    }

    public List<Shop> findAll() {
        return this.shopRepository.findAll();
    }

    public String saveLogoFile(byte[] logoBytes, String shopName) throws IOException {
        String logoFilePath = "uploads/logos/" + shopName + ".png";
        File logoFile = new File(logoFilePath);
        logoFile.getParentFile().mkdirs();
        try (FileOutputStream fos = new FileOutputStream(logoFile)) {
            fos.write(logoBytes);
        }
        return logoFilePath;
    }
}
