package com.ccs.core.utils.mapper;

import com.ccs.api.v1.model.input.PaisInput;
import com.ccs.api.v1.model.output.PaisOutput;
import com.ccs.domain.entity.Pais;
import org.springframework.stereotype.Component;

@Component
public class PaisMapper extends AbstractMapper<PaisOutput, PaisInput, Pais> {
}
