package com.deviget.minesweeper.util;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Mapper utility to handle Entity <-> mapping.-
 */
@Component
public class ModelMapperUtils {

    @Autowired
    private static final ModelMapper modelMapper;

    static {
        modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
    }

    private ModelMapperUtils() {
    }

    /**
     * Maps an object from one  class to another.-
     *
     * @param inObject the input class
     * @param outClass the output class
     * @param <D>      the type class of the output class
     * @param <T>      the input object to map
     * @return an object of type D with the populated parameters from inObject object
     */
    public <D, T> D map(final T inObject, final Class<D> outClass) {
        return modelMapper.map(inObject, outClass);
    }
}
