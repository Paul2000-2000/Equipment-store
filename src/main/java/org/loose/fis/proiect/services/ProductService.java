package org.loose.fis.proiect.services;

import org.dizitart.no2.Nitrite;
import org.dizitart.no2.objects.ObjectRepository;
import org.loose.fis.proiect.exceptions.UsernameAlreadyExistsException;
import org.loose.fis.proiect.model.Product;
import org.loose.fis.proiect.model.User;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Objects;
import static org.loose.fis.proiect.services.FileSystemService.getPathToFile;

public class ProductService {

    private static ObjectRepository<Product> productRepository;

    public static void initDatabase()
    {
        Nitrite database = Nitrite.builder()
                .filePath(getPathToFile("products.db").toFile())
                .openOrCreate("test", "test");

        productRepository = database.getRepository(Product.class);
    }
    public static void addProduct(String name, String price, String stock,String category,String company)
    {
        productRepository.insert(new Product(name, price, stock,category,company));
    }
    public static int checkProductAlreadyExist(String name,String stock)
    {
        int k=0;
        for (Product product : productRepository.find()) {
            if (Objects.equals(name, product.getName()))
            {
                String st=product.getStock();
                product.setStock(String.valueOf(Integer.parseInt(st)+Integer.parseInt(stock)));
                productRepository.update(product);
                k=1;
            }
        }
        return k;
    }
}
