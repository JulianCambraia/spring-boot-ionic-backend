package br.com.juliancambraia.cursomc.services.validation;

import br.com.juliancambraia.cursomc.domain.Cliente;
import br.com.juliancambraia.cursomc.dto.ClienteDTO;
import br.com.juliancambraia.cursomc.repositories.ClienteRepository;
import br.com.juliancambraia.cursomc.resources.exceptions.FieldMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerMapping;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ClienteUpdateValidator implements ConstraintValidator<ClienteUpdate, ClienteDTO> {

    @Autowired
    private HttpServletRequest request;

    @Autowired
    private ClienteRepository clienteRepository;

    @Override
    public void initialize(ClienteUpdate constraintAnnotation) {

    }

    /**
     * Valida se na atualização do cliente já existe um Email para um cliente que não seja o cliente que está sendo atualizado, ou seja
     * um cliente com ID diferente do cliente da atualização.
     *
     * @param value
     * @param context
     * @return
     */
    @Override
    public boolean isValid(ClienteDTO value, ConstraintValidatorContext context) {

        @SuppressWarnings("unchecked")
        Map<String, String> map = (Map<String, String>) request.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
        Integer urlId = Integer.parseInt(map.get("id"));

        List<FieldMessage> list = new ArrayList<>();

        Cliente cliente = clienteRepository.findByEmail(value.getEmail());

        if (cliente != null && !cliente.getId().equals(urlId)) {
            list.add(new FieldMessage("email", "Já existe um cliente com este email."));
        }

        for (FieldMessage e : list) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(e.getMessage()).addPropertyNode(e.getFieldName()).addConstraintViolation();
        }
        return list.isEmpty();
    }
}
