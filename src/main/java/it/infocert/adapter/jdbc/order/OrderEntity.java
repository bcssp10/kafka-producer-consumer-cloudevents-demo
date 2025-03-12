package it.infocert.adapter.jdbc.order;

import org.springframework.data.annotation.Id;

public record OrderEntity(@Id Long id, String item, int quantity) {

}