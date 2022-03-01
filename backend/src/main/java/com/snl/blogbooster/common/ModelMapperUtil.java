package com.snl.blogbooster.common;
import org.modelmapper.ModelMapper;

public class ModelMapperUtil {

    private static ModelMapper modelMapper = new ModelMapper();
    public static ModelMapper getModelMapper()
    {
        return modelMapper;
    }
}
