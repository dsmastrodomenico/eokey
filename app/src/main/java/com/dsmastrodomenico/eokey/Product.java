package com.dsmastrodomenico.eokey;

public class Product {
    private String IDProduct;
    private String Name;
    private String Description;
    private Double Price;

    public Product() {
    }

    public Product(String IDProduct, String name, String description, Double price) {
        this.IDProduct = IDProduct;
        Name = name;
        Description = description;
        Price = price;
    }

    public String getIDProduct() {
        return IDProduct;
    }

    public void setIDProduct(String IDProduct) {
        this.IDProduct = IDProduct;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public Double getPrice() {
        return Price;
    }

    public void setPrice(Double price) {
        Price = price;
    }
}
