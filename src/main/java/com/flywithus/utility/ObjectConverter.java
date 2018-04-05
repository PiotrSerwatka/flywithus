package com.flywithus.utility;

import org.dozer.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.StreamSupport;

import static java.util.stream.Collectors.toList;

@Component
public class ObjectConverter {

    private Mapper mapper;

    public ObjectConverter(Mapper mapper) {
        this.mapper = mapper;
    }

    public <T> T convert(Object from, Class<T> to) {
        return mapper.map(from, to);
    }

    public <T> List<T> convert(Iterable<?> from, Class<T> to) {
        return StreamSupport.stream(from.spliterator(), false)
                .map(element -> convert(element, to))
                .collect(toList());
    }


}
