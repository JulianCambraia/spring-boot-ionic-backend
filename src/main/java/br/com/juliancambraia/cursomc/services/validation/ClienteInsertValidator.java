package br.com.juliancambraia.cursomc.services.validation;

import br.com.juliancambraia.cursomc.domain.Cliente;
import br.com.juliancambraia.cursomc.domain.enums.TipoClienteEnum;
import br.com.juliancambraia.cursomc.dto.ClienteNewDTO;
import br.com.juliancambraia.cursomc.repositories.ClienteRepository;
import br.com.juliancambraia.cursomc.resources.exceptions.FieldMessage;
import br.com.juliancambraia.cursomc.services.validation.utils.BR;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.ArrayList;
import java.util.List;

public class ClienteInsertValidator implements ConstraintValidator<ClienteInsert, ClienteNewDTO> {

    @Autowired
    private ClienteRepository clienteRepository;
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

        Cliente cliente = clienteRepository.findByEmail(value.getEmail());

        if (cliente != null) {
            list.add(new FieldMessage("email", "Já existe um cliente com este email."));
        }

        for (FieldMessage e : list) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(e.getMessage()).addPropertyNode(e.getFieldName()).addConstraintViolation();
        }
        return list.isEmpty();
    }
}
