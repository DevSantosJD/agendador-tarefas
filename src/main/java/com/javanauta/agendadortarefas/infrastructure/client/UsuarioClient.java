package com.javanauta.agendadortarefas.infrastructure.client;

import com.javanauta.agendadortarefas.buisness.dto.UsuarioDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

//Usar a anotação, nomeia a api apontada e utiliza uma variavel de ambiente
//que possui o endereço ca url da api que será acessada
@FeignClient(name = "usuario", url = "${usuario.url}")
public interface UsuarioClient {

    /*
    O metodo abaixo, possui a responsabilidade de apontar para a api
     que reotrnara os dados do usuario.
     */
    @GetMapping("/usuario")
    UsuarioDTO buscaUsuarioPorEmail(@RequestParam("email") String email,
                                    @RequestHeader("Authorization") String token);
}
