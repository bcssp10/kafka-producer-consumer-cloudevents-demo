package it.infocert.domain;

public record Order(Long id, String item, int quantity) {
    // Constructor, getters, and setters

    public Order(Long id) {
        this(id, null, 0);
    }
}