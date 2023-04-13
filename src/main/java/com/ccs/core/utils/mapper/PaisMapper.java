package com.ccs.core.utils.mapper;

import com.ccs.api.model.input.PaisInput;
import com.ccs.api.model.output.PaisOutput;
import com.ccs.domain.model.Pais;
import org.springframework.stereotype.Component;

@Component
public class PaisMapper extends AbstractMapper<PaisOutput, PaisInput, Pais> {
}
