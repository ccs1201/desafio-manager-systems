package com.ccs.core.utils.mapper;

import com.ccs.api.v1.model.input.UsuarioInput;
import com.ccs.api.v1.output.UsuarioOutput;
import com.ccs.domain.model.Usuario;
import org.springframework.stereotype.Component;

@Component
public class UsuarioMapper extends AbstractMapper<UsuarioOutput, UsuarioInput, Usuario>{

}
