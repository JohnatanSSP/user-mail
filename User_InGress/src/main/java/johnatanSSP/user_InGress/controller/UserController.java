package johnatanSSP.user_InGress.controller;

import johnatanSSP.user_InGress.domain.UserModel;
import johnatanSSP.user_InGress.dto.UserDto;
import johnatanSSP.user_InGress.service.UserService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

// OpenAPI / Swagger annotations
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.Valid;

@RestController
@Tag(name = "Users", description = "Operações de gerenciamento de usuários")
public class UserController {

    @Autowired
    final UserService service;

    public UserController(UserService service) {
        this.service = service;
    }

    @Operation(summary = "Cria um novo usuário", description = "Cria um usuário e publica o evento via RabbitMQ")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Usuário criado com sucesso",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserModel.class))),
            @ApiResponse(responseCode = "400", description = "Requisição inválida", content = @Content),
            @ApiResponse(responseCode = "409", description = "Email already in use", content = @Content)
    })
    @PostMapping("/user")
    public ResponseEntity<UserModel> createUser(@Valid @RequestBody UserDto userDto){
        var userModel = new UserModel();
        BeanUtils.copyProperties(userDto,userModel);
        return ResponseEntity.status(HttpStatus.CREATED).body(service.saveAndPublish((userModel)));
    }

    @Operation(summary = "Lista todos os usuários", description = "Retorna a lista completa de usuários")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de usuários retornada com sucesso",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserModel.class)))
    })
    @GetMapping("/all/users")
    public ResponseEntity<List<UserModel>> listAllUsers() {
        List<UserModel> users = service.getAllUsers();
        return ResponseEntity.ok(users);
    }

    @Operation(summary = "Deleta um usuário", description = "Deleta o usuário identificado pelo id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Usuário deletado com sucesso", content = @Content),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado", content = @Content)
    })
    @DeleteMapping("/user/{id}")
    public ResponseEntity<Void> deleteUser(
            @Parameter(description = "ID do usuário a ser deletado", required = true)
            @PathVariable UUID id) {
        service.deleteById(id);
        return ResponseEntity.noContent().build();
    }

}
