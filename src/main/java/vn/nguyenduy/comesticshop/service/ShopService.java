package vn.nguyenduy.comesticshop.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import vn.nguyenduy.comesticshop.domain.Shop;
import vn.nguyenduy.comesticshop.domain.User;
import vn.nguyenduy.comesticshop.repository.ShopRepository;

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

    public Shop findById(Long shopId) {
        return shopRepository.findById(shopId).orElseThrow(() -> new RuntimeException("Shop not found"));
    }

    public void save(Shop shop) {
        shopRepository.save(shop);
    }

    public Page<Shop> getAllShops(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return shopRepository.findAll(pageable);
    }

    public Shop getShopById(Long id) {
        return shopRepository.findById(id).orElse(null);
    }

    public void deleteShop(Long id) {
        shopRepository.deleteById(id);
    }
}
