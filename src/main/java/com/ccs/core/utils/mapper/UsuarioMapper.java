package com.ccs.core.utils.mapper;

import com.ccs.api.model.input.UsuarioInput;
import com.ccs.api.model.output.UsuarioOutput;
import com.ccs.domain.model.Usuario;
import org.springframework.stereotype.Component;

@Component
public class UsuarioMapper extends AbstractMapper<UsuarioOutput, UsuarioInput, Usuario>{

}
