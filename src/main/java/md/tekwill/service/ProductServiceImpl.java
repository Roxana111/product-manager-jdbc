package md.tekwill.service;

import md.tekwill.entity.product.Drink;
import md.tekwill.entity.product.Food;
import md.tekwill.entity.product.FoodCategory;

import md.tekwill.dao.ProductRepository;
import md.tekwill.entity.product.FoodCategory;
import md.tekwill.entity.product.Product;
import md.tekwill.exceptions.ProductExistsException;
import md.tekwill.exceptions.ProductUpdateUnknownPropertyException;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;

    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public void create(String name, double price, LocalDate bestBefore, double volume) {
        if(productRepository.findByName(name)!=null){
            throw new ProductExistsException("Product with name "+name+" already exists! ");
        }
        Drink drink = new Drink(name, price, bestBefore, volume);
        productRepository.save(drink);
    }

    @Override
    public void create(String name, double price, LocalDate bestBefore, FoodCategory category) {
        if(productRepository.findByName(name)!=null){
            throw new ProductExistsException("Product with name "+name+" already exists! ");
        }
        Food food = new Food(name, price, bestBefore, category);
        productRepository.save(food);
    }

    @Override
    public List<Product> getAll() {
        return productRepository.findAll();
    }

    @Override
    public List<Product> getAllNonExpired() {
        List<Product> nonExpired = new ArrayList<>();
        for (Product p : getAll()) {
            if (p.getBestBefore().isAfter(LocalDate.now()) || p.getBestBefore().equals(LocalDate.now())) {
                nonExpired.add(p);
            }
        }
        return nonExpired;
    }

    @Override
    public List<Product> getAllExpired() {
        List<Product> expired = new ArrayList<>();
        for (Product p : getAll()) {
            if (p.getBestBefore().isBefore(LocalDate.now())) {
                expired.add(p);
            }
        }
        return expired;
    }

    @Override
    public Product getById(int id) {
        return productRepository.findById(id);
    }

    @Override
    public void update(int id, double volume) throws ProductUpdateUnknownPropertyException {
        Product product = getById(id);
        if (product instanceof Drink) {
            productRepository.update(id, volume);
            return;
        }
        throw new ProductUpdateUnknownPropertyException("Product with id  " + id + " is not a drink!");
    }


    @Override
    public void update(int id, FoodCategory category) throws ProductUpdateUnknownPropertyException {
        Product product = getById(id);
        if (product instanceof Food) {
            productRepository.update(id, category);
            return;
        }
        throw new ProductUpdateUnknownPropertyException("Product with id  " + id + " is not food!");
    }

    @Override
    public void delete(int id) {
        productRepository.delete(id);
    }
}
