package com.ccs.core.utils.mapper;

import com.ccs.api.v1.model.input.UsuarioInput;
import com.ccs.api.v1.model.output.UsuarioOutput;
import com.ccs.domain.entity.Usuario;
import org.springframework.stereotype.Component;

@Component
public class UsuarioMapper extends AbstractMapper<UsuarioOutput, UsuarioInput, Usuario>{

}
