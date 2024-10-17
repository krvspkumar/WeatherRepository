package org.ltimindtree.configuration;

import software.amazon.awssdk.enhanced.dynamodb.AttributeConverter;
import software.amazon.awssdk.enhanced.dynamodb.AttributeValueType;
import software.amazon.awssdk.enhanced.dynamodb.EnhancedType;
import software.amazon.awssdk.services.dynamodb.model.AttributeValue;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.ltimindtree.model.WeatherResponse.Weather;

public class WeatherArrayConverter implements AttributeConverter<Weather[]> {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public AttributeValue transformFrom(Weather[] input) {
        try {
            return AttributeValue.builder().s(objectMapper.writeValueAsString(input)).build();
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Error serializing Weather array", e);
        }
    }

    @Override
    public Weather[] transformTo(AttributeValue input) {
        try {
            return objectMapper.readValue(input.s(), Weather[].class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Error deserializing Weather array", e);
        }
    }

    @Override
    public EnhancedType<Weather[]> type() {
        return EnhancedType.of(Weather[].class);
    }

    @Override
    public AttributeValueType attributeValueType() {
        return AttributeValueType.S;
    }
}