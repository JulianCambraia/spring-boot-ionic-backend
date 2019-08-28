package br.com.juliancambraia.cursomc.services.validation;

import br.com.juliancambraia.cursomc.domain.enums.TipoClienteEnum;
import br.com.juliancambraia.cursomc.dto.ClienteNewDTO;
import br.com.juliancambraia.cursomc.resources.exceptions.FieldMessage;
import br.com.juliancambraia.cursomc.services.validation.utils.BR;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.ArrayList;
import java.util.List;

public class ClienteInsertValidator implements ConstraintValidator<ClienteInsert, ClienteNewDTO> {


    @Override
    public void initialize(ClienteInsert constraintAnnotation) {

    }

    @Override
    public boolean isValid(ClienteNewDTO value, ConstraintValidatorContext context) {
        List<FieldMessage> list = new ArrayList<>();
        if (value.getTipoClienteEnum().equals(TipoClienteEnum.PESSOAFISICA.getCodigo()) && !BR.isValidCPF(value.getCpfOuCnpj())) {
            list.add(new FieldMessage("cpfOuCnpj", "CPF inválido"));
        }

        if (value.getTipoClienteEnum().equals(TipoClienteEnum.PESSOAJURIDICA.getCodigo()) && !BR.isValidCNPJ(value.getCpfOuCnpj())) {
            list.add(new FieldMessage("cpfOuCnpj", "CNPJ inválido"));
        }

        for (FieldMessage e : list) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(e.getMessage()).addPropertyNode(e.getFieldName()).addConstraintViolation();
        }
        return list.isEmpty();
    }
}
