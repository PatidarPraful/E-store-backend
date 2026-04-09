package com.prafull.ecom_project.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.prafull.ecom_project.model.Product;
import com.prafull.ecom_project.service.ProductService;



@RestController
@RequestMapping("/api")
@CrossOrigin
public class ProductController {

    @Autowired
    ProductService service;
       
    @RequestMapping("/")
    public String greet(){
        return "Hello World";
    }

    @RequestMapping("/products")
    public ResponseEntity<List<Product>> getAllProducts(){
        // Logic to fetch products from the database
        return new ResponseEntity<>(service.getAllProducts(), HttpStatus.OK);
    }

    @RequestMapping("/product/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable int id){

        Product product = service.getProductById(id);
        if(product == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(product, HttpStatus.OK);
    }

    @PostMapping("/product")
    public ResponseEntity<?> addProduct(@RequestPart Product product, @RequestPart MultipartFile imageFile){
        

        try{
            System.out.println("product adding");
            Product product1 = service.addProduct(product, imageFile);
            return new ResponseEntity<>(product1, HttpStatus.CREATED); 
        }
        catch(Exception e){
            return new ResponseEntity<>(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    @GetMapping("/product/{productId}/image")
    public ResponseEntity<byte []> getImageByProductId(@PathVariable int productId){
        Product product = service.getProductById(productId);
        if(product == null || product.getImageData() == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok()
                .header("Content-Type", product.getImageType())
                .body(product.getImageData());                                  
    }

    @PutMapping("/product/{id}")
    public ResponseEntity<String> updateProduct(@PathVariable int id, @RequestPart Product product, @RequestPart MultipartFile imageFile){
        Product product1 = null;
        try{
          product1 = service.udpateProduct(id, product, imageFile);
        }
        catch(Exception e){
            return new ResponseEntity<>(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        }
        

        if(product1 != null){
            return new ResponseEntity<>("Product updated successfully", HttpStatus.OK);
        }
        else{
            return new ResponseEntity<>("Product not found", HttpStatus.NOT_FOUND);
        }
        
    }

    @DeleteMapping("/product/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable int id){
        
        Product product = service.getProductById(id);

        if(product != null){
            service.deleteProduct(id);
            return new ResponseEntity<>("Product deleted successfully", HttpStatus.OK);
        }
        else{
            return new ResponseEntity<>("Product not found", HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/products/search")
    public ResponseEntity<List<Product>> searchProducts(@RequestParam String keyword){

        List<Product> products = service.searchProducts(keyword);

        return new ResponseEntity<>(products, HttpStatus.OK);
    }
}
